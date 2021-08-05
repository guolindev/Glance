/*
 * Copyright (C)  guolin, Glance Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glance.guolindev.ui.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.glance.guolindev.logic.model.*
import com.glance.guolindev.logic.repository.DatabaseRepository
import com.glance.guolindev.logic.typechange.BLOB_FIELD_TYPE
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

/**
 * DataViewModel holds view data of DataActivity and provide api to specific data operations of table.
 *
 * @author guolin
 * @since 2020/9/13
 */
class DataViewModel(private val repository: DatabaseRepository) : ViewModel() {

    /**
     * The LiveData variable to observe get columns result.
     */
    val columnsLiveData: LiveData<List<Column>>
        get() = _columnsLiveData

    /**
     * The LiveData variable to observe update data result.
     */
    val updateDataLiveData: LiveData<Resource<UpdateBean?>>
        get() = _updateDataLiveData

    /**
     * The LiveData variable to observe exceptions happened in this ViewModel.
     */
    val errorLiveData: LiveData<Throwable>
        get() = _errorLiveData

    private val _columnsLiveData = MutableLiveData<List<Column>>()

    private val _updateDataLiveData = MutableLiveData<Resource<UpdateBean?>>()

    private val _errorLiveData = MutableLiveData<Throwable>()

    private val handler = CoroutineExceptionHandler { _, throwable ->
        _errorLiveData.value = throwable
    }

    /**
     * Get the columns of a table.
     */
    fun getColumnsInTable(table: String) = viewModelScope.launch(handler) {
        val columns = repository.getColumnsInTable(table)
        _columnsLiveData.value = columns
    }

    /**
     * Update data in a specific table by primary key. So there must be a primary key in table.
     */
    fun updateDataInTable(updateBean: UpdateBean) = viewModelScope.launch {
        try {
            val table = updateBean.table
            val row = updateBean.row
            val columnIndex = updateBean.columnIndex
            val updateColumnName = row.dataList[columnIndex].columnName
            val updateColumnType = row.dataList[columnIndex].columnType
            val updateValue = updateBean.updateValue
            var primaryKey: Data? = null
            var updateColumnValid = false
            if (updateColumnType == BLOB_FIELD_TYPE) {
                _updateDataLiveData.value = Resource.error("Blob column can not be modified by Glance")
                return@launch
            }
            for (data in row.dataList) {
                if (data.isPrimaryKey) {
                    primaryKey = data
                }
                if (data.columnName == updateColumnName) {
                    updateColumnValid = true
                }
                if (primaryKey != null && updateColumnValid) {
                    break
                }
            }
            if (primaryKey == null || !updateColumnValid) {
                _updateDataLiveData.value = if (primaryKey == null) {
                    Resource.error("Update failed: table $table doesn't have a primary key.")
                } else {
                    Resource.error("Update failed: $updateColumnName is an invalid column.")
                }
                return@launch
            }
            val affectedRows = repository.updateDataInTableByPrimaryKey(
                table,
                primaryKey,
                updateColumnName,
                updateColumnType,
                updateValue
            )
            if (affectedRows == 1) {
                _updateDataLiveData.value = Resource.success(updateBean)
            } else {
                _updateDataLiveData.value =
                    Resource.error("Update abnormal: affected rows are $affectedRows")
            }
        } catch (e: Exception) {
            _updateDataLiveData.value = Resource.error("Update failed: ${e.message}")
        }
    }

    /**
     * Get the flow to load data from specific table.
     */
    fun loadDataFromTable(table: String, columns: List<Column>) =
        repository.getDataFromTableStream(table, columns).cachedIn(viewModelScope)

}
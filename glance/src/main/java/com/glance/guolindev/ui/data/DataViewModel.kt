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
import com.glance.guolindev.logic.model.Column
import com.glance.guolindev.logic.model.Resource
import com.glance.guolindev.logic.model.Row
import com.glance.guolindev.logic.repository.DatabaseRepository
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
    val updateDataLiveData: LiveData<Resource<Any?>>
        get() = _updateDataLiveData

    /**
     * The LiveData variable to observe exceptions happened in this ViewModel.
     */
    val errorLiveData: LiveData<Throwable>
        get() = _errorLiveData

    private val _columnsLiveData = MutableLiveData<List<Column>>()

    private val _updateDataLiveData = MutableLiveData<Resource<Any?>>()

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
     * Update data in a specific table.
     */
    fun updateDataInTable(table: String, row: Row) = viewModelScope.launch(handler) {
        try {
            // TODO update data in table
            _updateDataLiveData.value = Resource.success(null)
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
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

package com.glance.guolindev.ui.table

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glance.guolindev.logic.model.Resource
import com.glance.guolindev.logic.model.Table
import com.glance.guolindev.logic.repository.DatabaseRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

/**
 * TableViewModel holds view data of TableActivity and provide api to specific table operations.
 *
 * @author guolin
 * @since 2020/9/4
 */
class TableViewModel(private val repository: DatabaseRepository) : ViewModel() {

    /**
     * The LiveData variable to observe db file list.
     */
    val tablesLiveData: LiveData<Resource<List<Table>>>
        get() = _tablesLiveData

    private val _tablesLiveData = MutableLiveData<Resource<List<Table>>>()

    private val handler = CoroutineExceptionHandler { _, throwable ->
        _tablesLiveData.value = Resource.error(throwable.message ?: "Uncaught exception happens")
    }

    /**
     * Get all tables in a specific db file represented by the [dbPath] parameter.
     */
    fun getAllTablesInDB(dbPath: String) = viewModelScope.launch(handler) {
        _tablesLiveData.value = Resource.loading()
        _tablesLiveData.value = Resource.success(repository.getSortedTablesInDB(dbPath))
    }

    /**
     * Close the opened database when [TableActivity] destroyed.
     */
    fun closeDatabase() = viewModelScope.launch(handler) {
        repository.closeDatabase()
    }

}
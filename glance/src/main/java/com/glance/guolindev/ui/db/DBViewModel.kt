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

package com.glance.guolindev.ui.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glance.guolindev.logic.model.DBFile
import com.glance.guolindev.logic.repository.Repository
import com.glance.guolindev.logic.util.DBScanner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import org.litepal.LitePal
import org.litepal.extension.isExist

/**
 * DBViewModel holds view data of DBActivity and provide api to specific db operations.
 *
 * @author guolin
 * @since 2020/8/25
 */
class DBViewModel : ViewModel() {

    /**
     * The LiveData variable to observe db file list.
     */
    val dbListLiveData: LiveData<List<DBFile>>
        get() = _dbListLiveData

    private val _dbListLiveData = MutableLiveData<List<DBFile>>()

    /**
     * Load the db files from cache immediately and show them on UI.
     * Then scan all db files of current app.
     */
    fun loadAndRefreshDBFiles() = viewModelScope.launch {
        // There're 3 steps in this function.
        // First load db files from cache and show the on UI immediately.
        val cachedDBList = Repository.loadCachedDbFiles()
        _dbListLiveData.value = cachedDBList

        // Second scan all db files of current app and update the UI with DiffUtil.
        val scannedDBList = Repository.scanAllDBFiles()
        _dbListLiveData.value = scannedDBList

        // Third update the cache with lasted data.
        Repository.cacheDbFiles(scannedDBList)
    }

    /**
     * Scan all db files of current app. When find a db file, use LiveData to notify to the activity.
     */
    fun scanAllDBFiles() {
        viewModelScope.launch {
//            Repository.scanAllDBFiles()
//                .flowOn(Dispatchers.Default)
//                .collect {
//                    _dbFileLiveData.value = it
//                }
        }
    }

}
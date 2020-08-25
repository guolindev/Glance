package com.glance.guolindev.ui.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glance.guolindev.logic.model.DBFile
import com.glance.guolindev.logic.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

/**
 * DBViewModel holds view data of DBActivity and provide api to specific db operations.
 *
 * @author guolin
 * @since 2020/8/25
 */
class DBViewModel : ViewModel() {

    /**
     * The LiveData variable to observe db file when found one.
     */
    val dbFileLiveData: LiveData<DBFile>
        get() = _dbFileLiveData

    private val _dbFileLiveData = MutableLiveData<DBFile>()

    /**
     * Scan all db files of current app. When find a db file, use LiveData to notify to the activity.
     */
    fun scanAllDBFiles() {
        viewModelScope.launch {
            Repository.scanAllDBFiles()
                .flowOn(Dispatchers.Default)
                .collect {
                    _dbFileLiveData.value = it
                }
        }
    }

}
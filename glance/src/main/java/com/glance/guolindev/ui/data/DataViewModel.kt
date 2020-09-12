package com.glance.guolindev.ui.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glance.guolindev.logic.model.Column
import com.glance.guolindev.logic.model.Resource
import com.glance.guolindev.logic.model.Table
import com.glance.guolindev.logic.repository.DatabaseRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

/**
 *
 * @author guolin
 * @since 2020/9/13
 */
class DataViewModel(private val repository: DatabaseRepository) : ViewModel() {

    /**
     * The LiveData variable to observe db file list.
     */
    val columnsLiveData: LiveData<Resource<List<Column>>>
        get() = _columnsLiveData

    private val _columnsLiveData = MutableLiveData<Resource<List<Column>>>()

    private val handler = CoroutineExceptionHandler { _, throwable ->
        _columnsLiveData.value = Resource.error(throwable.message ?: "Uncaught exception happens")
    }

    fun getColumnsInTable(table: String) = viewModelScope.launch {
        val columns = repository.getColumnsInTable(table)
        _columnsLiveData.value = Resource.success(columns)
    }

}
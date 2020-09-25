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

package com.glance.guolindev.logic.repository

import android.database.sqlite.SQLiteDatabase
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.glance.guolindev.logic.model.Column
import com.glance.guolindev.logic.model.Row
import com.glance.guolindev.logic.util.DBHelper
import com.glance.guolindev.logic.util.DBPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.lang.RuntimeException

/**
 * We set page size to 50 in pager layer. So we only load 50 items by each page.
 * And we only need to access to database only after a lot of pages, since the page in database is quite large.
 */
const val PAGE_SIZE = 50

/**
 * DatabaseRepository to communicate with ViewModels and database layer back end logic handler.
 *
 * @author guolin
 * @since 2020/9/4
 */
class DatabaseRepository(private val dbHelper: DBHelper) {

    private var openedDatabase: SQLiteDatabase? = null

    /**
     * Find all tables in a specific db file represented by the [dbPath] parameter.
     * And sort them by the table name.
     */
    suspend fun getSortedTablesInDB(dbPath: String) = withContext(Dispatchers.Default) {
        openedDatabase = dbHelper.openDatabase(dbPath)
        openedDatabase?.let { db ->
            val tableList = dbHelper.getTablesInDB(db)
            tableList.sortedBy { it.name }
        } ?: emptyList()
    }

    /**
     * Get all columns in a specific table, and return them in a List.
     */
    suspend fun getColumnsInTable(table: String) = withContext(Dispatchers.Default) {
        openedDatabase?.let { db ->
            dbHelper.getColumnsInTable(db, table)
        } ?: throw RuntimeException("Opened database is null.")
    }

    /**
     * Close the opened databases and makes [openedDatabase] null.
     */
    suspend fun closeDatabase() = withContext(Dispatchers.Default) {
        openedDatabase?.close()
        openedDatabase = null
    }

    /**
     * Get the stream that could to load data by [DBPagingSource].
     */
    fun getDataFromTableStream(table: String, columns: List<Column>): Flow<PagingData<Row>> {
        openedDatabase?.let { db ->
            return Pager(config =  PagingConfig(PAGE_SIZE), pagingSourceFactory = { DBPagingSource(dbHelper, db, table, columns) }).flow
        } ?: throw RuntimeException("Opened database is null.")
    }

}
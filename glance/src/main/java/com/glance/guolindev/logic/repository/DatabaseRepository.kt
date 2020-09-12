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
import com.glance.guolindev.logic.model.Table
import com.glance.guolindev.logic.util.DBHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * DatabaseRepository to communicate with ViewModels and database layer back end logic handler.
 *
 * @author guolin
 * @since 2020/9/4
 */
class DatabaseRepository(private val dbHelper: DBHelper) {

    var openedDatabase: SQLiteDatabase? = null

    /**
     * Find all tables in a specific db file represented by the [dbPath] parameter.
     * And sort them by the table name.
     */
    suspend fun getSortedTablesInDB(dbPath: String): List<Table> = withContext(Dispatchers.Default) {
        openedDatabase = dbHelper.openDatabase(dbPath)
        openedDatabase?.let {
            val tableList = dbHelper.getTablesInDB(it)
            tableList.sortedBy { it.name }
        } ?: emptyList()
    }



    /**
     * Close the opened databases and makes [openedDatabase] null.
     */
    fun closeDatabase() {
        openedDatabase?.close()
        openedDatabase = null
    }

}
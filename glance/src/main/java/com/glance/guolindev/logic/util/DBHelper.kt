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
package com.glance.guolindev.logic.util

import android.database.sqlite.SQLiteDatabase
import com.glance.guolindev.logic.model.Table
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


/**
 * Helper class with all necessary database operations.
 * @author guolin
 * @since 2020/9/4
 */
class DBHelper {

    /**
     * Open a database by the passed db file path and return SQLiteDatabase instance to operate this db file.
     */
    suspend fun openDatabase(path: String): SQLiteDatabase = withContext(Dispatchers.Default) {
        SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE)
    }

    /**
     * Find all tables by the [database] parameter.
     */
    suspend fun getAllTablesInDB(database: SQLiteDatabase): List<Table> = withContext(Dispatchers.Default) {
        val tableList = ArrayList<Table>()
        database.rawQuery("select * from sqlite_master", null).use { cursor ->
            if (cursor.moveToFirst()) {
                do {
                    val tableName = cursor.getString(cursor.getColumnIndexOrThrow("tbl_name"))
                    tableList.add(Table(tableName))
                } while (cursor.moveToNext())
            }
        }
        tableList
    }

}
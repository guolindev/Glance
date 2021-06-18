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
import android.widget.TextView
import com.glance.guolindev.Glance
import com.glance.guolindev.extension.dp
import com.glance.guolindev.logic.model.Column
import com.glance.guolindev.logic.model.Data
import com.glance.guolindev.logic.model.Row
import com.glance.guolindev.logic.model.Table
import com.glance.guolindev.logic.typechange.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.max
import kotlin.math.min

/**
 * We set a quite large number of page in database layer.
 * 1001 is a weird number, but it can let us have a more accurate measure for each column width.
 * If the column is integer with increment number, 1001 can measure the enough space for 9999 records.
 * 1000 is more decent number, but if the record based on 0, it can only measure the space for 999 records.
 */
const val PAGE_SIZE = 1001

/**
 * Helper class with all necessary database operations.
 *
 * @author guolin
 * @since 2020/9/4
 */
class DBHelper {

    /**
     * The max width of a column can be.
     */
    private val maxColumnWidth = 300.dp

    /**
     * The min width of a column can be.
     */
    private val minColumnWidth = 20.dp

    /**
     * All the supported mapping type rules in the array.
     */
    private val typeChangeRules = arrayOf(IntegerMap(), TextMap(), RealMap(), BlobMap(), NullMap())

    /**
     * Open a database by the passed db file path and return SQLiteDatabase instance to operate this db file.
     */
    suspend fun openDatabase(path: String): SQLiteDatabase = withContext(Dispatchers.Default) {
        SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE)
    }

    /**
     * Find all tables by the [db] parameter.
     */
    suspend fun getTablesInDB(db: SQLiteDatabase) = withContext(Dispatchers.Default) {
        val tableList = ArrayList<Table>()
        db.rawQuery("select * from sqlite_master where type = ?", arrayOf("table")).use { cursor ->
            if (cursor.moveToFirst()) {
                do {
                    val tableName = cursor.getString(cursor.getColumnIndexOrThrow("tbl_name"))
                    tableList.add(Table(tableName))
                } while (cursor.moveToNext())
            }
        }
        tableList
    }

    /**
     * Get all columns in a specific table, and return them in a List.
     */
    suspend fun getColumnsInTable(db: SQLiteDatabase, table: String) = withContext(Dispatchers.Default) {
        val columnList = ArrayList<Column>()
        val getColumnsSQL = "pragma table_info($table)"
        db.rawQuery(getColumnsSQL, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                do {
                    val columnName = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                    val columnType = cursor.getString(cursor.getColumnIndexOrThrow("type"))
                    val primaryKey = cursor.getInt(cursor.getColumnIndexOrThrow("pk"))
                    val column = Column(columnName, columnType, primaryKey == 1)
                    columnList.add(column)
                } while (cursor.moveToNext())
            }
        }
        measureColumnsWidth(db, table, columnList)
        columnList
    }

    /**
     * Load data in a table by page. Need to specify which columns data need to load.
     * Then we can load the data into a rowList and match the position for the [columns] param.
     */
    suspend fun loadDataInTable(db: SQLiteDatabase, table: String, page: Int, columns: List<Column>) = withContext(Dispatchers.Default) {
        val rowList = ArrayList<Row>()
        val offset = page * PAGE_SIZE
        val limit = "${offset},${PAGE_SIZE}"
        db.query(table, null, null, null, null, null, null, limit)?.use { cursor ->
            if (cursor.moveToFirst()) {
                var count = 1
                do {
                    val dataList = ArrayList<Data>()
                    for (column in columns) {
                        val columnIndex = cursor.getColumnIndexOrThrow(column.name)
                        var fieldType = ""
                        for (rule in typeChangeRules) {
                            val type = rule.columnType2FieldType(column.type)
                            if (type != null) {
                                fieldType = type
                                break
                            }
                        }
                        val value =  if (cursor.isNull(columnIndex)) {
                            "<NULL>"
                        } else {
                            when(fieldType) {
                                TEXT_FIELD_TYPE -> cursor.getString(columnIndex)
                                INTEGER_FIELD_TYPE -> cursor.getLong(columnIndex).toString()
                                REAL_FIELD_TYPE -> cursor.getDouble(columnIndex).toString()
                                BLOB_FIELD_TYPE -> "<Binary Data>"
                                NULL_FIELD_TYPE -> "<NULL>"
                                // This column type is not supported. Glance will use the getString way to read value from this column.
                                else -> cursor.getString(columnIndex)
                            }
                        }
                        dataList.add(Data(value, fieldType, column.isPrimaryKey))
                    }
                    val lineNum = offset + count // This is the line number of current row, starting by 1.
                    rowList.add(Row(lineNum, dataList))
                    count++
                } while (cursor.moveToNext())
            }
        }
        rowList
    }

    /**
     * Measure the proper width of each column. They should just wrap the text content, but they can't
     * be smaller than the min width or larger than the max width.
     */
    private suspend fun measureColumnsWidth(db: SQLiteDatabase, table: String, columns: List<Column>) = withContext(Dispatchers.Default) {
        val paint = TextView(Glance.context).paint
        for (column in columns) {
            var columnWidth = paint.measureText(column.name).toInt()
            columnWidth = min(columnWidth, maxColumnWidth)
            columnWidth = max(columnWidth, minColumnWidth)
            column.width = columnWidth
        }
        val rowList = loadDataInTable(db, table, 0, columns) // load page 0 data
        // we iterate the first page data and evaluate the proper width of each column.
        for (row in rowList) {
            row.dataList.forEachIndexed { index, data ->
                val column = columns[index]
                var columnWidth = paint.measureText(data.value).toInt()
                columnWidth = min(columnWidth, maxColumnWidth)
                columnWidth = max(columnWidth, minColumnWidth)
                if (columnWidth > column.width) {
                    column.width = columnWidth
                }
            }
        }
    }

}
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
import androidx.paging.PagingSource
import com.glance.guolindev.logic.model.Column
import com.glance.guolindev.logic.model.Row

/**
 * We need to use a DBPagingSource and inherits from PagingSource to implements the paging function with paging3 library.
 *
 * @author guolin
 * @since 2020/9/17
 */
class DBPagingSource(private val dbHelper: DBHelper,
                     private val db: SQLiteDatabase,
                     private val table: String, private
                     val columns: List<Column>) : PagingSource<Int, Row>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Row> {
        val page = params.key ?: 0 // set page 0 as default
        val rowData = dbHelper.loadDataInTable(db, table, page, columns)
        val prevKey = if (page > 0) page - 1 else null
        val nextKey = if (rowData.isNotEmpty()) page + 1 else null
        return LoadResult.Page(rowData, prevKey, nextKey)
    }

}
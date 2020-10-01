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

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.glance.guolindev.R
import com.glance.guolindev.extension.dp
import com.glance.guolindev.logic.model.Column
import com.glance.guolindev.logic.model.Resource
import com.glance.guolindev.view.TableCellView
import kotlinx.android.synthetic.main.glance_library_activity_data.*
import kotlinx.android.synthetic.main.glance_library_activity_table.recyclerView
import kotlinx.android.synthetic.main.glance_library_activity_table.toolbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

/**
 * Data layer of Activity, which shows data from a table by page.
 *
 * @author guolin
 * @since 2020/9/13
 */
class DataActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProvider(this, DataViewModelFactory()).get(DataViewModel::class.java) }

    /**
     * The adapter for the main data of table.
     */
    private lateinit var adapter: DataAdapter

    /**
     * The adapter for the footer view.
     */
    private lateinit var footerAdapter: DataFooterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.glance_library_activity_data)
        val table = intent.getStringExtra(TABLE_NAME)
        if (table == null) {
            Toast.makeText(this, "Table name is null", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = table

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        viewModel.columnsLiveData.observe(this) {
            when (it.status) {
                Resource.SUCCESS -> {
                    initAdapter(table, it.data!!)
                }
                Resource.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.getColumnsInTable(table)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Init the adapter for displaying data in RecyclerView.
     */
    private fun initAdapter(table: String, columns: List<Column>) {
        // It may cost some time for calculating row width, so we put it into thread.
        thread {
            var rowWidth = 0
            for (column in columns) {
                rowWidth += column.width
            }
            rowWidth += columns.size * 20.dp // we always have 20dp extra space for each column. 5dp for start. 15dp for end.
            recyclerView.post {
                // Make sure we are back to the main thread and we can get horizontalScrollView width now.
                rowWidth = rowWidth.coerceAtLeast(horizontalScrollView.width)
                buildTableTitle(columns, rowWidth)
                adapter = DataAdapter(columns, rowWidth)
                footerAdapter = DataFooterAdapter(horizontalScrollView.width) {
                    adapter.itemCount
                }
                recyclerView.adapter = ConcatAdapter(adapter, footerAdapter)
                adapter.addLoadStateListener { loadState ->
                    when (loadState.refresh) {
                        is LoadState.NotLoading -> {
                            horizontalScrollView.visibility = View.VISIBLE
                            progressBar.visibility = View.INVISIBLE
                            footerAdapter.displayFooter()
                        }
                        is LoadState.Loading -> {
                            horizontalScrollView.visibility = View.INVISIBLE
                            progressBar.visibility = View.VISIBLE
                        }
                    }
                }
                loadDataFromTable(table, columns)
            }
        }
    }

    /**
     * Begin to load data from table with the specific columns.
     */
    private fun loadDataFromTable(table: String, columns: List<Column>) {
        lifecycleScope.launch {
            viewModel.loadDataFromTable(table, columns).collect {
                adapter.submitData(it)
                // This line will never reach. Don't do anything below
            }
            // This line will never reach. Don't do anything below
        }
    }

    /**
     * Build a TableRowLayout as a row to show the columns of a table as title.
     */
    private fun buildTableTitle(columns: List<Column>, rowWidth: Int) {
        val param = rowTitleLayout.layoutParams
        param.width = rowWidth
        columns.forEachIndexed { index, column ->
            val tableCellView = buildTableCellView(column)
            tableCellView.isFirstCell = index == 0 // Indicate it's first cell of the row or not
            // We let each column has 20dp extra space, to make it look better.
            val layoutParam = LinearLayout.LayoutParams(column.width + 20.dp, LinearLayout.LayoutParams.MATCH_PARENT)
            rowTitleLayout.addView(tableCellView, layoutParam)
        }
    }

    /**
     * Build a TableCellView widget as a table cell to show data in title.
     */
    private fun buildTableCellView(column: Column): TableCellView {
        val tableCellView = TableCellView(this)
        tableCellView.gravity = Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL
        tableCellView.setTextColor(ContextCompat.getColor(this, R.color.glance_library_table_text))
        tableCellView.typeface = Typeface.DEFAULT_BOLD
        tableCellView.text = column.name
        return tableCellView
    }

    companion object {

        const val TABLE_NAME = "table_name"

        fun actionOpenTable(context: Context, tableName: String) {
            val intent = Intent(context, DataActivity::class.java)
            intent.putExtra(TABLE_NAME, tableName)
            context.startActivity(intent)
        }

    }

}
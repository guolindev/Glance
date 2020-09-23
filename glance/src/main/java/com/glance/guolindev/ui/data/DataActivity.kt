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
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.glance.guolindev.Glance
import com.glance.guolindev.R
import com.glance.guolindev.exception.ColumnTypeUnsupportedException
import com.glance.guolindev.logic.model.Column
import com.glance.guolindev.logic.model.Resource
import kotlinx.android.synthetic.main.glance_library_activity_table.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Data layer of Activity, which shows data from a table by page.
 *
 * @author guolin
 * @since 2020/9/13
 */
class DataActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProvider(this, DataViewModelFactory()).get(DataViewModel::class.java) }

    private lateinit var adapter: DataAdapter

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
                    val columns = it.data!!
                    for (column in columns) {
                        println("column ${column.name} type is ${column.type}")
                    }
                    adapter = DataAdapter(columns)
                    recyclerView.adapter = adapter
                    loadDataFromTable(table, columns)
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

    private fun loadDataFromTable(table: String, columns: List<Column>) {
        lifecycleScope.launch {
            viewModel.loadDataFromTable(table, columns)
                .catch { e ->
                    Toast.makeText(Glance.context, e.message, Toast.LENGTH_SHORT).show()
                }
                .collect {
                    adapter.submitData(it)
                }
        }
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
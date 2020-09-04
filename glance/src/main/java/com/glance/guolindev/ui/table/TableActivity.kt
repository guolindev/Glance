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

package com.glance.guolindev.ui.table

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.glance.guolindev.R
import kotlinx.android.synthetic.main.glance_library_activity_table.*

/**
 * Table layer of Activity, which shows all tables in a specific database file.
 *
 * @author guolin
 * @since 2020/9/4
 */
class TableActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProvider(this, TableViewModelFactory()).get(TableViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.glance_library_activity_table)
        val dbName = intent.getStringExtra(DB_NAME)
        val dbPath = intent.getStringExtra(DB_PATH)
        if (dbPath == null) {
            Toast.makeText(this, "dbPath is null which is not a correct state.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = dbName
        viewModel.getAllTablesInDB(dbPath)
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

    companion object {

        const val DB_NAME = "db_name"
        const val DB_PATH = "db_path"

        fun actionOpenDatabase(context: Context, dbName: String, dbPath: String) {
            val intent = Intent(context, TableActivity::class.java)
            intent.putExtra(DB_NAME, dbName)
            intent.putExtra(DB_PATH, dbPath)
            context.startActivity(intent)
        }

    }

}
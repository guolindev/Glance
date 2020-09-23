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

package com.glance.guolindev.ui.db

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.glance.guolindev.R
import com.glance.guolindev.logic.model.DBFile
import kotlinx.android.synthetic.main.glance_library_activity_db.*

/**
 * Databases layer of Activity, which shows all the databases file found by Glance.
 * Glance will scan the internal and external storage of current app and display all the files named end with .db.
 *
 * @author guolin
 * @since 2020/8/25
 */
class DBActivity : AppCompatActivity() {

    private val dbViewModel by lazy { ViewModelProvider(this, DBViewModelFactory()).get(DBViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.glance_library_activity_db)
        val dbList = ArrayList<DBFile>()
        val adapter = DBAdapter(dbList)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        dbViewModel.dbListLiveData.observe(this) { newDBList ->
            val diffResult = DiffUtil.calculateDiff(DBDiffCallback(dbList, newDBList))
            dbList.clear()
            dbList.addAll(newDBList)
            diffResult.dispatchUpdatesTo(adapter)
            val title = if (adapter.itemCount <= 1) {
                "1 ${getString(R.string.glance_library_database_found)}"
            } else {
                "${adapter.itemCount} ${getString(R.string.glance_library_databases_found)}"
            }
            titleText.text = title
        }
        dbViewModel.progressLiveData.observe(this) {
            progressBar.visibility = if (it) {
                // start loading
                View.VISIBLE
            } else {
                // finish loading
                View.INVISIBLE
            }
        }
        dbViewModel.loadAndRefreshDBFiles()
    }

}
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
import androidx.recyclerview.widget.RecyclerView
import com.glance.guolindev.R
import com.glance.guolindev.databinding.GlanceLibraryActivityDbBinding
import com.glance.guolindev.logic.model.DBFile

/**
 * Databases layer of Activity, which shows all the databases file found by Glance.
 * Glance will scan the internal and external storage of current app and display all the files named end with .db.
 *
 * @author guolin
 * @since 2020/8/25
 */
class DBActivity : AppCompatActivity() {

    private val dbViewModel by lazy { ViewModelProvider(this, DBViewModelFactory()).get(DBViewModel::class.java) }

    private lateinit var binding: GlanceLibraryActivityDbBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = GlanceLibraryActivityDbBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dbList = ArrayList<DBFile>()
        val adapter = DBAdapter(dbList)
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = layoutManager
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                // When new item inserted by DiffUtil in adapter, we always scroll to the top to show the lasted db file to user.
                if (savedInstanceState == null) {
                    // We only scroll to the top when savedInstanceState is null.
                    // This can avoid scrolling to top every time when device rotates.
                    binding.recyclerView.scrollToPosition(0)
                }
            }
        })
        dbViewModel.dbListLiveData.observe(this) { newDBList ->
            val diffResult = DiffUtil.calculateDiff(DBDiffCallback(dbList, newDBList))
            dbList.clear()
            dbList.addAll(newDBList)
            diffResult.dispatchUpdatesTo(adapter)
            val title = if (adapter.itemCount <= 1) {
                "${adapter.itemCount} ${getString(R.string.glance_library_database_found)}"
            } else {
                "${adapter.itemCount} ${getString(R.string.glance_library_databases_found)}"
            }
            binding.titleText.text = title
            if (adapter.itemCount > 0) {
                binding.noDbTextView.visibility = View.INVISIBLE
                binding.recyclerView.visibility = View.VISIBLE
            } else {
                binding.noDbTextView.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.INVISIBLE
            }
        }
        dbViewModel.progressLiveData.observe(this) {
            binding.progressBar.visibility = if (it) {
                // start loading
                View.VISIBLE
            } else {
                // finish loading
                View.INVISIBLE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (dbViewModel.dbListLiveData.value == null) { // When there's no data on ui, we load and refresh db files.
            dbViewModel.loadAndRefreshDBFiles()
        } else { // Otherwise, we only refresh db files to show the latest data.
            dbViewModel.refreshDBFiles()
        }
    }

}
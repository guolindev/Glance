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

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.glance.guolindev.R
import com.glance.guolindev.databinding.GlanceLibraryDbItemBinding
import com.glance.guolindev.extension.exists
import com.glance.guolindev.extension.isValidDBFile
import com.glance.guolindev.extension.setExtraMarginForFirstAndLastItem
import com.glance.guolindev.logic.model.DBFile
import com.glance.guolindev.ui.table.TableActivity
import com.google.android.material.card.MaterialCardView
import java.text.SimpleDateFormat
import java.util.*

/**
 * Adapter for the RecyclerView to show all the db files of current app.
 *
 * @author guolin
 * @since 2020/8/26
 */
class DBAdapter(private val dbList: List<DBFile>) : RecyclerView.Adapter<DBAdapter.ViewHolder>() {

    lateinit var context: Context

    class ViewHolder(dbItemBinding: GlanceLibraryDbItemBinding) : RecyclerView.ViewHolder(dbItemBinding.root) {
        val storageLayout: MaterialCardView = dbItemBinding.storageLayout
        val storageText: TextView = dbItemBinding.storageText
        val dbNameText: TextView = dbItemBinding.dbNameText
        val dbPathText: TextView = dbItemBinding.dbPathText
        val modifyTimeText: TextView = dbItemBinding.modifyTimeText
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (!::context.isInitialized) context = parent.context
        val dbItemBinding = GlanceLibraryDbItemBinding.inflate(LayoutInflater.from(context), parent, false)
        val holder = ViewHolder(dbItemBinding)
        holder.itemView.setOnClickListener {
            val position = holder.bindingAdapterPosition
            val dbFile = dbList[position]
            if (!dbFile.exists()) {
                Toast.makeText(parent.context, "This file doesn't exist anymore.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (!dbFile.isValidDBFile()) {
                Toast.makeText(parent.context, "This file is not a valid db file.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            TableActivity.actionOpenDatabase(parent.context, dbFile.name, dbFile.path)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setExtraMarginForFirstAndLastItem(position == 0, position == dbList.size - 1)
        val dbFile = dbList[position]
        holder.dbNameText.text = dbFile.name
        holder.dbPathText.text = dbFile.path
        if (dbFile.internal) {
            holder.storageText.setText(R.string.glance_library_internal_storage)
            holder.storageLayout.setCardBackgroundColor(ContextCompat.getColor(context, R.color.glance_library_db_card_internal_storage_db))
        } else {
            holder.storageText.setText(R.string.glance_library_external_storage)
            holder.storageLayout.setCardBackgroundColor(ContextCompat.getColor(context, R.color.glance_library_db_card_external_storage_db))
        }
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)
        val modifyTime = "Last modified ${simpleDateFormat.format(dbFile.modifyTime)}"
        holder.modifyTimeText.text = modifyTime
    }

    override fun getItemCount() = dbList.size

}
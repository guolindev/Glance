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

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.glance.guolindev.R
import com.glance.guolindev.logic.model.DBFile
import kotlinx.android.synthetic.main.db_item.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Adapter for the RecyclerView to show all the db files of current app.
 * @author guolin
 * @since 2020/8/26
 */
class DBAdapter(private val dbList: List<DBFile>) : RecyclerView.Adapter<DBAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val storageText: TextView = itemView.storageText
        val dbNameText: TextView = itemView.dbNameText
        val dbPathText: TextView = itemView.dbPathText
        val modifyTimeText: TextView = itemView.modifyTimeText
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.db_item, parent, false)
        val holder = ViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dbFile = dbList[position]
        holder.dbNameText.text = dbFile.name
        holder.dbPathText.text = dbFile.path
        if (dbFile.internal) {
            holder.storageText.setText(R.string.glance_library_internal_storage)
        } else {
            holder.storageText.setText(R.string.glance_library_external_storage)
        }
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)
        val modifyTime = "Last modified ${simpleDateFormat.format(dbFile.modifyTime)}"
        holder.modifyTimeText.text = modifyTime
    }

    override fun getItemCount() = dbList.size

}
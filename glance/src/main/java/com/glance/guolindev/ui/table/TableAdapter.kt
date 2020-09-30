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

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.glance.guolindev.R
import com.glance.guolindev.logic.model.Table
import com.glance.guolindev.ui.data.DataActivity
import kotlinx.android.synthetic.main.glance_library_table_item.view.*

/**
 * Adapter for the RecyclerView to show all tables in db file.
 *
 * @author guolin
 * @since 2020/9/10
 */
class TableAdapter(private val tableList: List<Table>) : RecyclerView.Adapter<TableAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tableNameText: TextView = itemView.tableNameText
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.glance_library_table_item, parent, false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            val position = holder.bindingAdapterPosition
            val table = tableList[position]
            DataActivity.actionOpenTable(parent.context, table.name)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val table = tableList[position]
        holder.tableNameText.text = table.name
    }

    override fun getItemCount() = tableList.size

}
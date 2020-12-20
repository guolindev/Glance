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
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.glance.guolindev.R
import com.glance.guolindev.databinding.GlanceLibraryRowItemBinding
import com.glance.guolindev.extension.dp
import com.glance.guolindev.logic.model.Column
import com.glance.guolindev.logic.model.Row
import com.glance.guolindev.view.TableCellView
import com.glance.guolindev.view.TableRowLayout

/**
 * This is adapter of RecyclerView to display data from a table. Using PagingDataAdapter as parent
 * to implement the paging job.
 *
 * @author guolin
 * @since 2020/9/22
 */
class DataAdapter(private val columns: List<Column>, private val rowWidth: Int) : PagingDataAdapter<Row, DataAdapter.ViewHolder>(COMPARATOR) {

    lateinit var context: Context

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (!::context.isInitialized) context = parent.context
        val rowLayoutBinding = GlanceLibraryRowItemBinding.inflate(LayoutInflater.from(context), parent, false)
        val param = rowLayoutBinding.root.layoutParams
        param.width = rowWidth
        for (column in columns) {
            val tableCellView = buildTableCellView()
            // We let each column has 20dp extra space, to make it look better.
            val layoutParam = LinearLayout.LayoutParams(column.width + 20.dp, LinearLayout.LayoutParams.MATCH_PARENT)
            rowLayoutBinding.root.addView(tableCellView, layoutParam)
        }
        return ViewHolder(rowLayoutBinding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val row = getItem(position)
        if (row != null) {
            val rowLayout = holder.itemView as TableRowLayout
            val backgroundColorRes = if (position % 2 == 0) {
                R.color.glance_library_table_even_row_bg
            } else {
                R.color.glance_library_table_odd_row_bg
            }
            rowLayout.setBackgroundColor(ContextCompat.getColor(context, backgroundColorRes))
            for (i in (0 until rowLayout.childCount)) {
                val tableCellView = rowLayout.getChildAt(i) as TableCellView
                tableCellView.columnIndex = i
                tableCellView.row = row
            }
        }
    }

    /**
     * Build a TextView widget as a table cell to show data in a row.
     */
    private fun buildTableCellView(): TableCellView {
        val tableCellView = TableCellView(context)
        tableCellView.gravity = Gravity.CENTER_VERTICAL
        // Actually each column has 20dp extra space, but we only use 10 in padding.
        // This makes each column has more space to show their content before be ellipsized.
        tableCellView.setPadding(5.dp, 0, 5.dp, 0)
        tableCellView.setSingleLine()
        tableCellView.ellipsize = TextUtils.TruncateAt.END
        tableCellView.setTextColor(ContextCompat.getColor(context, R.color.glance_library_table_text))

        return tableCellView
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Row>() {
            override fun areItemsTheSame(oldItem: Row, newItem: Row): Boolean =
                oldItem.lineNum == newItem.lineNum

            override fun areContentsTheSame(oldItem: Row, newItem: Row): Boolean =
                oldItem == newItem
        }
    }

}
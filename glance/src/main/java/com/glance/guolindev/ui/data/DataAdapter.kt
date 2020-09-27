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
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.glance.guolindev.R
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

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rowLayout = LayoutInflater.from(parent.context).inflate(R.layout.glance_library_row_item, parent, false) as TableRowLayout
        val param = rowLayout.layoutParams
        param.width = rowWidth
        for (column in columns) {
            val tableCellView = buildTableCellView(parent.context, column.width)
            rowLayout.addView(tableCellView)
        }
        return ViewHolder(rowLayout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val row = getItem(position)
        if (row != null) {
            val isLastRow = position == itemCount - 1
            val rowLayout = holder.itemView as TableRowLayout
            rowLayout.shouldDrawBottomBorder = isLastRow // We only draw the bottom border when it's last row of the table.
            for (i in (0 until rowLayout.childCount)) {
                val tableCellView = rowLayout.getChildAt(i) as TableCellView
                tableCellView.isFirstCell = i == 0 // This is first cell of the row or not
                tableCellView.isLastCell = i == rowLayout.childCount - 1 // This is last cell of the row or not
                tableCellView.text = row.data[i]
            }
        }
    }

    /**
     * Build a TextView widget as a table cell to show data in a row.
     */
    private fun buildTableCellView(context: Context, width: Int): TableCellView {
        val tableCellView = TableCellView(context)
        tableCellView.gravity = Gravity.CENTER_VERTICAL
        // Actually each column has 20dp extra space, but we only use 10 in padding.
        // This makes each column has more space to show their content before be ellipsized.
        tableCellView.setPadding(5.dp, 0, 5.dp, 0)
        tableCellView.setSingleLine()
        tableCellView.ellipsize = TextUtils.TruncateAt.END
        val layoutParam = LinearLayout.LayoutParams(width + 20.dp, LinearLayout.LayoutParams.MATCH_PARENT)
        tableCellView.layoutParams = layoutParam
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
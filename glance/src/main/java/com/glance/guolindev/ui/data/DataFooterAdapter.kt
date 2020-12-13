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
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.glance.guolindev.R
import com.glance.guolindev.databinding.GlanceLibraryDataFooterBinding
import com.glance.guolindev.extension.toNumericString
import com.glance.guolindev.view.FooterTextView

/**
 * The footer adapter to show how many records are loaded.
 *
 * @author guolin
 * @since 2020/9/30
 */
class DataFooterAdapter(private val layoutWidth: Int, private val screenWidth: Int, private val block: () -> Int) : RecyclerView.Adapter<DataFooterAdapter.ViewHolder>() {

    private lateinit var context: Context

    /**
     * We do not display footer at first. Only when main data are loaded, we show the footer.
     */
    private var displayFooter = false

    /**
     * This is the widget to show how many records are loaded.
     */
    private lateinit var footerTextView: FooterTextView

    class ViewHolder(itemView: TextView) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (!::context.isInitialized) context = parent.context
        val footerBinding = GlanceLibraryDataFooterBinding.inflate(LayoutInflater.from(context), parent, false)
        footerBinding.root.screenWidth = screenWidth.toFloat()
        footerBinding.root.layoutParams.width = layoutWidth
        return ViewHolder(footerBinding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recordCount = block()
        val text = if (recordCount > 1) {
            context.resources.getString(R.string.glance_library_records_loaded)
        } else {
            context.resources.getString(R.string.glance_library_record_loaded)
        }
        if (!::footerTextView.isInitialized) {
            footerTextView = holder.itemView as FooterTextView
        }
        footerTextView.text = String.format(text, recordCount.toNumericString())
    }

    override fun getItemCount() = if (displayFooter) 1 else 0

    /**
     * Display the footer to show how many records are loaded.
     */
    fun displayFooter() {
        if (!displayFooter) {
            // Once footer is displayed, we don't execute it anymore for better performance.
            displayFooter = true
            notifyDataSetChanged()
        }
    }

    /**
     * Notify FooterTextView the horizontal scroll amount to invalidate the view.
     */
    fun notifyScrollXChanged(scrollX: Float){
        if (::footerTextView.isInitialized) {
            footerTextView.notifyScrollXChanged(scrollX)
        }
    }

}
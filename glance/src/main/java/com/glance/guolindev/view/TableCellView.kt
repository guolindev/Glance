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

package com.glance.guolindev.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.glance.guolindev.R
import com.glance.guolindev.extension.dp


/**
 * Custom view to represent a table cell. Draw the border(only left border is needed) for the cell when necessary.
 *
 * @author guolin
 * @since 2020/9/27
 */
class TableCellView(context: Context, attrs: AttributeSet? = null) : AppCompatTextView(context, attrs) {

    /**
     * Use this paint to draw border of table.
     */
    private val borderPaint = Paint()

    /**
     * Indicate current cell the is first cell of the row or not.
     */
    var isFirstCell = false

    init {
        borderPaint.color = ContextCompat.getColor(context, R.color.glance_library_table_border)
        borderPaint.strokeWidth = 1f.dp
    }

    override fun onDraw(canvas: Canvas) {
        if (!isFirstCell) {
            // We don't draw the left border if it's first cell.
            canvas.drawLine(0f, 0f, 0f, height.toFloat(), borderPaint)
        }
        super.onDraw(canvas)
    }

}
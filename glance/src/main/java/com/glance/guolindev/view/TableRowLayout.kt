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
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.glance.guolindev.R
import com.glance.guolindev.extension.dp

/**
 * Custom view to represent a row of the table. Draw the top and bottom border for the row when necessary.
 *
 * @author guolin
 * @since 2020/9/27
 */
class TableRowLayout(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs) {

    /**
     * Use this paint to draw border of table.
     */
    private val borderPaint = Paint()

    /**
     * Indicate if we should draw the bottom border of the row.
     * We shouldn't draw the bottom border of a row and draw the top border of next row, otherwise the border will be too thick.
     * Control that by logic.
     */
    var shouldDrawBottomBorder = false

    init {
        setWillNotDraw(false) // Layout may not call onDraw(), so we need to disable that.
        borderPaint.color = ContextCompat.getColor(context, R.color.glance_library_table_border)
        borderPaint.strokeWidth = 1f.dp
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawLine(0f, 0f, width.toFloat(), 0f, borderPaint)
        if (shouldDrawBottomBorder) {
            canvas.drawLine(0f, height.toFloat() - 1, width.toFloat(), height.toFloat() - 1, borderPaint)
        }
        super.onDraw(canvas)
    }

}
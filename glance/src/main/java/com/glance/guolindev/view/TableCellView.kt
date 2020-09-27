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
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView


/**
 * Custom view to represent a table cell. Draw the left and right border for the cell when necessary.
 *
 * @author guolin
 * @since 2020/9/27
 */
class TableCellView(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : AppCompatTextView(context, attrs, defStyleAttr) {

    init {
        paint.color = Color.WHITE
    }

    /**
     * Indicate current cell the is first cell of the row or not.
     */
    var isFirstCell = false

    /**
     * Indicate current cell the is last cell of the row or not.
     */
    var isLastCell = false

    override fun onDraw(canvas: Canvas) {
        if (!isFirstCell) {
            // We don't draw the left border if it's first cell.
            canvas.drawLine(0f, 0f, 0f, height.toFloat(), paint)
        }
        if (!isLastCell) {
            // We don't draw the right border if it's last cell.
            canvas.drawLine(width.toFloat(), 0f, width.toFloat(), height.toFloat(), paint)
        }
        super.onDraw(canvas)
    }

}
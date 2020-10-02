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
import android.graphics.Rect
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.glance.guolindev.R

/**
 * Custom FooterTextView to show the footer text info.
 * The main goal of this view is that it can always display the text at the horizontal center of the screen.
 * Even if we scroll the HorizontalScrollView with any horizontal distance.
 *
 * @author guolin
 * @since 2020/10/1
 */
class FooterTextView(context: Context, attrs: AttributeSet? = null) : AppCompatTextView(context, attrs) {

    /**
     * This indicate the horizontal scroll distance by HorizontalScrollView.
     */
    private var scrollX = 0f

    /**
     * Use this Rect to get the bounds of displayed text.
     */
    private val rect = Rect()

    /**
     * This is important. We want the text always displayed at the center of the screen, so we must
     * know exactly how the screen width is.
     */
    var screenWidth = 0f

    init {
        paint.color = ContextCompat.getColor(context, R.color.glance_library_table_text)
    }

    /**
     * We always draw the text at the center of the screen.
     */
    override fun onDraw(canvas: Canvas) {
        val text= text.toString()
        val textWidth = paint.measureText(text)
        val leftEdge = scrollX
        val rightEdge = scrollX + screenWidth
        // This is the center position on x axis of the visible screen.
        val x = (leftEdge + rightEdge) / 2 - textWidth / 2;
        paint.getTextBounds(text, 0, text.length, rect)
        val offset = (rect.top + rect.bottom) / 2f
        val y = height / 2f - offset
        // Display the text at center
        canvas.drawText(text, x, y, paint)
    }

    /**
     * Notify the horizontal scroll amount the invalidate self to redraw the text at center.
     */
    fun notifyScrollXChanged(scrollX: Float) {
        this.scrollX = scrollX
        invalidate()
    }

}
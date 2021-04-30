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
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.HorizontalScrollView

/**
 * Custom HorizontalScrollView with scroll listener function.
 * We can call [setScrollObserver] to set a observer, and get notified when HorizontalScroller is scrolled.
 *
 * @author guolin
 * @since 2020/10/2
 */
class HorizontalScroller(context: Context, attrs: AttributeSet? = null) : HorizontalScrollView(context, attrs)  {

    private lateinit var scrollObserver: (Float) -> Unit

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        if (::scrollObserver.isInitialized) {
            scrollObserver(l.toFloat())
        }
    }

    /**
     * Set a observer, and get notified when HorizontalScroller is scrolled.
     */
    fun setScrollObserver(observer: (Float) -> Unit) {
        scrollObserver = observer
    }

    /**
     * if the motion event is not ACTION_DOWN or ACTION_POINTER_DOWN, then deal with it to scroll, while passing
     * the event downwards
     */
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.actionMasked != MotionEvent.ACTION_DOWN || ev.actionMasked != MotionEvent.ACTION_POINTER_DOWN) {
            onTouchEvent(ev)
        }
        return super.dispatchTouchEvent(ev)
    }

    /**
     * do not intercept touch event so that child scrollable view can also receive event to scroll
     */
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean = false

}
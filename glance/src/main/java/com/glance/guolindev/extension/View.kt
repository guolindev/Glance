package com.glance.guolindev.extension

import android.view.View
import com.glance.guolindev.view.TableCellView

/**
 * View extension methods.
 * @author guolin
 * @since 2021/5/29
 */

/**
 * Register a callback to be invoked when this view is double clicked. If this view is not
 * clickable, it becomes clickable.
 *
 * @param listener The callback that will run
 */
fun TableCellView.setOnDoubleClickListener(listener: View.OnClickListener) {
    setOnClickListener {
        val clickTimeStamp = System.currentTimeMillis()
        if (clickTimeStamp - firstClickTimeStamp <= 500) {
            // This triggers double click event.
            listener.onClick(this)
        } else {
            firstClickTimeStamp = clickTimeStamp;
        }
    }
}
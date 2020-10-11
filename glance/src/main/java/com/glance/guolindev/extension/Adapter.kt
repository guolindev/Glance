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

package com.glance.guolindev.extension

import androidx.recyclerview.widget.RecyclerView

/**
 * RecyclerView.Adapter extension methods.
 *
 * @author guolin
 * @since 2020/10/11
 */

/**
 * This method will be invoked in the [RecyclerView.Adapter.onBindViewHolder], to give first/last itemView
 * the extra 10dp top/bottom margin to make the space between each item equal.
 */
fun RecyclerView.ViewHolder.setExtraMarginForFirstAndLastItem(firstItem: Boolean, lastItem: Boolean) {
    if (firstItem || lastItem) {
        // We give first/last itemView the extra 10dp top/bottom margin to make the space between each item equal.
        val itemView = itemView
        val params = itemView.layoutParams as RecyclerView.LayoutParams
        if (firstItem) {
            params.topMargin = params.topMargin + 10.dp
        } else {
            params.bottomMargin = params.bottomMargin + 10.dp
        }
    }
}
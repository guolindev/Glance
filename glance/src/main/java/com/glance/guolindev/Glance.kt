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

package com.glance.guolindev

import android.content.Context

/**
 * Global singleton class to provide necessary data.
 *
 * @author guolin
 * @since 2020/8/15
 */
object Glance {

    /**
     * Global application context.
     */
    lateinit var context: Context

    fun initialize(_context: Context) {
        context = _context.applicationContext
    }

}
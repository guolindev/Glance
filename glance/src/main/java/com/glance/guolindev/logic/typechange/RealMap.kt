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

package com.glance.guolindev.logic.typechange

import java.util.*

/**
 * This class deals with real type.
 *
 * @author guolin
 * @since 2020/11/8
 */

const val REAL_FIELD_TYPE = "REAL"

class RealMap : OrmMap {

    private val realTypeList = listOf("REAL", "DOUBLE", "DOUBLE PRECISION", "FLOAT")

    override fun columnType2FieldType(columnType: String): String? {
        return when (columnType.toUpperCase(Locale.US)) {
            in realTypeList -> REAL_FIELD_TYPE
            else -> null
        }
    }

}
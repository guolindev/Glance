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
 * This class deals with text type.
 *
 * @author guolin
 * @since 2020/11/8
 */

const val TEXT_FIELD_TYPE = "TEXT"

class TextMap : OrmMap {

    override fun columnType2FieldType(columnType: String): String? {
        val upperColumnType = columnType.toUpperCase(Locale.US)
        return when {
            upperColumnType == "TEXT" ||
            upperColumnType == "CLOB" ||
            upperColumnType.startsWith("CHARACTER") ||
            upperColumnType.startsWith("VARCHAR") ||
            upperColumnType.startsWith("VARYING") ||
            upperColumnType.startsWith("NCHAR") ||
            upperColumnType.startsWith("NATIVE CHARACTER") ||
            upperColumnType.startsWith("NVARCHAR") -> TEXT_FIELD_TYPE
            else -> null
        }
    }

}
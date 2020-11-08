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

/**
 * This is the interface to map the object field types to database column types. The purpose of this
 * interface is to define a method, and let all subclasses implement it. Each subclass deals the
 * mapping work for one type and each subclass will do their own logic to finish the mapping job.
 *
 * The mapping rule should follow the SQLite3 data types definition in this link:
 * https://www.sqlite.org/datatype3.html
 *
 * @author guolin
 * @since 2020/11/7
 */
interface OrmMap {

    /**
     * Subclasses implement this method to to get corresponding field type with column type.
     */
    fun columnType2FieldType(columnType: String): String?

}
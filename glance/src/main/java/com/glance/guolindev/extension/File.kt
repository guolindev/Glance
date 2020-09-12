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

import com.glance.guolindev.logic.model.DBFile
import java.io.File
import java.io.FileReader
import java.lang.Exception

/**
 * File extension methods.
 * @author guolin
 * @since 2020/8/24
 */

/**
 * Check a file ends with .db or not.
 */
fun File.isDBFile() = name.endsWith(".db")

/**
 * Check the file represented by DBFile exists or not.
 */
fun DBFile.exists() = File(path).exists()

/**
 * Check the file represented by DBFile is valid SQLite db file or not.
 */
fun DBFile.isValidDBFile() = try {
    val reader = FileReader(File(path))
    val buffer = CharArray(16)
    reader.read(buffer, 0, 16)
    val str = String(buffer)
    reader.close()
    str == "SQLite format 3\u0000"
} catch (e: Exception) {
    e.printStackTrace()
    false
}
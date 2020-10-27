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
package com.glance.sample

import android.database.sqlite.SQLiteDatabase
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream

/**
 *
 * @author guolin
 * @since 2020/10/27
 */
@RunWith(AndroidJUnit4::class)
class TableStructureTest {

    @Test
    fun testTableStructure() {
        // Context of the app under test.
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val dbPath = context.getExternalFilesDir("glanceTest")
        val dbFile = File(dbPath, "okgo.db")
        if (!dbFile.exists()) {
            dbFile.createNewFile()
            val assetsManager = context.assets
            val input = assetsManager.open("okgo.db")
            val bis = BufferedInputStream(input)
            val bos = BufferedOutputStream(FileOutputStream(dbFile))
            val bytes = ByteArray(1024)
            var len = bis.read(bytes)
            while (len != -1) {
                bos.write(bytes, 0, len)
                bos.flush()
                len = bis.read(bytes)
            }
            bos.close()
            bis.close()
        }
        val tableList = ArrayList<String>()
        val db = SQLiteDatabase.openDatabase(dbFile.path, null, SQLiteDatabase.OPEN_READWRITE)
        db.rawQuery("select * from sqlite_master where type = ?", arrayOf("table")).use { cursor ->
            if (cursor.moveToFirst()) {
                do {
                    val tableName = cursor.getString(cursor.getColumnIndexOrThrow("tbl_name"))
                    tableList.add(tableName)
                } while (cursor.moveToNext())
            }
        }
        Assert.assertTrue(tableList.containsAll(listOf("android_metadata", "cache", "cookie", "download", "upload")))
        Assert.assertEquals(5, tableList.size)
    }

}
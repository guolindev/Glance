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

package com.glance.guolindev.logic.util

import com.glance.guolindev.Glance
import com.glance.guolindev.extension.isDBFile
import com.glance.guolindev.logic.model.DBFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

/**
 * A utility scanner to scan internal and external storage of current app. Find all db files.
 *
 * @author guolin
 * @since 2020/8/15
 */
class DBScanner {

    /**
     * Scan all db files of the current app. Including internal storage and external storage.
     * @return A db list contains all db files under the app.
     */
    suspend fun scanAllDBFiles() = withContext(Dispatchers.Default) {
        val dbList = ArrayList<DBFile>()
        val dataDir = Glance.context.filesDir.parentFile
        if (dataDir != null) {
            scanDBFilesUnderSpecificDir(dataDir, true, dbList)
        }
        val externalDataDir = Glance.context.getExternalFilesDir("")?.parentFile
        if (externalDataDir != null) {
            scanDBFilesUnderSpecificDir(externalDataDir, false, dbList)
        }
        dbList.sortBy {
            it.modifyTime
        }
        dbList.reversed() // We sort the dbList by reversed modify time. So lasted modified db file will be showed on top.
    }

    /**
     * Scan all the files under specific directory recursively.
     * @param dir
     *          Base directory to scan.
     * @param internal
     *          Indicates this is internal storage or external storage. True means internal, false means external.
     * @param dbList
     *          A db list contains all db files under the specific dir.
     */
    private fun scanDBFilesUnderSpecificDir(dir: File, internal: Boolean, dbList: ArrayList<DBFile>) {
        val listFiles = dir.listFiles()
        if (listFiles != null) {
            for (file in listFiles) {
                if (file.isDirectory) {
                    scanDBFilesUnderSpecificDir(file, internal, dbList)
                } else if (file.isDBFile()) {
                    dbList.add(DBFile(file.name, file.path, internal, Date(file.lastModified())))
                }
            }
        }
    }

}
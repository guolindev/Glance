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
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import java.io.File

/**
 * A utility scanner to scan internal and external storage of current app. Find all db files.
 *
 * @author guolin
 * @since 2020/8/15
 */
object DBScanner {

    /**
     * Scan all db files of the current app. Including internal storage and external storage.
     * Use Flow to emits the db files once find one.
     * @return Flow object to collect and get each db file.
     */
    suspend fun scanAllDBFiles() = flow<DBFile> {
        val dataDir = Glance.context.filesDir.parentFile
        if (dataDir != null) {
            scanDBFilesUnderSpecificDir(dataDir)
        }
        val externalDataDir = Glance.context.getExternalFilesDir("")?.parentFile
        if (externalDataDir != null) {
            scanDBFilesUnderSpecificDir(externalDataDir)
        }
    }

    /**
     * Scan all the files under specific directory recursively.
     * Emits each file ends with .db which consider as a db file.
     * @param dir
     *          Base directory to scan.
     */
    private suspend fun FlowCollector<DBFile>.scanDBFilesUnderSpecificDir(dir: File) {
        val listFiles = dir.listFiles()
        if (listFiles != null) {
            for (file in listFiles) {
                if (file.isDirectory) {
                    scanDBFilesUnderSpecificDir(file)
                } else if (file.isDBFile()) {
                    emit(DBFile(file.name, file.path))
                }
            }
        }
    }

}
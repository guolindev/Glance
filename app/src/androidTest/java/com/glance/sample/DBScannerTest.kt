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

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.glance.guolindev.logic.util.DBScanner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Test for DBScanner class.
 * @author guolin
 * @since 2020/8/24
 */
@RunWith(AndroidJUnit4::class)
class DBScannerTest {

    @Test
    fun scanAllDBFiles() {
        runBlocking {
            DBScanner.scanAllDBFiles()
                .flowOn(Dispatchers.Default)
                .collect {
                    assertTrue(it.name.endsWith(".db"))
                    assertTrue(it.path.endsWith(".db"))
                }
        }
    }

}
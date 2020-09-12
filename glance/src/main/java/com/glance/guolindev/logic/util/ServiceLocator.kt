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

import com.glance.guolindev.logic.repository.FileRepository
import com.glance.guolindev.logic.repository.DatabaseRepository

/**
 * ServiceLocator to provide instances that no one should create.
 * Basically this work should be done by a DI library like hilt, but since we do not charge the Application class, so just keep it simple by a ServiceLocator.
 *
 * @author guolin
 * @since 2020/9/4
 */
object ServiceLocator {

    fun provideDBRepository() = FileRepository(provideDBScanner())

    fun provideTableRepository() = DatabaseRepository(provideDBHelper())

    private fun provideDBScanner() = DBScanner()

    private fun provideDBHelper() = DBHelper()

}
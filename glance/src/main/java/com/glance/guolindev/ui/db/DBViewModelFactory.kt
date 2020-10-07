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

package com.glance.guolindev.ui.db

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.glance.guolindev.logic.util.ServiceLocator

/**
 * The ViewModel Factory to create DBViewModel instance and pass a DBRepository instance as parameter which provided by ServiceLocator.
 *
 * @author guolin
 * @since 2020/9/4
 */
class DBViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DBViewModel(ServiceLocator.provideDBRepository()) as T
    }

}
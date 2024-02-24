/*
 * Copyright 2021-2024 Azim Muradov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.stardewvalleydesigner.component.mainmenu

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.stardewvalleydesigner.designformat.models.Design
import kotlinx.coroutines.*
import kotlinx.coroutines.swing.Swing
import java.nio.file.Files
import kotlin.io.path.Path
import io.stardewvalleydesigner.component.mainmenu.MainMenuIntent as Intent


internal typealias MainMenuStore = Store<Intent, Unit, Nothing>


class MainMenuStoreFactory(private val storeFactory: StoreFactory) {

    fun create(
        onEditorScreenCall: (Design, designPath: String?) -> Unit,
    ): MainMenuStore = object : MainMenuStore by storeFactory.create(
        name = "MainMenuStore",
        initialState = Unit,
        executorFactory = { ExecutorImpl(onEditorScreenCall) },
    ) {}


    private class ExecutorImpl(
        private val onEditorScreenCall: (Design, designPath: String?) -> Unit,
    ) : CoroutineExecutor<Intent, Nothing, Unit, Nothing, Nothing>(mainContext = Dispatchers.Swing) {

        override fun executeIntent(intent: Intent) {
            when (intent) {
                is Intent.UserDesignsMenu.OpenDesign -> {
                    onEditorScreenCall(intent.design, intent.designPath)
                }

                is Intent.UserDesignsMenu.DeleteDesign -> {
                    scope.launch {
                        withContext(Dispatchers.IO) {
                            Files.deleteIfExists(Path(intent.designPath))
                        }
                    }
                }
            }
        }

        override fun executeAction(action: Nothing) = Unit
    }
}

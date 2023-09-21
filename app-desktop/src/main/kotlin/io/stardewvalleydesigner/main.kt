/*
 * Copyright 2021-2023 Azim Muradov
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

package io.stardewvalleydesigner

import androidx.compose.runtime.*
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.application
import com.arkivanov.mvikotlin.core.utils.setMainThreadId
import io.stardewvalleydesigner.components.RootComponent
import io.stardewvalleydesigner.components.rootComponent
import io.stardewvalleydesigner.screens.EditorScreen
import io.stardewvalleydesigner.screens.MainMenuScreen
import io.stardewvalleydesigner.settings.Lang
import io.stardewvalleydesigner.utils.GlobalSettings
import io.stardewvalleydesigner.utils.WithSettings


fun main() {
    val root: RootComponent = runOnUiThread {
        setMainThreadId(Thread.currentThread().id)
        rootComponent()
    }

    application {
        AppTheme(themeVariant = ThemeVariant.LIGHT) {
            WithSettings(lang = Lang.EN) {
                Root(root, ::exitApplication)
            }
        }
    }
}

@Composable
private fun Root(component: RootComponent, exitApplication: () -> Unit) {
    val wordList = GlobalSettings.strings

    val rootChildren by component.children.collectAsState()

    Screen(
        title = wordList.screenTitle(wordList.mainMenuTitle),
        initialSize = DpSize(width = 1000.dp, height = 700.dp),
        onCloseRequest = exitApplication,
    ) {
        MainMenuScreen(rootChildren.mainMenuComponent)
    }

    rootChildren.editorComponents.forEach { editorComponent ->
        key(editorComponent) {
            val layoutType = editorComponent.store.state.map.layout.type
            Screen(
                title = wordList.screenTitle(wordList.layout(layoutType)),
                initialSize = DpSize(width = 1280.dp, height = 720.dp),
                onCloseRequest = { component.destroyEditorComponent(editorComponent) },
            ) {
                EditorScreen(editorComponent)
            }
        }
    }
}

const val ICON_RES_PATH: String = "icons/icon-1024.png"

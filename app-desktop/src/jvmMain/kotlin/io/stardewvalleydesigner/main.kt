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

package io.stardewvalleydesigner

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.application
import com.arkivanov.mvikotlin.core.utils.setMainThreadId
import io.stardewvalleydesigner.component.editor.EditorIntent
import io.stardewvalleydesigner.component.root.RootComponent
import io.stardewvalleydesigner.component.root.rootComponent
import io.stardewvalleydesigner.screens.MainMenuScreen
import io.stardewvalleydesigner.settings.Lang
import io.stardewvalleydesigner.ui.component.editor.EditorScreen
import io.stardewvalleydesigner.ui.component.editor.res.WithImageResources
import io.stardewvalleydesigner.ui.component.editor.screen.topmenu.*
import io.stardewvalleydesigner.ui.component.settings.GlobalSettings
import io.stardewvalleydesigner.ui.component.settings.WithSettings
import io.stardewvalleydesigner.ui.component.themes.AppTheme
import io.stardewvalleydesigner.ui.component.themes.ThemeVariant


fun main() {
    val root: RootComponent = runOnUiThread {
        setMainThreadId(Thread.currentThread().id)
        rootComponent()
    }

    application {
        AppTheme(themeVariant = ThemeVariant.LIGHT) {
            WithSettings(lang = Lang.EN) {
                WithImageResources {
                    Root(root, ::exitApplication)
                }
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
        initialSize = DpSize(width = 900.dp, height = 600.dp),
        onCloseRequest = exitApplication,
    ) {
        MainMenuScreen(rootChildren.mainMenuComponent)
    }

    rootChildren.editorComponents.forEach { editorComponent ->
        key(editorComponent) {
            val layoutType = editorComponent.store.state.map.layout.type
            Screen(
                title = wordList.screenTitle(wordList.layout(layoutType)),
                initialSize = DpSize(width = 1200.dp, height = 750.dp),
                onCloseRequest = { component.destroyEditorComponent(editorComponent) },
                onKeyEvent = {
                    when {
                        it.isCtrlPressed && it.key == Key.Z && it.type == KeyEventType.KeyDown -> {
                            editorComponent.store.accept(EditorIntent.History.GoBack)
                            true
                        }

                        it.isCtrlPressed && it.key == Key.Y && it.type == KeyEventType.KeyDown -> {
                            editorComponent.store.accept(EditorIntent.History.GoForward)
                            true
                        }

                        else -> false
                    }
                },
            ) {
                EditorScreen(
                    editorComponent,
                    rightBottomMenus = { editorState, snackbarHostState ->
                        var designSaveAbsolutePath: String? by remember { mutableStateOf(editorState.designPath) }

                        SaveDesignButton(
                            map = editorState.map,
                            playerName = editorState.playerName,
                            farmName = editorState.farmName,
                            season = editorState.season,
                            palette = editorState.palette,
                            options = editorState.options,
                            snackbarHostState = snackbarHostState,
                            designSaveAbsolutePath = designSaveAbsolutePath,
                        )
                        SaveDesignAsButton(
                            map = editorState.map,
                            playerName = editorState.playerName,
                            farmName = editorState.farmName,
                            season = editorState.season,
                            palette = editorState.palette,
                            options = editorState.options,
                            snackbarHostState = snackbarHostState,
                            designSaveAbsolutePath = designSaveAbsolutePath,
                            onDesignSaveAbsolutePathChanged = { designSaveAbsolutePath = it },
                        )
                        SaveDesignAsImageButton(
                            map = editorState.map,
                            season = editorState.season,
                            visibleLayers = editorState.visLayers,
                            snackbarHostState = snackbarHostState,
                        )

                        Divider(
                            modifier = Modifier.fillMaxHeight(fraction = 0.6f).width(1.dp),
                            color = MaterialTheme.colors.onPrimary.copy(alpha = 0.5f),
                        )

                        HelpButton()
                    }
                )
            }
        }
    }
}

const val ICON_RES_PATH: String = "icons/icon-1024.png"

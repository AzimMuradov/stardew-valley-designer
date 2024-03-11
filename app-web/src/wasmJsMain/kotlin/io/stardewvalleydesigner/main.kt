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

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.input.key.*
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.CanvasBasedWindow
import com.arkivanov.mvikotlin.extensions.coroutines.states
import io.stardewvalleydesigner.LoggerUtils.createLoggerAwareStoreFactory
import io.stardewvalleydesigner.component.dialog.newdesign.NewDesignComponentImpl
import io.stardewvalleydesigner.component.dialog.newdesign.NewDesignState
import io.stardewvalleydesigner.component.dialog.opendesign.OpenDesignComponentImpl
import io.stardewvalleydesigner.component.dialog.opendesign.OpenDesignState
import io.stardewvalleydesigner.component.dialog.opensvsave.OpenSvSaveComponentImpl
import io.stardewvalleydesigner.component.dialog.opensvsave.OpenSvSaveState
import io.stardewvalleydesigner.component.editor.*
import io.stardewvalleydesigner.designformat.models.Design
import io.stardewvalleydesigner.engine.layout.LayoutType
import io.stardewvalleydesigner.engine.layout.LayoutsProvider
import io.stardewvalleydesigner.settings.Lang
import io.stardewvalleydesigner.settings.SettingsInterpreter
import io.stardewvalleydesigner.ui.component.editor.EditorScreen
import io.stardewvalleydesigner.ui.component.editor.res.WithImageResources
import io.stardewvalleydesigner.ui.component.editor.screen.bottommenu.*
import io.stardewvalleydesigner.ui.component.settings.WithSettings
import io.stardewvalleydesigner.ui.component.themes.AppTheme
import io.stardewvalleydesigner.ui.component.themes.ThemeVariant
import io.stardewvalleydesigner.ui.component.windowsize.WithMeasuredWindowSize


@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    removeWaitMsg()

    val lang = Lang.EN

    CanvasBasedWindow(title = SettingsInterpreter.wordList(lang).application) {
        var editorComponent by remember {
            mutableStateOf(
                value = EditorComponentImpl(
                    EditorState.default(LayoutsProvider.layoutOf(LayoutType.StandardFarm))
                ),
            )
        }

        val newDesignComponent by remember {
            mutableStateOf(
                NewDesignComponentImpl { design: Design, designPath: String? ->
                    editorComponent = EditorComponentImpl(
                        EditorState.from(design, designPath)
                    )
                }
            )
        }
        val newDesignStore = newDesignComponent.store
        val newDesignState by newDesignStore.states.collectAsState(newDesignStore.state)

        val openDesignComponent by remember {
            mutableStateOf(
                OpenDesignComponentImpl { design: Design, designPath: String? ->
                    editorComponent = EditorComponentImpl(
                        EditorState.from(design, designPath)
                    )
                }
            )
        }
        val openDesignStore = openDesignComponent.store
        val openDesignState by openDesignStore.states.collectAsState(openDesignStore.state)

        val openSvSaveComponent by remember {
            mutableStateOf(
                OpenSvSaveComponentImpl { design: Design, designPath: String? ->
                    editorComponent = EditorComponentImpl(
                        EditorState.from(design, designPath)
                    )
                }
            )
        }
        val openSvSaveStore = openSvSaveComponent.store
        val openSvSaveState by openSvSaveStore.states.collectAsState(openSvSaveStore.state)

        val show by remember {
            derivedStateOf {
                listOf(
                    newDesignState != NewDesignState.NotOpened,
                    openDesignState != OpenDesignState.NotOpened,
                    openSvSaveState != OpenSvSaveState.NotOpened,
                ).any { it }
            }
        }

        AppTheme(themeVariant = ThemeVariant.LIGHT) {
            WithSettings(lang) {
                WithImageResources(themeVariant = ThemeVariant.LIGHT) {
                    WithMeasuredWindowSize(windowWidth = LocalWindowInfo.current.containerSize.width) {
                        Box(
                            Modifier
                                .fillMaxSize()
                                .then(if (show) Modifier.blur(10.dp) else Modifier)
                                .onKeyEvent {
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
                                }
                        ) {
                            EditorScreen(
                                component = editorComponent,
                                rightBottomMenus = { editorState, snackbarHostState ->
                                    NewDesignButton(
                                        state = newDesignState,
                                        intentConsumer = newDesignStore::accept,
                                    )
                                    OpenDesignButton(
                                        state = openDesignState,
                                        intentConsumer = openDesignStore::accept,
                                    )
                                    OpenSvSaveButton(
                                        state = openSvSaveState,
                                        intentConsumer = openSvSaveStore::accept,
                                    )
                                    Divider(
                                        modifier = Modifier.fillMaxHeight(fraction = 0.6f).width(1.dp),
                                        color = MaterialTheme.colors.onPrimary.copy(alpha = 0.5f),
                                    )
                                    SaveDesignAsButton(
                                        map = editorState.map,
                                        playerName = editorState.playerName,
                                        farmName = editorState.farmName,
                                        season = editorState.season,
                                        palette = editorState.palette,
                                        options = editorState.options,
                                        snackbarHostState = snackbarHostState,
                                        designSaveAbsolutePath = null,
                                        onDesignSaveAbsolutePathChanged = {},
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
        }
    }
}

class EditorComponentImpl(
    initialState: EditorState,
) : EditorComponent {

    override val store = EditorStoreFactory(createLoggerAwareStoreFactory()).create(initialState)
}

fun removeWaitMsg(): Unit = js(
    """
    {
        let waitMsgBlock = document.getElementById("wait-msg");
        if (waitMsgBlock) {
            waitMsgBlock.remove();
        } else {
            console.warn(waitMsgBlock);
        }
    }
    """
)

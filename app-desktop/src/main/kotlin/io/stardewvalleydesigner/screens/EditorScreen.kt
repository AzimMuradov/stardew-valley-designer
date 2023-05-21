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

package io.stardewvalleydesigner.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.arkivanov.mvikotlin.extensions.coroutines.states
import io.stardewvalleydesigner.editor.EditorComponent
import io.stardewvalleydesigner.editor.EditorIntent
import io.stardewvalleydesigner.screens.editor.main.MainPart
import io.stardewvalleydesigner.screens.editor.sidemenus.LeftSideMenus
import io.stardewvalleydesigner.screens.editor.sidemenus.RightSideMenus
import io.stardewvalleydesigner.screens.editor.topmenubar.TopMenuBar


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditorScreen(component: EditorComponent) {
    val store = component.store
    val state by store.states.collectAsState(component.store.state)
    val (history, map, toolkit, palette, /* flavors, */ visLayers, /* clipboard, */ options) = state

    Column(
        modifier = Modifier.fillMaxSize().onKeyEvent {
            when {
                it.isCtrlPressed && it.key == Key.Z && it.type == KeyEventType.KeyUp -> {
                    store.accept(EditorIntent.History.GoBack)
                    true
                }

                it.isCtrlPressed && it.key == Key.Y && it.type == KeyEventType.KeyUp -> {
                    store.accept(EditorIntent.History.GoForward)
                    true
                }

                else -> false
            }
        }
    ) {
        TopMenuBar(
            map = map,
            visibleLayers = visLayers.visibleLayers,
            history = history,
            disallowedTypes = map.layout.disallowedTypes,
            onEntitySelection = { component.store.accept(EditorIntent.Palette.AddToInUse(it)) },
            options = options,
            intentConsumer = store::accept
        )

        Row(Modifier.fillMaxWidth().weight(1f)) {
            LeftSideMenus(
                toolkit = toolkit,
                palette = palette,
                width = SIDE_MENUS_WIDTH,
                intentConsumer = store::accept
            )
            MainPart(
                map = map,
                visibleLayers = visLayers.visibleLayers,
                toolkit = toolkit,
                options = options,
                intentConsumer = store::accept
            )
            RightSideMenus(
                visibleLayers = visLayers.visibleLayers,
                onVisibilityChange = { layerType, visible ->
                    store.accept(EditorIntent.VisLayers.ChangeVisibility(layerType, visible))
                },
                layout = map.layout,
                width = SIDE_MENUS_WIDTH,
                intentConsumer = store::accept
            )
        }
    }
}


private val SIDE_MENUS_WIDTH: Dp = 350.dp

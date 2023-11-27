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

package io.stardewvalleydesigner.ui.component.editor.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arkivanov.mvikotlin.extensions.coroutines.states
import io.stardewvalleydesigner.editor.EditorComponent
import io.stardewvalleydesigner.editor.EditorIntent
import io.stardewvalleydesigner.ui.component.editor.screens.editor.layout.MainPart
import io.stardewvalleydesigner.ui.component.editor.screens.editor.sidemenus.LeftSideMenus
import io.stardewvalleydesigner.ui.component.editor.screens.editor.sidemenus.RightSideMenus
import io.stardewvalleydesigner.ui.component.editor.screens.editor.topmenu.TopMenu


@Composable
fun EditorScreen(component: EditorComponent) {
    val store = component.store
    val state by store.states.collectAsState(component.store.state)
    val (history, map, toolkit, palette, /* flavors, */ visLayers, /* clipboard, */ options, planPath) = state

    val snackbarHostState = remember { SnackbarHostState() }

    Column(Modifier.fillMaxSize()) {
        TopMenu(
            map = map,
            visibleLayers = visLayers.visibleLayers,
            history = history,
            disallowedTypes = map.layout.disallowedTypes,
            onEntitySelection = { component.store.accept(EditorIntent.Palette.AddToInUse(it)) },
            options = options,
            snackbarHostState = snackbarHostState,
            intentConsumer = store::accept,
            planPath = planPath,
        )

        val menuWidth = 350.dp

        Row(Modifier.fillMaxWidth().weight(1f)) {
            LeftSideMenus(
                toolkit = toolkit,
                palette = palette,
                entities = map.entities,
                options = options,
                width = menuWidth,
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
                width = menuWidth,
                intentConsumer = store::accept
            )
        }
    }

    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.weight(1f))

        SnackbarHost(snackbarHostState) { data ->
            Snackbar(
                modifier = when (data.message.length) {
                    in 0..<10 -> Modifier.fillMaxWidth(0.2f)
                    in 10..<30 -> Modifier.fillMaxWidth(0.4f)
                    else -> Modifier.fillMaxWidth(0.6f)
                }.padding(12.dp),
                content = {
                    Text(
                        text = data.message,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
            )
        }
    }
}

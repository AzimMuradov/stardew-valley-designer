/*
 * Copyright 2021-2021 Azim Muradov
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

package me.azimmuradov.svc.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import me.azimmuradov.svc.cartographer.wishes.SvcWish
import me.azimmuradov.svc.components.screens.Cartographer
import me.azimmuradov.svc.screens.cartographer.main.SvcLayout
import me.azimmuradov.svc.screens.cartographer.sidemenus.LeftSideMenus
import me.azimmuradov.svc.screens.cartographer.sidemenus.RightSideMenus
import me.azimmuradov.svc.screens.cartographer.topmenubar.TopMenuBar


@Composable
fun CartographerUi(component: Cartographer) {
    val svc = component.svc
    val options = component.options
    val settings = component.settings

    val state by svc.state.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        TopMenuBar(
            history = state.history,
            disallowedTypes = state.editor.layout.disallowedTypes,
            onEntitySelection = { svc.consume(SvcWish.Palette.AddToInUse(it)) },
            options = options,
            lang = settings.lang,
            wishConsumer = svc::consume
        )

        Row(Modifier.fillMaxWidth().weight(1f)) {
            LeftSideMenus(
                toolkit = state.toolkit,
                palette = state.palette,
                lang = settings.lang,
                width = SIDE_MENUS_WIDTH,
                wishConsumer = svc::consume
            )
            SvcLayout(
                layoutSize = state.editor.layout.size,
                visibleEntities = state.editor.entities.filter { (layerType) ->
                    layerType in state.editor.visibleLayers
                },
                heldEntities = state.editor.heldEntities,
                chosenEntities = state.editor.chosenEntities,
                toolkit = state.toolkit,
                options = options,
                lang = settings.lang,
                wishConsumer = svc::consume
            )
            RightSideMenus(
                visibleLayers = state.editor.visibleLayers,
                onVisibilityChange = { layerType, visible ->
                    svc.consume(SvcWish.VisibilityLayers.ChangeVisibility(layerType, visible))
                },
                layout = state.editor.layout,
                entities = state.editor.entities,
                lang = settings.lang,
                width = SIDE_MENUS_WIDTH
            )
        }
    }
}


private val SIDE_MENUS_WIDTH: Dp = 350.dp
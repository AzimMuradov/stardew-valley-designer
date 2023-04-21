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

package me.azimmuradov.svc.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.arkivanov.mvikotlin.extensions.coroutines.states
import me.azimmuradov.svc.cartographer.CartographerComponent
import me.azimmuradov.svc.cartographer.CartographerIntent
import me.azimmuradov.svc.cartographer.res.LayoutSpritesProvider
import me.azimmuradov.svc.engine.layers.layeredEntitiesData
import me.azimmuradov.svc.screens.cartographer.main.SvcLayout
import me.azimmuradov.svc.screens.cartographer.sidemenus.LeftSideMenus
import me.azimmuradov.svc.screens.cartographer.sidemenus.RightSideMenus
import me.azimmuradov.svc.screens.cartographer.topmenubar.TopMenuBar


@Composable
fun CartographerScreen(component: CartographerComponent) {
    val store = component.store
    val state by store.states.collectAsState(component.store.state)
    val (history, map, toolkit, palette, /* flavors, */ visLayers, /* clipboard, */ options) = state

    Column(modifier = Modifier.fillMaxSize()) {
        TopMenuBar(
            history = history,
            disallowedTypes = map.layout.disallowedTypes,
            onEntitySelection = { component.store.accept(CartographerIntent.Palette.AddToInUse(it)) },
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
            SvcLayout(
                layoutSprite = LayoutSpritesProvider.layoutSpriteBy(map.layout.type),
                layoutSize = map.layout.size,
                visibleEntities = run {
                    val all = map.entities.all.filter { (layerType) ->
                        layerType in visLayers.visibleLayers
                    }.toMap()
                    layeredEntitiesData { all.getOrDefault(key = it, defaultValue = setOf()) }
                },
                selectedEntities = map.selectedEntities,
                toolkit = toolkit,
                options = options,
                intentConsumer = store::accept
            )
            RightSideMenus(
                visibleLayers = visLayers.visibleLayers,
                onVisibilityChange = { layerType, visible ->
                    store.accept(CartographerIntent.VisLayers.ChangeVisibility(layerType, visible))
                },
                layout = map.layout,
                entities = map.entities,
                width = SIDE_MENUS_WIDTH
            )
        }
    }
}


private val SIDE_MENUS_WIDTH: Dp = 350.dp

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import me.azimmuradov.svc.components.screens.Cartographer
import me.azimmuradov.svc.engine.layers.layeredEntities
import me.azimmuradov.svc.screens.cartographer.main.SvcLayout
import me.azimmuradov.svc.screens.cartographer.sidemenus.LeftSideMenus
import me.azimmuradov.svc.screens.cartographer.sidemenus.RightSideMenus
import me.azimmuradov.svc.screens.cartographer.topmenubar.TopMenuBar


@Composable
fun CartographerUi(component: Cartographer) {
    val svc = component.svc
    val options = component.options
    val settings = component.settings

    Column(modifier = Modifier.fillMaxSize()) {
        TopMenuBar(
            history = svc.history,
            disallowedTypes = svc.layout.disallowedTypes,
            onEntitySelection = svc::useInPalette,
            options = options,
            lang = settings.lang,
        )

        Row(Modifier.fillMaxWidth().weight(1f)) {
            LeftSideMenus(
                toolInfo = svc.tool,
                onToolSelection = svc::chooseToolOf,
                palette = svc.palette,
                lang = settings.lang,
                width = SIDE_MENUS_WIDTH,
            )
            SvcLayout(
                layoutSize = svc.layout.size,
                visibleEntities = layeredEntities {
                    if (it in svc.visibleLayers) svc.entities.entitiesBy(it) else listOf()
                },
                heldEntities = svc.heldEntities,
                tool = svc.tool,
                options = options,
                lang = settings.lang,
            )
            RightSideMenus(
                visibleLayers = svc.visibleLayers,
                onVisibilityChange = svc::changeVisibilityBy,
                layoutSize = svc.layout.size,
                entities = svc.entities,
                lang = settings.lang,
                width = SIDE_MENUS_WIDTH,
            )
        }
    }
}


private val SIDE_MENUS_WIDTH: Dp = 350.dp
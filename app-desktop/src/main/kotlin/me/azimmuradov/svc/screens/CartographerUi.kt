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
import me.azimmuradov.svc.screens.cartographer.*


@Composable
fun CartographerUi(component: Cartographer) {
    val svc = component.svc
    val options = component.options
    val settings = component.settings

    Column(modifier = Modifier.fillMaxSize()) {
        TopMenuBar(
            onEntitySelection = { id -> svc.palette.putInUse(id) },
            options = options,
            language = settings.language,
        )

        Row(Modifier.fillMaxWidth().weight(1f)) {
            LeftSideMenus(
                svc = svc,
                settings = settings,
                width = SIDE_MENUS_WIDTH,
            )
            SvcLayout(
                svc = svc,
                options = options,
                settings = settings,
            )
            RightSideMenus(
                svc = svc,
                settings = settings,
                width = SIDE_MENUS_WIDTH,
            )
        }
    }
}


private val SIDE_MENUS_WIDTH: Dp = 300.dp
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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.azimmuradov.svc.components.screens.MainMenuComponent
import me.azimmuradov.svc.screens.mainmenu.LeftSideMenu
import me.azimmuradov.svc.screens.mainmenu.RightSideMenu


@Composable
fun MainMenuUi(component: MainMenuComponent) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(10.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        LeftSideMenu()
        RightSideMenu(component.onCartographerScreenCall)
    }
}

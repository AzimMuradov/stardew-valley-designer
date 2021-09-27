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

package me.azimmuradov.svc.screens.cartographer

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.azimmuradov.svc.components.screens.Cartographer
import me.azimmuradov.svc.screens.cartographer.left.Palette
import me.azimmuradov.svc.screens.cartographer.left.Toolbar


@Composable
fun LeftSideMenus(
    model: Cartographer.Model,
    modifier: Modifier = Modifier,
) {
    SideMenus(modifier) {
        menu { Toolbar(model.svc.toolkit, model.settings.language) }
        menu { Palette(model.svc.palette, model.settings.language) }
        // TODO : menu(Modifier.weight(1f)) { Stub(text = "Clipboard") }
    }
}
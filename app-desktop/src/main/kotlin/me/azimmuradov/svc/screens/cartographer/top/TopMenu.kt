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

package me.azimmuradov.svc.screens.cartographer.top

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.azimmuradov.svc.components.screens.Cartographer
import me.azimmuradov.svc.components.screens.cartographer.SessionSettings
import me.azimmuradov.svc.components.screens.cartographer.menus.MainOptionsMenu
import me.azimmuradov.svc.components.screens.cartographer.menus.entityselection.*


@Composable
fun TopMenu(
    model: Cartographer.Model,
    updateSessionSettings: (SessionSettings) -> Unit,
    modifier: Modifier = Modifier,
) {
    val language = model.settings.language

    Row(modifier) {
        // TODO : File Menu (it is working title)
        FileMenu(model, updateSessionSettings, menu = MainOptionsMenu)
        Spacer(Modifier.weight(1f))
        EntitySelectionMenu(model.svc.palette, languageCartographer = language.cartographer, menu = BuildingsMenu)
        Spacer(Modifier.weight(1f))
        EntitySelectionMenu(model.svc.palette, languageCartographer = language.cartographer, menu = CommonEquipmentMenu)
        Spacer(Modifier.weight(1f))
        EntitySelectionMenu(model.svc.palette, languageCartographer = language.cartographer, menu = FurnitureMenu)
        Spacer(Modifier.weight(1f))
        EntitySelectionMenu(model.svc.palette, languageCartographer = language.cartographer, menu = FarmElementsMenu)
        Spacer(Modifier.weight(1f))
        EntitySelectionMenu(model.svc.palette, languageCartographer = language.cartographer, menu = TerrainElementsMenu)
        Spacer(Modifier.weight(1f))
        // TODO : Entity Search
        // TODO : Layout Selection Menu
        OptionsMenu(model, updateSessionSettings, menu = MainOptionsMenu)
    }
}
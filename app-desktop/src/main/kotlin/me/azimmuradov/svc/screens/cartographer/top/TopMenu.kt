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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.azimmuradov.svc.components.screens.cartographer.Options
import me.azimmuradov.svc.components.screens.cartographer.menus.MainOptionsMenu
import me.azimmuradov.svc.components.screens.cartographer.menus.entityselection.*
import me.azimmuradov.svc.engine.entity.ids.EntityId
import me.azimmuradov.svc.settings.languages.Language


@Composable
fun TopMenu(
    onEntitySelection: (EntityId<*>) -> Unit,
    options: Options,
    language: Language,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        // TODO : File Menu

        EntitySelectionMenu(
            menu = BuildingsMenu,
            onEntitySelection = onEntitySelection,
            language = language,
        )

        EntitySelectionMenu(
            menu = CommonEquipmentMenu,
            onEntitySelection = onEntitySelection,
            language = language,
        )

        EntitySelectionMenu(
            menu = FurnitureMenu,
            onEntitySelection = onEntitySelection,
            language = language,
        )

        EntitySelectionMenu(
            menu = FarmElementsMenu,
            onEntitySelection = onEntitySelection,
            language = language,
        )

        EntitySelectionMenu(
            menu = TerrainElementsMenu,
            onEntitySelection = onEntitySelection,
            language = language,
        )

        // TODO : Entity Search

        // TODO : Layout Selection Menu

        OptionsMenu(options, language, menu = MainOptionsMenu)
    }
}
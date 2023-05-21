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

package io.stardewvalleydesigner.screens.editor.topmenubar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.stardewvalleydesigner.editor.EditorIntent
import io.stardewvalleydesigner.editor.menus.MainOptionsMenu
import io.stardewvalleydesigner.editor.menus.entityselection.*
import io.stardewvalleydesigner.editor.modules.history.HistoryState
import io.stardewvalleydesigner.editor.modules.map.MapState
import io.stardewvalleydesigner.editor.modules.options.OptionsState
import io.stardewvalleydesigner.engine.entity.Entity
import io.stardewvalleydesigner.engine.entity.EntityType
import io.stardewvalleydesigner.engine.layer.LayerType


@Composable
fun TopMenu(
    map: MapState,
    visibleLayers: Set<LayerType<*>>,
    history: HistoryState,
    disallowedTypes: Set<EntityType>,
    onEntitySelection: (Entity<*>) -> Unit,
    options: OptionsState,
    modifier: Modifier = Modifier,
    intentConsumer: (EditorIntent) -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        // TODO : File Menu

        History(
            history = history,
            intentConsumer = intentConsumer
        )

        EntitySelectionMenu(
            menu = BuildingsMenu,
            disallowedTypes = disallowedTypes,
            onEntitySelection = onEntitySelection,
        )

        EntitySelectionMenu(
            menu = CommonEquipmentMenu,
            disallowedTypes = disallowedTypes,
            onEntitySelection = onEntitySelection,
        )

        EntitySelectionMenu(
            menu = FurnitureMenu,
            disallowedTypes = disallowedTypes,
            onEntitySelection = onEntitySelection,
        )

        EntitySelectionMenu(
            menu = FarmElementsMenu,
            disallowedTypes = disallowedTypes,
            onEntitySelection = onEntitySelection,
        )

        EntitySelectionMenu(
            menu = TerrainElementsMenu,
            disallowedTypes = disallowedTypes,
            onEntitySelection = onEntitySelection,
        )

        // TODO : Entity Search

        ScreenshotButton(map, visibleLayers)

        OptionsMenu(
            options = options,
            menu = MainOptionsMenu,
            intentConsumer = intentConsumer
        )
    }
}

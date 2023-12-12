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

package io.stardewvalleydesigner.screens.editor.topmenu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
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
    snackbarHostState: SnackbarHostState,
    intentConsumer: (EditorIntent) -> Unit,
    planPath: String?,
) {
    Row(
        modifier = Modifier
            .shadow(elevation = 4.dp)
            .fillMaxWidth().height(56.dp)
            .background(color = MaterialTheme.colors.primary)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        History(history, intentConsumer)

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

        Row(verticalAlignment = Alignment.CenterVertically) {
            SavePlanAsImageButton(map, visibleLayers, snackbarHostState)
            Spacer(Modifier.size(4.dp))
            SavePlanButton(map, snackbarHostState, planPath)
            Spacer(Modifier.size(8.dp))
            OptionsMenu(
                options = options,
                menu = MainOptionsMenu,
                intentConsumer = intentConsumer
            )
        }
    }
}

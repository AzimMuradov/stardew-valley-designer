/*
 * Copyright 2021-2024 Azim Muradov
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

package io.stardewvalleydesigner.ui.component.editor.screen.topmenu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import io.stardewvalleydesigner.component.editor.EditorIntent
import io.stardewvalleydesigner.component.editor.menus.MainOptionsMenu
import io.stardewvalleydesigner.component.editor.menus.entityselection.*
import io.stardewvalleydesigner.component.editor.modules.history.HistoryState
import io.stardewvalleydesigner.data.Season
import io.stardewvalleydesigner.designformat.models.Options
import io.stardewvalleydesigner.engine.entity.Entity
import io.stardewvalleydesigner.engine.entity.EntityType


@Composable
internal fun TopMenu(
    history: HistoryState,
    season: Season,
    disallowedTypes: Set<EntityType>,
    onEntitySelection: (Entity<*>) -> Unit,
    options: Options,
    intentConsumer: (EditorIntent) -> Unit,
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
            season,
            menu = BuildingsMenu,
            disallowedTypes = disallowedTypes,
            onEntitySelection = onEntitySelection,
        )

        EntitySelectionMenu(
            season,
            menu = CommonEquipmentMenu,
            disallowedTypes = disallowedTypes,
            onEntitySelection = onEntitySelection,
        )

        EntitySelectionMenu(
            season,
            menu = FurnitureMenu,
            disallowedTypes = disallowedTypes,
            onEntitySelection = onEntitySelection,
        )

        EntitySelectionMenu(
            season,
            menu = FarmElementsMenu,
            disallowedTypes = disallowedTypes,
            onEntitySelection = onEntitySelection,
        )

        EntitySelectionMenu(
            season,
            menu = TerrainElementsMenu,
            disallowedTypes = disallowedTypes,
            onEntitySelection = onEntitySelection,
        )

        OptionsMenu(
            options = options,
            menu = MainOptionsMenu,
            intentConsumer = intentConsumer
        )
    }
}

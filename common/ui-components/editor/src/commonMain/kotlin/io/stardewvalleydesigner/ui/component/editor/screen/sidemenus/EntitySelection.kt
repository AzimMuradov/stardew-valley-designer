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

package io.stardewvalleydesigner.ui.component.editor.screen.sidemenus

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.stardewvalleydesigner.data.Season
import io.stardewvalleydesigner.engine.entity.Entity
import io.stardewvalleydesigner.engine.entity.EntityType
import io.stardewvalleydesigner.ui.component.editor.res.Sprite


@Composable
internal fun EntitySelection(
    state: LazyListState,
    rowCapacity: UInt,
    entities: List<Entity<*>>,
    season: Season,
    disallowedTypes: Set<EntityType>,
    onEntitySelection: (Entity<*>) -> Unit,
) {
    val filteredEntities: List<Entity<*>> = remember(entities, disallowedTypes) {
        entities.filterNot {
            it.type in disallowedTypes
        }
    }

    Box(Modifier.fillMaxSize()) {
        EntitiesGrid(
            modifier = Modifier.fillMaxSize().padding(end = 12.dp),
            state = state,
            rowCapacity = rowCapacity,
            entities = filteredEntities,
            season = season,
            onEntitySelection = onEntitySelection,
        )
        VerticalScrollbar(
            adapter = rememberScrollbarAdapter(scrollState = state),
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight()
        )
    }
}

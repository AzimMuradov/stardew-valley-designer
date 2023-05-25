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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.stardewvalleydesigner.editor.EditorIntent
import io.stardewvalleydesigner.editor.modules.history.HistoryState
import io.stardewvalleydesigner.editor.modules.map.MapState
import io.stardewvalleydesigner.editor.modules.options.OptionsState
import io.stardewvalleydesigner.engine.entity.Entity
import io.stardewvalleydesigner.engine.entity.EntityType
import io.stardewvalleydesigner.engine.layer.LayerType


@Composable
fun TopMenuBar(
    map: MapState,
    visibleLayers: Set<LayerType<*>>,
    history: HistoryState,
    disallowedTypes: Set<EntityType>,
    onEntitySelection: (Entity<*>) -> Unit,
    options: OptionsState,
    intentConsumer: (EditorIntent) -> Unit,
) {
    Surface(elevation = 4.dp) {
        Column(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth().height(24.dp)
                    .background(color = MaterialTheme.colors.primaryVariant),
            )
            TopMenu(
                map = map,
                visibleLayers = visibleLayers,
                history = history,
                disallowedTypes = disallowedTypes,
                onEntitySelection = onEntitySelection,
                options = options,
                modifier = Modifier
                    .fillMaxWidth().height(56.dp)
                    .background(color = MaterialTheme.colors.primary)
                    .padding(horizontal = 16.dp),
                intentConsumer = intentConsumer
            )
        }
    }
}
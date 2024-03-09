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

package io.stardewvalleydesigner.ui.component.editor.screen.layout

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.stardewvalleydesigner.component.editor.EditorIntent
import io.stardewvalleydesigner.component.editor.modules.map.MapState
import io.stardewvalleydesigner.component.editor.modules.toolkit.ToolkitState
import io.stardewvalleydesigner.data.Season
import io.stardewvalleydesigner.designformat.models.Options
import io.stardewvalleydesigner.engine.geometry.Coordinate
import io.stardewvalleydesigner.engine.layer.LayerType


@Composable
internal fun RowScope.MainPart(
    map: MapState,
    season: Season,
    visibleLayers: Set<LayerType<*>>,
    toolkit: ToolkitState,
    options: Options,
    currCoordinate: Coordinate,
    onCurrCoordinateChanged: (Coordinate) -> Unit,
    scale: Float,
    onScaleChanged: (Float) -> Unit,
    intentConsumer: (EditorIntent) -> Unit,
) {
    Box(Modifier.fillMaxHeight().weight(1f).padding(horizontal = 24.dp), Alignment.Center) {
        EditorLayout(
            map, season, visibleLayers, toolkit, options,
            currCoordinate, onCurrCoordinateChanged,
            scale, onScaleChanged,
            intentConsumer,
        )
    }
}

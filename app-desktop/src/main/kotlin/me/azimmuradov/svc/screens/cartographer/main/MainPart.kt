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

package me.azimmuradov.svc.screens.cartographer.main

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.azimmuradov.svc.cartographer.CartographerIntent
import me.azimmuradov.svc.cartographer.modules.map.MapState
import me.azimmuradov.svc.cartographer.modules.options.OptionsState
import me.azimmuradov.svc.cartographer.modules.toolkit.ToolkitState
import me.azimmuradov.svc.engine.layer.LayerType


@Composable
fun RowScope.SvcLayout(
    map: MapState,
    visibleLayers: Set<LayerType<*>>,
    toolkit: ToolkitState,
    options: OptionsState,
    intentConsumer: (CartographerIntent) -> Unit,
) {
    Box(modifier = Modifier.fillMaxHeight().weight(1f).padding(30.dp)) {
        EditorLayout(
            map = map,
            visibleLayers = visibleLayers,
            toolkit = toolkit,
            options = options,
            intentConsumer = intentConsumer
        )
    }
}

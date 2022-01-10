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

package me.azimmuradov.svc.screens.cartographer.main

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.azimmuradov.svc.cartographer.state.ToolkitState
import me.azimmuradov.svc.cartographer.wishes.SvcWish
import me.azimmuradov.svc.components.screens.cartographer.Options
import me.azimmuradov.svc.engine.geometry.Rect
import me.azimmuradov.svc.engine.layers.LayeredEntitiesData


@Composable
fun RowScope.SvcLayout(
    layoutSize: Rect,
    visibleEntities: LayeredEntitiesData,
    toolkit: ToolkitState,
    options: Options,
    wishConsumer: (SvcWish) -> Unit,
) {
    Box(modifier = Modifier.fillMaxHeight().weight(1f).padding(30.dp)) {
        EditorLayout(
            layoutSize = layoutSize,
            visibleEntities = visibleEntities,
            toolkit = toolkit,
            options = options,
            wishConsumer = wishConsumer
        )
    }
}
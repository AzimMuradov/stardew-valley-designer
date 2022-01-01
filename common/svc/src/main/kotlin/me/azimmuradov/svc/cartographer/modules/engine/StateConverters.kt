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

package me.azimmuradov.svc.cartographer.modules.engine

import me.azimmuradov.svc.cartographer.state.editor.LayoutState
import me.azimmuradov.svc.engine.entity.PlacedEntity
import me.azimmuradov.svc.engine.layer.LayerType
import me.azimmuradov.svc.engine.layers.Layers
import me.azimmuradov.svc.engine.layout.Layout

internal fun Layers.toEntitiesState(): List<Pair<LayerType<*>, List<PlacedEntity<*>>>> =
    all.map { (layerType, layer) ->
        layerType to layer.objects.toList()
    }

internal fun Layout.toState(): LayoutState = LayoutState(
    size = size,
    disallowedTypes = disallowedTypes.toSet(),
    disallowedTypesMap = disallowedTypesMap.toMap(),
    disallowedCoordinates = disallowedCoordinates.toSet()
)
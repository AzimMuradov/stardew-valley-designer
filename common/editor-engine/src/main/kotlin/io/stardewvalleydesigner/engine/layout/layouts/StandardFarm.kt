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

package io.stardewvalleydesigner.engine.layout.layouts

import io.stardewvalleydesigner.engine.entity.FloorFurnitureType
import io.stardewvalleydesigner.engine.entity.ObjectType
import io.stardewvalleydesigner.engine.geometry.rectOf
import io.stardewvalleydesigner.engine.layout.Layout
import io.stardewvalleydesigner.engine.layout.LayoutType


internal val StandardFarmLayout: Layout = Layout(
    type = LayoutType.StandardFarm,
    size = rectOf(w = 80, h = 65),
    disallowedTypes = setOf(
        FloorFurnitureType,
        ObjectType.FurnitureType.HouseFurnitureType,
        ObjectType.FurnitureType.IndoorFurnitureType,
    ),
    disallowedTypesMap = emptyMap(),
    disallowedCoordinates = emptySet(),
)

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

package io.stardewvalleydesigner.engine.layout.layouts

import io.stardewvalleydesigner.engine.entity.EntityWithoutFloorType
import io.stardewvalleydesigner.engine.entity.ObjectType.FurnitureType.HouseFurnitureType
import io.stardewvalleydesigner.engine.geometry.*
import io.stardewvalleydesigner.engine.layout.Layout
import io.stardewvalleydesigner.engine.layout.LayoutType


internal val ShedLayout: Layout by lazy {
    shedLayoutOf(
        type = LayoutType.Shed,
        size = rectOf(w = 13, h = 14)
    )
}

internal val BigShedLayout: Layout by lazy {
    shedLayoutOf(
        type = LayoutType.BigShed,
        size = rectOf(w = 19, h = 17)
    )
}


private fun shedLayoutOf(type: LayoutType, size: Rect) = Layout(
    type,
    size,
    disallowedTypes = setOf(HouseFurnitureType) + EntityWithoutFloorType.all,
    disallowedTypesMap = mapOf(),
    disallowedCoordinates = buildSet {
        this += (0 until size.w).flatMap { x ->
            listOf(0, 1, 2, 3, size.h - 1).map { y ->
                xy(x, y)
            }
        }
        this += (0 until size.h).flatMap { y ->
            listOf(0, size.w - 1).map { x ->
                xy(x, y)
            }
        }
        this -= xy(x = size.w / 2, y = size.h - 1)
    }
)

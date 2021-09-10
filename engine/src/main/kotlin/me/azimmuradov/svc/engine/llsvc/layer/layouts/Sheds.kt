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

package me.azimmuradov.svc.engine.llsvc.layer.layouts

import me.azimmuradov.svc.engine.Rect
import me.azimmuradov.svc.engine.llsvc.entity.EntityWithoutFloorType
import me.azimmuradov.svc.engine.llsvc.entity.ObjectType.FurnitureType.HouseFurnitureType
import me.azimmuradov.svc.engine.llsvc.layer.SvcLayout
import me.azimmuradov.svc.engine.xy


internal val ShedLayout: SvcLayout = shedLayoutOf(size = Rect(w = 11, h = 10))

internal val BigShedLayout: SvcLayout = shedLayoutOf(size = Rect(w = 17, h = 13))


private fun shedLayoutOf(size: Rect) = SvcLayout(
    size,
    disallowedTypes = setOf(HouseFurnitureType) + EntityWithoutFloorType.all,
    disallowedTypesMap = mapOf(),
    disallowedCoordinates = ((0 until size.w) - size.w / 2).map { x -> xy(x, y = size.h - 1) }.toSet(),
)
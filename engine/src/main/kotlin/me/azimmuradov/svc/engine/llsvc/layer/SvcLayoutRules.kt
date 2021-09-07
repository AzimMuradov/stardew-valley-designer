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

package me.azimmuradov.svc.engine.llsvc.layer

import me.azimmuradov.svc.engine.Coordinate
import me.azimmuradov.svc.engine.Rect
import me.azimmuradov.svc.engine.llsvc.contains
import me.azimmuradov.svc.engine.llsvc.entity.SvcEntityType


class SvcLayoutRules(
    size: Rect,
    val disallowedTypes: Set<SvcEntityType> = setOf(),
    val disallowedTypesMap: Map<Coordinate, Set<SvcEntityType>> = mapOf(),
    val disallowedCoordinates: Set<Coordinate> = setOf(),
) {

    init {
        require(disallowedTypesMap.keys in size && disallowedCoordinates in size) {
            "Wrong `SvcLayoutRules` definition"
        }
    }
}
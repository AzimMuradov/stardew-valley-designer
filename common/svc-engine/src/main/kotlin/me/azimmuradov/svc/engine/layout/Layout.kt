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

package me.azimmuradov.svc.engine.layout

import me.azimmuradov.svc.engine.contains
import me.azimmuradov.svc.engine.entity.EntityType
import me.azimmuradov.svc.engine.entity.PlacedEntity
import me.azimmuradov.svc.engine.geometry.*


/**
 * SVC layout.
 */
class Layout(
    val type: LayoutType,
    val size: Rect,
    disallowedTypes: Set<EntityType> = setOf(),
    disallowedTypesMap: Map<Coordinate, Set<EntityType>> = mapOf(),
    disallowedCoordinates: Set<Coordinate> = setOf(),
) : LayoutRules(size, disallowedTypes, disallowedTypesMap, disallowedCoordinates)


/**
 * SVC layout rules.
 */
open class LayoutRules internal constructor(
    size: Rect,
    val disallowedTypes: Set<EntityType>,
    val disallowedTypesMap: Map<Coordinate, Set<EntityType>>,
    val disallowedCoordinates: Set<Coordinate>,
) {

    init {
        require(disallowedTypesMap.keys in size && disallowedCoordinates in size) {
            "Wrong `LayoutRules` definition."
        }
    }
}


/**
 * Returns `true` if [this] placed entity respects the given [layoutRules].
 */
infix fun <EType : EntityType> PlacedEntity<EType>.respects(layoutRules: LayoutRules): Boolean {
    val (entity, _, coordinates) = this

    val requirements = sequence {
        with(layoutRules) {
            yield(entity.type !in disallowedTypes)
            yield(coordinates.mapNotNull(disallowedTypesMap::get).none { entity.type in it })
            yield(coordinates.none(disallowedCoordinates::contains))
        }
    }

    return requirements.all { it }
}

/**
 * Returns `true` if [this] placed entity respects the given [layoutRules].
 */
infix fun <EType : EntityType> PlacedEntity<EType>.respectsLayout(layout: Layout): Boolean =
    coordinates in layout.size && respects(layoutRules = layout)


// Private utils

private operator fun Rect.contains(coordinates: Iterable<Coordinate>): Boolean {
    // Check that iterable is not empty
    val first = coordinates.firstOrNull() ?: return true

    val (min, max) = run {
        var (minX: Int, minY: Int) = first
        var (maxX: Int, maxY: Int) = first

        for ((x, y) in coordinates) {
            minX = minOf(minX, x)
            maxX = maxOf(maxX, x)
            minY = minOf(minY, y)
            maxY = maxOf(maxY, y)
        }

        xy(minX, minY) to xy(maxX, maxY)
    }

    return contains(min) && contains(max)
}

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

package io.stardewvalleydesigner.engine.layout

import io.stardewvalleydesigner.engine.entity.EntityType
import io.stardewvalleydesigner.engine.geometry.Coordinate
import io.stardewvalleydesigner.engine.geometry.Rect
import io.stardewvalleydesigner.engine.layer.PlacedEntity
import io.stardewvalleydesigner.engine.layer.contains


/**
 * Editor layout.
 */
class Layout(
    val type: LayoutType,
    val area: Rect,
    val disallowedTypes: Set<EntityType> = setOf(),
    val disallowedTypesMap: Map<Coordinate, Set<EntityType>> = mapOf(),
    val disallowedCoordinates: Set<Coordinate> = setOf(),
) {

    init {
        require(disallowedTypesMap.keys in area && disallowedCoordinates in area) {
            "Wrong layout definition."
        }
    }


    private companion object {

        operator fun Rect.contains(coordinates: Iterable<Coordinate>): Boolean =
            coordinates.all { (x, y) -> x < w && y < h }
    }
}


/**
 * Returns `true` if [this] placed entity respects the given [layout].
 */
infix fun <T : EntityType> PlacedEntity<T>.respects(layout: Layout): Boolean {
    val placedEntity = this
    val (entity, _, coordinates) = placedEntity
    val requirements = sequence {
        yield(placedEntity in layout.area)
        yield(entity.type !in layout.disallowedTypes)
        yield(
            coordinates
                .mapNotNull(layout.disallowedTypesMap::get)
                .none { entity.type in it }
        )
        yield(coordinates.none(layout.disallowedCoordinates::contains))
    }
    return requirements.all { it }
}

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

package io.stardewvalleydesigner.engine.layer

import io.stardewvalleydesigner.engine.contains
import io.stardewvalleydesigner.engine.entity.Entity
import io.stardewvalleydesigner.engine.entity.EntityType
import io.stardewvalleydesigner.engine.geometry.*


/**
 * Placed entity.
 */
data class PlacedEntity<out T : EntityType>(
    val entity: Entity<T>,
    val place: Coordinate,
) {

    val coordinates: List<Coordinate> by lazy {
        val (x, y) = place
        val (w, h) = entity.size

        val xs = x until x + w
        val ys = y until y + h

        (xs * ys).map(Pair<Int, Int>::toCoordinate)
    }

    operator fun component3(): List<Coordinate> = coordinates


    private companion object {

        // Cartesian product
        private operator fun <A, B> Iterable<A>.times(other: Iterable<B>): List<Pair<A, B>> =
            flatMap { a -> other.map { b -> a to b } }
    }
}

/**
 * Static factory function for [PlacedEntity] creation.
 */
fun Entity<*>.placeIt(there: Coordinate): PlacedEntity<*> =
    PlacedEntity(entity = this, place = there)


// Utilities

val <T : EntityType> PlacedEntity<T>.type get() = entity.type

val PlacedEntity<*>.corners: CanonicalCorners
    get() = CanonicalCorners(
        bottomLeft = place,
        topRight = xy(
            x = place.x + entity.size.w - 1,
            y = place.y + entity.size.h - 1,
        )
    )

/**
 * Returns `true` if [entity] is contained in [this] rectangle.
 */
operator fun Rect.contains(entity: PlacedEntity<*>): Boolean {
    val (bl, tr) = entity.corners
    return bl in this && tr in this
}

val Iterable<PlacedEntity<*>>.coordinates: Set<Coordinate>
    get() = flatMapTo(mutableSetOf()) { it.coordinates }

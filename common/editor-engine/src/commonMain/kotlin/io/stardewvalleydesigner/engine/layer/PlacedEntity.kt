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
 * Placed rectangular object.
 */
data class PlacedEntity<out EType : EntityType>(
    val entity: Entity<EType>,
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


    // `Any` overrides

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PlacedEntity<*>) return false

        // `rectObject` and `place` are swapped for efficiency

        if (place != other.place) return false
        if (entity != other.entity) return false

        return true
    }

    override fun hashCode(): Int {
        var result = entity.hashCode()
        result = 31 * result + place.hashCode()
        return result
    }
}


// Static factories

/**
 * Static factory function for [PlacedEntity] creation.
 */
fun Entity<*>.placeIt(there: Coordinate): PlacedEntity<*> =
    PlacedEntity(entity = this, place = there)


// Operators


val PlacedEntity<*>.corners: CanonicalCorners
    get() = CanonicalCorners(
        bottomLeft = place,
        topRight = xy(
            x = place.x + entity.size.w - 1,
            y = place.y + entity.size.h - 1,
        )
    )

val PlacedEntity<*>.coordinateRanges: Pair<IntRange, IntRange>
    get() {
        val (bl, tr) = corners
        return bl.x..tr.x to bl.y..tr.y
    }

operator fun PlacedEntity<*>.contains(c: Coordinate): Boolean {
    val (xs, ys) = coordinateRanges
    return c.x in xs && c.y in ys
}

operator fun PlacedEntity<*>.contains(other: PlacedEntity<*>): Boolean {
    val (bl, tr) = other.corners
    return bl in this && tr in this
}

// TODO : FIX BUGS
/**
 * Returns `true` if [this] overlaps with the [other] placed rectangular object.
 */
infix fun PlacedEntity<*>.overlapsWith(other: PlacedEntity<*>): Boolean {
    val (bl, tr) = other.corners
    return bl in this || tr in this
}

/**
 * Returns `true` if [obj] is contained in [this] rectangle.
 */
operator fun Rect.contains(obj: PlacedEntity<*>): Boolean {
    val (bl, tr) = obj.corners
    return bl in this && tr in this
}

/**
 * All coordinates of this list of objects.
 */
val Iterable<PlacedEntity<*>>.coordinates: Set<Coordinate>
    get() = flatMapTo(mutableSetOf()) { it.coordinates }


// Private utils

// Cartesian product

private operator fun <A, B> Iterable<A>.times(other: Iterable<B>): List<Pair<A, B>> =
    flatMap { a -> other.map { b -> a to b } }

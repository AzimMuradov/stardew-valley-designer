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

package me.azimmuradov.svc.engine.rectmap

import me.azimmuradov.svc.engine.contains
import me.azimmuradov.svc.engine.geometry.*
import me.azimmuradov.svc.engine.overlapsWith


/**
 * Placed rectangular object.
 */
data class PlacedRectObject<out RO : RectObject>(
    val rectObject: RO,
    val place: Coordinate,
) {

    val coordinates: List<Coordinate> by lazy(toPlacedRect()::coordinates)

    operator fun component3(): List<Coordinate> = coordinates


    // `Any` overrides

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PlacedRectObject<*>

        // `rectObject` and `place` are swapped for efficiency

        if (place != other.place) return false
        if (rectObject != other.rectObject) return false

        return true
    }

    override fun hashCode(): Int {
        var result = rectObject.hashCode()
        result = 31 * result + place.hashCode()
        return result
    }
}


/**
 * Static factory function for [PlacedRectObject] creation.
 */
fun <RO : RectObject> RO.placeIt(there: Coordinate): PlacedRectObject<RO> =
    PlacedRectObject(rectObject = this, place = there)

/**
 * Returns `true` if [this] overlaps with the [other] placed rectangular object.
 */
infix fun PlacedRectObject<*>.overlapsWith(other: PlacedRectObject<*>): Boolean =
    this.coordinates overlapsWith other.coordinates

/**
 * Returns `true` if [obj] is contained in [this] rectangle.
 */
operator fun Rect.contains(obj: PlacedRectObject<*>): Boolean {
    val (rectObject, place) = obj

    val (x, y) = place
    val (w, h) = rectObject.size

    val (minCorner, maxCorner) = place to xy(x = x + w - 1, y = y + h - 1)

    return minCorner in this && maxCorner in this
}

/**
 * All coordinates of this list of objects.
 */
val Iterable<PlacedRectObject<*>>.coordinates: Set<Coordinate>
    get() = flatMapTo(mutableSetOf()) { it.coordinates }


// `PlacedRect` interop

fun PlacedRectObject<*>.toPlacedRect(): PlacedRect = PlacedRect(place, rect = rectObject.size)
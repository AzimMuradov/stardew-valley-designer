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

package io.stardewvalleydesigner.engine.geometry.shapes

import io.stardewvalleydesigner.engine.geometry.*


object PlacedRectStrategy : PlacedShapeStrategy {

    override fun coordinates(corners: CanonicalCorners): Set<Coordinate> {
        val (bl, tr) = corners

        val xs = bl.x..tr.x
        val ys = bl.y..tr.y

        return (xs * ys).mapTo(
            destination = mutableSetOf(),
            transform = Pair<Int, Int>::toCoordinate
        )
    }
}


// Private utils

// Cartesian product

private operator fun <A, B> Iterable<A>.times(other: Iterable<B>): List<Pair<A, B>> =
    flatMap { a -> other.map { b -> a to b } }

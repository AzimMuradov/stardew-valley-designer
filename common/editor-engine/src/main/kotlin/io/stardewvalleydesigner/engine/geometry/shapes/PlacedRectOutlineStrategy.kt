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


data object PlacedRectOutlineStrategy : PlacedShapeStrategy {

    override fun coordinates(a: Coordinate, b: Coordinate): Set<Coordinate> {
        val (bl, tr) = CanonicalCorners.fromTwoCoordinates(a, b)

        val xs = bl.x..tr.x
        val ys = bl.y..tr.y

        return buildSet {
            addAll(xs.map { xy(it, bl.y) })
            addAll(xs.map { xy(it, tr.y) })
            addAll(ys.map { xy(bl.x, it) })
            addAll(ys.map { xy(tr.x, it) })
        }
    }
}

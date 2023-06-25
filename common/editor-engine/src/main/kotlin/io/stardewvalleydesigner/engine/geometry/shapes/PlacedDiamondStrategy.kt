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


object PlacedDiamondStrategy : PlacedShapeStrategy {

    override fun coordinates(corners: CanonicalCorners): Set<Coordinate> {
        val (bl, tr) = corners

        return buildSet {
            val leftBorder = buildList {
                addAll(Bresenham.line(xy(bl.x, (bl.y + tr.y) / 2), xy((bl.x + tr.x) / 2, bl.y)))
                addAll(Bresenham.line(xy(bl.x, (bl.y + tr.y + 1) / 2), xy((bl.x + tr.x) / 2, tr.y)))
            }
            val rightBorder = buildList {
                addAll(Bresenham.line(xy(tr.x, (bl.y + tr.y) / 2), xy((bl.x + tr.x + 1) / 2, bl.y)))
                addAll(Bresenham.line(xy(tr.x, (bl.y + tr.y + 1) / 2), xy((bl.x + tr.x + 1) / 2, tr.y)))
            }

            addAll(leftBorder)
            addAll(rightBorder)

            val leftBorderByY = leftBorder.associate { it.y to it.x }
            val rightBorderByY = rightBorder.associate { it.y to it.x }

            for ((y, leftX) in leftBorderByY) {
                addAll((leftX..rightBorderByY[y]!!).map { x -> xy(x, y) })
            }
        }
    }
}

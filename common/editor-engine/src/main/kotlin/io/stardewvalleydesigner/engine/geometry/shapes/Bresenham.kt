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

import io.stardewvalleydesigner.engine.geometry.Coordinate
import io.stardewvalleydesigner.engine.geometry.xy
import kotlin.math.abs


internal object Bresenham {

    fun line(a: Coordinate, b: Coordinate): Set<Coordinate> {
        val (xA, yA) = a
        val (xB, yB) = b

        val dx = abs(xB - xA)
        val dy = abs(yB - yA)
        val sx = if (xA < xB) 1 else -1
        val sy = if (yA < yB) 1 else -1
        var err = dx - dy
        var currentX = xA
        var currentY = yA

        return buildSet {
            this += a
            while (!(currentX == xB && currentY == yB)) {
                val err2 = 2 * err
                if (err2 > -dy) {
                    err -= dy
                    currentX += sx
                }
                if (err2 < dx) {
                    err += dx
                    currentY += sy
                }
                this += xy(currentX, currentY)
            }
        }
    }
}

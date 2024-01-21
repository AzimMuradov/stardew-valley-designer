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

package io.stardewvalleydesigner.engine.geometry.shapes

import io.stardewvalleydesigner.engine.geometry.Coordinate
import io.stardewvalleydesigner.engine.geometry.xy
import kotlin.math.abs


// Port of C code by Alois Zingl
// See http://members.chello.at/~easyfilter/bresenham.html

internal object BresenhamAlgorithms {

    fun line(a: Coordinate, b: Coordinate): Set<Coordinate> {
        val (x0, y0) = a
        val (x1, y1) = b

        val dx = abs(x1 - x0)
        val sx = if (x0 < x1) 1 else -1
        val dy = -abs(y1 - y0)
        val sy = if (y0 < y1) 1 else -1

        return buildSet {
            add(a)

            var (x, y) = a
            var err = dx + dy /* error value e_xy */

            while (!(x == x1 && y == y1)) {
                val err2 = 2 * err
                if (err2 >= dy) { /* e_xy + e_x > 0 */
                    err += dy
                    x += sx
                }
                if (err2 <= dx) { /* e_xy + e_y < 0 */
                    err += dx
                    y += sy
                }

                add(xy(x, y))
            }
        }
    }

    fun ellipse(a: Coordinate, b: Coordinate): Set<Coordinate> {
        val set = mutableSetOf<Coordinate>()

        var (x0, y0) = a
        var (x1, y1) = b

        var rA = abs(x1 - x0)
        val rB = abs(y1 - y0)
        var rB1 = rB and 1 /* values of diameter */

        var dx = (4 * (1 - rA) * rB * rB).toLong()
        var dy = (4 * (rB1 + 1) * rA * rA).toLong() /* error increment */
        var err = dx + dy + rB1 * rA * rA

        if (x0 > x1) { /* if called with swapped points */
            x0 = x1
            x1 += rA
        }
        if (y0 > y1) y0 = y1 /* .. exchange them */
        y0 += (rB + 1) / 2
        y1 = y0 - rB1 /* starting pixel */
        rA *= 8 * rA
        rB1 = 8 * rB * rB

        do {
            set += xy(x1, y0) /*   I. Quadrant */
            set += xy(x0, y0) /*  II. Quadrant */
            set += xy(x0, y1) /* III. Quadrant */
            set += xy(x1, y1) /*  IV. Quadrant */

            val err2: Long = 2 * err
            if (err2 <= dy) { /* y step */
                y0++
                y1--
                dy += rA.toLong()
                err += dy
            }
            if (err2 >= dx || 2 * err > dy) { /* x step */
                x0++
                x1--
                dx += rB1.toLong()
                err += dx
            }
        } while (x0 <= x1)

        while (y0 - y1 < rB) { /* too early stop of flat ellipses a=1 */
            set += xy(x0 - 1, y0) /* -> finish tip of ellipse */
            set += xy(x1 + 1, y0++)
            set += xy(x0 - 1, y1)
            set += xy(x1 + 1, y1--)
        }

        return set
    }
}

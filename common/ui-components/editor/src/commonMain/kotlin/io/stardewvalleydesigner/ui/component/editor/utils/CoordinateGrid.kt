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

package io.stardewvalleydesigner.ui.component.editor.utils

import androidx.compose.ui.geometry.Offset
import io.stardewvalleydesigner.engine.geometry.Coordinate
import kotlin.jvm.JvmInline


@JvmInline
value class CoordinateGrid(private val cellSide: Float) {

    operator fun get(x: Int, y: Int): Offset = Offset(
        x = x * cellSide,
        y = y * cellSide
    )

    operator fun get(coordinate: Coordinate): Offset = Offset(
        x = coordinate.x * cellSide,
        y = coordinate.y * cellSide
    )


    fun getX(x: Int): Float = x * cellSide

    fun getY(y: Int): Float = y * cellSide
}

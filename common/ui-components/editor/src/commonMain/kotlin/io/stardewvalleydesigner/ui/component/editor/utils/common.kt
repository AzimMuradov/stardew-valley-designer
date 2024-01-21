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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import io.stardewvalleydesigner.engine.geometry.*
import kotlin.math.roundToInt


internal val UNDEFINED: Coordinate = xy(Int.MAX_VALUE, Int.MAX_VALUE)


internal val Size.ratio: Float get() = width / height

internal val IntSize.ratio: Float get() = width.toFloat() / height.toFloat()


internal fun Coordinate.toIntOffset(): IntOffset = IntOffset(x, y)

internal fun IntOffset.toCoordinate(): Coordinate = xy(x, y)

internal fun Rect.toIntSize(): IntSize = IntSize(w, h)

internal fun Size.toIntSize(): IntSize = IntSize(width.roundToInt(), height.roundToInt())

internal fun Offset.toIntOffset(): IntOffset = IntOffset(x.roundToInt(), y.roundToInt())

internal fun IntSize.toRect(): Rect = rectOf(width, height)


internal operator fun Int.times(size: IntOffset): IntOffset = IntOffset(x = this * size.x, y = this * size.y)

internal fun IntSize(topLeft: IntOffset, bottomRight: IntOffset): IntSize = IntSize(
    width = bottomRight.x - topLeft.x,
    height = bottomRight.y - topLeft.y,
)

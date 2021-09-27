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

package me.azimmuradov.svc.utils

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import me.azimmuradov.svc.engine.rectmap.*


val Size.ratio: Float get() = width / height

val IntSize.ratio: Float get() = width.toFloat() / height.toFloat()


fun Coordinate.toIntOffset(): IntOffset = IntOffset(x, y)

fun IntOffset.toCoordinate(): Coordinate = xy(x, y)


fun Rect.toIntSize(): IntSize = IntSize(w, h)

fun IntSize.toRect(): Rect = rectOf(width, height)
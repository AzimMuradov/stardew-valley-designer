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

package me.azimmuradov.svc.utils

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultBlendMode
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import me.azimmuradov.svc.components.screens.cartographer.res.Sprite
import kotlin.math.roundToInt


fun DrawScope.drawSprite(
    sprite: Sprite,
    offset: IntOffset = IntOffset.Zero,
    layoutSize: Size,
    alpha: Float = 1.0f,
) {
    val (spriteW, spriteH) = sprite.size
    val (layoutW, layoutH) = layoutSize

    val spriteRatio = sprite.size.ratio
    val layoutRatio = layoutSize.ratio

    val dstSize =
        if (spriteRatio > layoutRatio) {
            IntSize(width = layoutW.roundToInt(), height = (spriteH * layoutW / spriteW).roundToInt())
        } else {
            IntSize(width = (spriteW * layoutH / spriteH).roundToInt(), height = layoutH.roundToInt())
        }

    val dstOffset = offset + IntOffset(
        x = ((layoutW - dstSize.width) / 2).roundToInt(),
        y = ((layoutH - dstSize.height) / 2).roundToInt(),
    )

    drawImage(
        image = sprite.image,
        srcOffset = sprite.offset,
        srcSize = sprite.size,
        dstOffset = dstOffset,
        dstSize = dstSize,
        alpha = alpha,
        style = Fill,
        colorFilter = null,
        blendMode = DefaultBlendMode,
        filterQuality = if (spriteW * 1.5 < dstSize.width) FilterQuality.None else FilterQuality.Medium,
    )
}

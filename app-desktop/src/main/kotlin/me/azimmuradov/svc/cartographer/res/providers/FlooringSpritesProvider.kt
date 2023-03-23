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

package me.azimmuradov.svc.cartographer.res.providers

import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import me.azimmuradov.svc.cartographer.res.*
import me.azimmuradov.svc.engine.layout.LayoutType


object FlooringSpritesProvider {

    private val flooringObjectSpriteSize: IntSize = IntSize(width = 32, height = 32)

    internal fun flooring(index: Int): Sprite {
        val (i, j) = (index % 8) to (index / 8)
        val (w, h) = flooringObjectSpriteSize

        return Sprite(
            image = ImageProvider.imageOf(ImageFile.WallsAndFloors),
            offset = IntOffset(x = i * w, y = 336 + j * h),
            size = flooringObjectSpriteSize,
        )
    }


    private val sprites: MutableMap<LayoutType, Sprite> = mutableMapOf()
}

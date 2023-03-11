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


object WallpaperSpritesProvider {

    private val wallpaperObjectSpriteSize: IntSize = IntSize(width = 16, height = 48)

    internal fun wallpaper(index: Int): Sprite {
        val (i, j) = (index % 16) to (index / 16)
        val (w, h) = wallpaperObjectSpriteSize

        return Sprite(
            image = ImageProvider.imageOf(ImageFile.WallsAndFloors),
            offset = IntOffset(x = i * w, y = j * h),
            size = wallpaperObjectSpriteSize,
        )
    }


    private val sprites: MutableMap<LayoutType, Sprite> = mutableMapOf()
}

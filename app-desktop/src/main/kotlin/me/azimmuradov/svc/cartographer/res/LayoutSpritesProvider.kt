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

package me.azimmuradov.svc.cartographer.res

import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import me.azimmuradov.svc.engine.layout.LayoutType


object LayoutSpritesProvider {

    fun layoutSpriteBy(type: LayoutType): Sprite = sprites[type] ?: when (type) {
        LayoutType.Shed -> TODO()
        LayoutType.BigShed -> {
            val img = useResource("layouts/big-shed-borders-light.png", ::loadImageBitmap)
            Sprite(
                image = img,
                offset = IntOffset.Zero,
                size = IntSize(img.width, img.height),
            )
        }
    }.also { sprites[type] = it }


    private val sprites: MutableMap<LayoutType, Sprite> = mutableMapOf()
}

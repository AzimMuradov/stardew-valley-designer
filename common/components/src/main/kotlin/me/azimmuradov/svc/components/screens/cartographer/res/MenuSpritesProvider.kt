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

package me.azimmuradov.svc.components.screens.cartographer.res

import me.azimmuradov.svc.cartographer.toolkit.ToolType


object MenuSpritesProvider {

    fun toolSpriteBy(type: ToolType): Sprite = sprites[type] ?: when (type) {
        ToolType.Hand -> common(100)
        ToolType.Pen -> common(101)
        ToolType.Eraser -> common(102)
        ToolType.EyeDropper -> common(103)
        ToolType.RectSelect -> common(104)
        ToolType.EllipseSelect -> common(105)
    }.also { sprites[type] = it }


    private val sprites: MutableMap<ToolType, Sprite> = mutableMapOf()
}
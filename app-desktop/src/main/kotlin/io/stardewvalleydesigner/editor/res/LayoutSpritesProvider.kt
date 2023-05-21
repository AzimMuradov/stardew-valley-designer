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

package io.stardewvalleydesigner.editor.res

import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import io.stardewvalleydesigner.engine.layout.LayoutType


object LayoutSpritesProvider {

    fun layoutSpriteBy(type: LayoutType): LayoutSprites = sprites[type] ?: when (type) {
        LayoutType.Shed -> LayoutSprites(
            fgImage = useResource("layouts/shed-fg-light.png", ::loadImageBitmap),
            bgImage = useResource("layouts/shed-bg-light.png", ::loadImageBitmap),
        )

        LayoutType.BigShed -> LayoutSprites(
            fgImage = useResource("layouts/big-shed-fg-light.png", ::loadImageBitmap),
            bgImage = useResource("layouts/big-shed-bg-light.png", ::loadImageBitmap),
        )

        LayoutType.StandardFarm -> LayoutSprites(
            fgImage = useResource("layouts/standard-farm-fg-spring.png", ::loadImageBitmap),
            bgImage = useResource("layouts/standard-farm-bg-spring.png", ::loadImageBitmap),
        )
    }.also { sprites[type] = it }


    private val sprites: MutableMap<LayoutType, LayoutSprites> = mutableMapOf()
}

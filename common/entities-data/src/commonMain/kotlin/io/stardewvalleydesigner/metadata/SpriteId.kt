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

package io.stardewvalleydesigner.metadata

import io.stardewvalleydesigner.engine.entity.Color
import io.stardewvalleydesigner.metadata.SpritePage.Companion.UNIT


sealed interface SpriteId {

    data class RegularSprite(
        val page: SpritePage,
        val size: SpriteSize,
        val offset: SpriteOffset,
    ) : SpriteId

    data class ChestSprite(
        val tint: Color,
        val offset: SpriteOffset,
        val coverOffset: SpriteOffset,
    ) : SpriteId {

        val page = SpritePage.Craftables
        val size = SpriteSize(w = 1, h = 2) * UNIT
    }
}

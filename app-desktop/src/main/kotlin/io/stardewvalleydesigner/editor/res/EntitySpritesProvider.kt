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

import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import io.stardewvalleydesigner.engine.entity.Colors
import io.stardewvalleydesigner.engine.entity.Entity
import io.stardewvalleydesigner.metadata.*
import io.stardewvalleydesigner.utils.toComposeColor


object EntitySpritesProvider {

    fun spriteBy(entity: Entity<*>): Sprite =
        EntityDataProvider.entityToMetadata.getValue(entity).let { (id, offset, size) ->
            when (val flavor = id.flavor) {
                null -> spriteImage(id, offset, size)

                is Colors.ChestColors -> {
                    when (val color = flavor.value) {
                        null -> spriteImage(id, offset, size)

                        else -> Sprite.TintedImage(
                            image = ImageProvider.imageOf(id.page),
                            size = size.let { (w, h) -> IntSize(w, h) },
                            tint = color.toComposeColor(),
                            offset = offset.let { (x, y) -> IntOffset(x, y) },
                            coverOffset = offset.let { (x, y) -> IntOffset(x, y + 32) }
                        )
                    }
                }

                // is Colors.FishPondColors -> TODO

                // is Colors.FlowerColors -> TODO

                // is FarmBuildingColors -> TODO

                // is Rotations.Rotations2 -> TODO

                // is Rotations.Rotations4 -> TODO

                else -> spriteImage(id, offset, size)
            }
        }

    private fun spriteImage(id: EntityId, offset: EntityOffset, size: EntitySize) = Sprite.Image(
        image = ImageProvider.imageOf(id.page),
        offset = offset.let { (x, y) -> IntOffset(x, y) },
        size = size.let { (w, h) -> IntSize(w, h) },
    )
}

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
import io.stardewvalleydesigner.engine.entity.Entity
import io.stardewvalleydesigner.metadata.EntityDataProvider


object EntitySpritesProvider {

    fun spriteBy(entity: Entity<*>): Sprite =
        EntityDataProvider.entityToMetadata.getValue(entity).let { (id, offset, size) ->
            Sprite(
                image = ImageProvider.imageOf(id.page),
                offset = offset.let { (x, y) -> IntOffset(x, y) },
                size = size.let { (w, h) -> IntSize(w, h) },
            )
        }
}
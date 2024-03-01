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

package io.stardewvalleydesigner.metadata.internal

import io.stardewvalleydesigner.engine.entity.Entity
import io.stardewvalleydesigner.engine.entity.Rotations
import io.stardewvalleydesigner.metadata.*
import io.stardewvalleydesigner.metadata.SpritePage.CommonObjects
import io.stardewvalleydesigner.metadata.SpritePage.Companion.UNIT
import io.stardewvalleydesigner.metadata.SpritePage.Furniture


internal fun common(
    entity: Entity<*>,
    id: Int,
) = QualifiedEntityData(
    qualifiedEntity = QualifiedEntity(entity),
    entityId = EntityId(EntityPage.CommonObjects, id),
    spriteId = SpriteId.RegularSprite(
        page = CommonObjects,
        offset = SpriteOffset(
            x = id % (CommonObjects.width / UNIT),
            y = id / (CommonObjects.width / UNIT),
        ) * UNIT,
        size = SpriteSize(w = 1, h = 1) * UNIT,
    )
)

internal fun furniture(
    entity: Entity<*>,
    id: Int,
    x: Int, y: Int,
    w: Int, h: Int,
    r: Rotations,
) = QualifiedEntityData(
    qualifiedEntity = QualifiedEntity(entity),
    entityId = EntityId(
        page = EntityPage.Furniture,
        localId = id,
        flavor = r,
    ),
    spriteId = SpriteId.RegularSprite(
        page = Furniture,
        offset = SpriteOffset(x, y) * UNIT,
        size = SpriteSize(w, h) * UNIT,
    ),
)

internal fun furniture(
    entity: Entity<*>,
    x: Int, y: Int,
    w: Int, h: Int,
) = QualifiedEntityData(
    qualifiedEntity = QualifiedEntity(entity),
    entityId = EntityId(
        page = EntityPage.Furniture,
        localId = (Furniture.width / UNIT) * y + x,
    ),
    spriteId = SpriteId.RegularSprite(
        page = Furniture,
        offset = SpriteOffset(x, y) * UNIT,
        size = SpriteSize(w, h) * UNIT,
    ),
)

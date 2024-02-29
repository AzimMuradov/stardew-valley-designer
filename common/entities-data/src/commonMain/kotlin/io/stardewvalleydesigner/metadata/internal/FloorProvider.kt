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
import io.stardewvalleydesigner.engine.entity.Floor
import io.stardewvalleydesigner.engine.entity.Floor.*
import io.stardewvalleydesigner.metadata.*
import io.stardewvalleydesigner.metadata.FloorVariant.*
import io.stardewvalleydesigner.metadata.SpritePage.Companion.UNIT


internal fun floor(qe: QualifiedEntity<Floor>): QualifiedEntityData {
    val entity = qe.entity

    fun flooring(whichFloor: Int) = flooring(
        entity = entity,
        qualifier = qe.qualifier as SpriteQualifier.FloorQualifier,
        whichFloor = whichFloor
    )

    return when (entity) {
        // Floors

        WoodFloor -> flooring(0)
        RusticPlankFloor -> flooring(11)
        StrawFloor -> flooring(4)
        WeatheredFloor -> flooring(2)
        CrystalFloor -> flooring(3)
        StoneFloor -> flooring(1)
        StoneWalkwayFloor -> flooring(12)
        BrickFloor -> flooring(10)


        // Paths

        WoodPath -> flooring(6)
        GravelPath -> flooring(5)
        CobblestonePath -> flooring(8)
        SteppingStonePath -> steppingStonePath(qe.qualifier as SpriteQualifier.SteppingStonePathQualifier)
        CrystalPath -> flooring(7)


        // Grass

        Grass -> common(Grass, 297)
    }
}


private fun flooring(
    entity: Floor,
    qualifier: SpriteQualifier.FloorQualifier,
    whichFloor: Int,
): QualifiedEntityData {
    val x = when (qualifier.variant) {
        Single, ColTop, ColCenter, ColBottom -> 0
        TopLeft, CenterLeft, BottomLeft, RowLeft -> 1
        TopCenter, CenterCenter, BottomCenter, RowCenter -> 2
        TopRight, CenterRight, BottomRight, RowRight -> 3
    }
    val y = when (qualifier.variant) {
        Single, TopLeft, TopCenter, TopRight -> 0
        ColTop, CenterLeft, CenterCenter, CenterRight -> 1
        ColCenter, BottomLeft, BottomCenter, BottomRight -> 2
        ColBottom, RowLeft, RowCenter, RowRight -> 3
    }
    val spritePage = if (qualifier.isNotWinter) {
        SpritePage.Flooring
    } else {
        SpritePage.FlooringWinter
    }

    return QualifiedEntityData(
        qualifiedEntity = QualifiedEntity(entity, qualifier),
        entityId = EntityId(EntityPage.Flooring, localId = whichFloor),
        spriteId = SpriteId.RegularSprite(
            page = spritePage,
            offset = SpriteOffset(
                x = (whichFloor % 4) * 4 + x,
                y = (whichFloor / 4) * 4 + y,
            ) * UNIT,
            size = SpriteSize(w = 1, h = 1) * UNIT,
        )
    )
}

private fun steppingStonePath(
    qualifier: SpriteQualifier.SteppingStonePathQualifier,
): QualifiedEntityData {
    val spritePage = if (qualifier.isNotWinter) {
        SpritePage.Flooring
    } else {
        SpritePage.FlooringWinter
    }

    return QualifiedEntityData(
        qualifiedEntity = QualifiedEntity(SteppingStonePath, qualifier),
        entityId = EntityId(EntityPage.Flooring, localId = 9),
        spriteId = SpriteId.RegularSprite(
            page = spritePage,
            offset = SpriteOffset(
                x = (9 % 4) * 4,
                y = (9 / 4) * 4,
            ) * UNIT,
            size = SpriteSize(w = 1, h = 1) * UNIT,
        )
    )
}

private fun common(
    entity: Entity<*>,
    index: Int,
) = QualifiedEntityData(
    qualifiedEntity = QualifiedEntity(entity),
    entityId = EntityId(
        page = EntityPage.CommonObjects,
        localId = index,
        flavor = null,
    ),
    spriteId = SpriteId.RegularSprite(
        page = SpritePage.CommonObjects,
        offset = SpriteOffset(
            x = index % (SpritePage.CommonObjects.width / UNIT),
            y = index / (SpritePage.CommonObjects.width / UNIT),
        ) * UNIT,
        size = SpriteSize(w = 1, h = 1) * UNIT,
    )
)

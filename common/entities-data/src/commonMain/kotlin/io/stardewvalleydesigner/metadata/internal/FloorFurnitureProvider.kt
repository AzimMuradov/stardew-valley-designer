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

import io.stardewvalleydesigner.engine.entity.FloorFurniture
import io.stardewvalleydesigner.engine.entity.FloorFurniture.*
import io.stardewvalleydesigner.engine.entity.Rotations
import io.stardewvalleydesigner.engine.entity.Rotations.Rotations2.R1
import io.stardewvalleydesigner.engine.entity.Rotations.Rotations2.R2
import io.stardewvalleydesigner.metadata.QualifiedEntity
import io.stardewvalleydesigner.metadata.QualifiedEntityData


internal fun floorFurniture(qe: QualifiedEntity<FloorFurniture>): QualifiedEntityData {
    val entity = qe.entity

    fun furniture(x: Int, y: Int, w: Int, h: Int) =
        furniture(entity, x, y, w, h)

    fun furniture(id: Int, x: Int, y: Int, w: Int, h: Int, r: Rotations) =
        furniture(entity, id, x, y, w, h, r)

    return when (entity) {
        SimpleRug.BurlapRug -> furniture(x = 14, y = 54, w = 2, h = 2)
        SimpleRug.WoodcutRug -> furniture(x = 17, y = 55, w = 2, h = 2)
        SimpleRug.LargeRedRug -> furniture(x = 14, y = 87, w = 4, h = 3)
        SimpleRug.MonsterRug -> furniture(x = 28, y = 50, w = 2, h = 2)
        SimpleRug.BlossomRug -> furniture(x = 22, y = 85, w = 6, h = 4)
        SimpleRug.LargeGreenRug -> furniture(x = 0, y = 87, w = 4, h = 3)
        SimpleRug.OldWorldRug -> furniture(x = 10, y = 87, w = 4, h = 3)
        SimpleRug.LargeCottageRug -> furniture(x = 18, y = 87, w = 4, h = 3)
        SimpleRug.OceanicRug -> furniture(x = 12, y = 38, w = 3, h = 2)
        SimpleRug.IcyRug -> furniture(x = 6, y = 87, w = 4, h = 3)
        SimpleRug.FunkyRug -> furniture(x = 22, y = 89, w = 5, h = 4)
        SimpleRug.ModernRug -> furniture(x = 27, y = 89, w = 5, h = 4)

        is RotatableRug.BambooMat -> when (entity.rotation) {
            R1 -> furniture(id = 1755, x = 27, y = 54, w = 2, h = 1, r = entity.rotation)
            R2 -> furniture(id = 1755, x = 29, y = 54, w = 1, h = 2, r = entity.rotation)
        }

        is RotatableRug.NauticalRug -> when (entity.rotation) {
            R1 -> furniture(id = 1737, x = 9, y = 54, w = 3, h = 2, r = entity.rotation)
            R2 -> furniture(id = 1737, x = 12, y = 54, w = 2, h = 3, r = entity.rotation)
        }

        is RotatableRug.DarkRug -> when (entity.rotation) {
            R1 -> furniture(id = 1461, x = 21, y = 45, w = 3, h = 2, r = entity.rotation)
            R2 -> furniture(id = 1461, x = 24, y = 45, w = 2, h = 3, r = entity.rotation)
        }

        is RotatableRug.RedRug -> when (entity.rotation) {
            R1 -> furniture(id = 1451, x = 11, y = 45, w = 3, h = 2, r = entity.rotation)
            R2 -> furniture(id = 1451, x = 14, y = 45, w = 2, h = 3, r = entity.rotation)
        }

        is RotatableRug.LightGreenRug -> when (entity.rotation) {
            R1 -> furniture(id = 2488, x = 24, y = 77, w = 3, h = 2, r = entity.rotation)
            R2 -> furniture(id = 2488, x = 27, y = 77, w = 2, h = 3, r = entity.rotation)
        }

        is RotatableRug.GreenCottageRug -> when (entity.rotation) {
            R1 -> furniture(id = 1623, x = 23, y = 50, w = 3, h = 2, r = entity.rotation)
            R2 -> furniture(id = 1623, x = 26, y = 50, w = 2, h = 3, r = entity.rotation)
        }

        is RotatableRug.RedCottageRug -> when (entity.rotation) {
            R1 -> furniture(id = 1618, x = 18, y = 50, w = 3, h = 2, r = entity.rotation)
            R2 -> furniture(id = 1618, x = 21, y = 50, w = 2, h = 3, r = entity.rotation)
        }

        is RotatableRug.MysticRug -> when (entity.rotation) {
            R1 -> furniture(id = 1664, x = 0, y = 52, w = 3, h = 2, r = entity.rotation)
            R2 -> furniture(id = 1664, x = 3, y = 52, w = 2, h = 3, r = entity.rotation)
        }

        is RotatableRug.BoneRug -> when (entity.rotation) {
            R1 -> furniture(id = 1964, x = 12, y = 61, w = 3, h = 2, r = entity.rotation)
            R2 -> furniture(id = 1964, x = 15, y = 61, w = 2, h = 3, r = entity.rotation)
        }

        is RotatableRug.SnowyRug -> when (entity.rotation) {
            R1 -> furniture(id = 1978, x = 26, y = 61, w = 3, h = 2, r = entity.rotation)
            R2 -> furniture(id = 1978, x = 29, y = 61, w = 2, h = 3, r = entity.rotation)
        }

        is RotatableRug.PirateRug -> when (entity.rotation) {
            R1 -> furniture(id = 1902, x = 14, y = 59, w = 3, h = 2, r = entity.rotation)
            R2 -> furniture(id = 1902, x = 17, y = 59, w = 2, h = 3, r = entity.rotation)
        }

        is RotatableRug.PatchworkRug -> when (entity.rotation) {
            R1 -> furniture(id = 1456, x = 16, y = 45, w = 3, h = 2, r = entity.rotation)
            R2 -> furniture(id = 1456, x = 19, y = 45, w = 2, h = 3, r = entity.rotation)
        }

        is RotatableRug.FruitSaladRug -> when (entity.rotation) {
            R1 -> furniture(id = 1909, x = 21, y = 59, w = 3, h = 2, r = entity.rotation)
            R2 -> furniture(id = 1909, x = 24, y = 59, w = 2, h = 3, r = entity.rotation)
        }

        FloorDivider.FloorDivider1L -> furniture(x = 13, y = 82, w = 1, h = 3)
        FloorDivider.FloorDivider1R -> furniture(x = 14, y = 82, w = 1, h = 3)
        FloorDivider.FloorDivider2L -> furniture(x = 15, y = 82, w = 1, h = 3)
        FloorDivider.FloorDivider2R -> furniture(x = 16, y = 82, w = 1, h = 3)
        FloorDivider.FloorDivider3L -> furniture(x = 17, y = 82, w = 1, h = 3)
        FloorDivider.FloorDivider3R -> furniture(x = 18, y = 82, w = 1, h = 3)
        FloorDivider.FloorDivider4L -> furniture(x = 19, y = 82, w = 1, h = 3)
        FloorDivider.FloorDivider4R -> furniture(x = 20, y = 82, w = 1, h = 3)
        FloorDivider.FloorDivider5L -> furniture(x = 21, y = 82, w = 1, h = 3)
        FloorDivider.FloorDivider5R -> furniture(x = 22, y = 82, w = 1, h = 3)
        FloorDivider.FloorDivider6L -> furniture(x = 23, y = 82, w = 1, h = 3)
        FloorDivider.FloorDivider6R -> furniture(x = 24, y = 82, w = 1, h = 3)
        FloorDivider.FloorDivider7L -> furniture(x = 25, y = 82, w = 1, h = 3)
        FloorDivider.FloorDivider7R -> furniture(x = 26, y = 82, w = 1, h = 3)
        FloorDivider.FloorDivider8L -> furniture(x = 27, y = 82, w = 1, h = 3)
        FloorDivider.FloorDivider8R -> furniture(x = 28, y = 82, w = 1, h = 3)
    }
}

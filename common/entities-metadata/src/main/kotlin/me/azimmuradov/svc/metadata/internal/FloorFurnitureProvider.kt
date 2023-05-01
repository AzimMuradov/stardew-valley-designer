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

package me.azimmuradov.svc.metadata.internal

import me.azimmuradov.svc.engine.entity.FloorFurniture
import me.azimmuradov.svc.engine.entity.FloorFurniture.*
import me.azimmuradov.svc.engine.entity.Rotations.Rotations2.R0
import me.azimmuradov.svc.engine.entity.Rotations.Rotations2.R1
import me.azimmuradov.svc.metadata.EntityMetadata


internal fun floorFurniture(entity: FloorFurniture): EntityMetadata = when (entity) {

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
        R0 -> TODO()
        R1 -> furniture(id = 1755, x = 29, y = 54, w = 1, h = 2, r = entity.rotation)
    }

    is RotatableRug.NauticalRug -> when (entity.rotation) {
        R0 -> TODO()
        R1 -> furniture(id = 1740, x = 12, y = 54, w = 2, h = 3, r = entity.rotation)
    }

    is RotatableRug.DarkRug -> when (entity.rotation) {
        R0 -> TODO()
        R1 -> furniture(id = 1464, x = 24, y = 45, w = 2, h = 3, r = entity.rotation)
    }

    is RotatableRug.RedRug -> when (entity.rotation) {
        R0 -> TODO()
        R1 -> furniture(id = 1454, x = 14, y = 45, w = 2, h = 3, r = entity.rotation)
    }

    is RotatableRug.LightGreenRug -> when (entity.rotation) {
        R0 -> TODO()
        R1 -> furniture(id = 2488, x = 27, y = 77, w = 2, h = 3, r = entity.rotation)
    }

    is RotatableRug.GreenCottageRug -> when (entity.rotation) {
        R0 -> TODO()
        R1 -> furniture(id = 1626, x = 26, y = 50, w = 2, h = 3, r = entity.rotation)
    }

    is RotatableRug.RedCottageRug -> when (entity.rotation) {
        R0 -> TODO()
        R1 -> furniture(id = 1621, x = 21, y = 50, w = 2, h = 3, r = entity.rotation)
    }

    is RotatableRug.MysticRug -> when (entity.rotation) {
        R0 -> TODO()
        R1 -> furniture(id = 1667, x = 3, y = 52, w = 2, h = 3, r = entity.rotation)
    }

    is RotatableRug.BoneRug -> when (entity.rotation) {
        R0 -> TODO()
        R1 -> furniture(id = 1967, x = 15, y = 61, w = 2, h = 3, r = entity.rotation)
    }

    is RotatableRug.SnowyRug -> when (entity.rotation) {
        R0 -> TODO()
        R1 -> furniture(id = 1981, x = 29, y = 61, w = 2, h = 3, r = entity.rotation)
    }

    is RotatableRug.PirateRug -> when (entity.rotation) {
        R0 -> TODO()
        R1 -> furniture(id = 1905, x = 17, y = 59, w = 2, h = 3, r = entity.rotation)
    }

    is RotatableRug.PatchworkRug -> when (entity.rotation) {
        R0 -> TODO()
        R1 -> furniture(id = 1459, x = 19, y = 45, w = 2, h = 3, r = entity.rotation)
    }

    is RotatableRug.FruitSaladRug -> when (entity.rotation) {
        R0 -> TODO()
        R1 -> furniture(id = 1912, x = 24, y = 59, w = 2, h = 3, r = entity.rotation)
    }

    is FloorDivider -> TODO()
}

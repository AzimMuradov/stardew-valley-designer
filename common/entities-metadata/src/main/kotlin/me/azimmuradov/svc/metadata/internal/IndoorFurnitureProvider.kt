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

import me.azimmuradov.svc.engine.entity.IndoorFurniture
import me.azimmuradov.svc.engine.entity.IndoorFurniture.*
import me.azimmuradov.svc.engine.entity.Rotations.Rotations4.*
import me.azimmuradov.svc.metadata.EntityMetadata


internal fun indoorFurniture(entity: IndoorFurniture): EntityMetadata = when (entity) {

    SimpleIndoorFurniture.ArtistBookcase -> furniture(x = 3, y = 40, w = 2, h = 3)
    SimpleIndoorFurniture.ModernBookcase -> furniture(x = 5, y = 40, w = 2, h = 3)
    SimpleIndoorFurniture.LuxuryBookcase -> furniture(x = 7, y = 40, w = 2, h = 3)
    SimpleIndoorFurniture.DarkBookcase -> furniture(x = 9, y = 40, w = 2, h = 3)

    SimpleIndoorFurniture.BrickFireplace -> furniture(x = 0, y = 56, w = 2, h = 5)
    SimpleIndoorFurniture.StoneFireplace -> furniture(x = 2, y = 56, w = 2, h = 5)
    SimpleIndoorFurniture.IridiumFireplace -> furniture(x = 4, y = 56, w = 2, h = 5)
    SimpleIndoorFurniture.ElegantFireplace -> furniture(x = 10, y = 58, w = 2, h = 5)
    SimpleIndoorFurniture.MonsterFireplace -> furniture(x = 8, y = 56, w = 2, h = 5)
    SimpleIndoorFurniture.StoveFireplace -> furniture(x = 6, y = 56, w = 2, h = 5)

    SimpleIndoorFurniture.CountryLamp -> furniture(x = 3, y = 45, w = 1, h = 3)
    SimpleIndoorFurniture.ModernLamp -> furniture(x = 7, y = 45, w = 1, h = 3)
    SimpleIndoorFurniture.ClassicLamp -> furniture(x = 9, y = 45, w = 1, h = 3)
    SimpleIndoorFurniture.BoxLamp -> furniture(x = 5, y = 45, w = 1, h = 3)
    SimpleIndoorFurniture.CandleLamp -> furniture(x = 23, y = 54, w = 1, h = 3)
    SimpleIndoorFurniture.OrnateLamp -> furniture(x = 30, y = 54, w = 1, h = 3)

    SimpleIndoorFurniture.FloorTV -> furniture(x = 16, y = 52, w = 2, h = 2)
    SimpleIndoorFurniture.BudgetTV -> furniture(x = 26, y = 45, w = 2, h = 3)
    SimpleIndoorFurniture.PlasmaTV -> furniture(x = 28, y = 45, w = 3, h = 3)
    SimpleIndoorFurniture.TropicalTV -> furniture(x = 22, y = 72, w = 3, h = 3)

    SimpleIndoorFurniture.SmallFishTank -> furniture(x = 18, y = 72, w = 2, h = 3)
    SimpleIndoorFurniture.ModernFishTank -> furniture(x = 14, y = 75, w = 2, h = 3)
    SimpleIndoorFurniture.LargeFishTank -> furniture(x = 0, y = 72, w = 4, h = 3)
    SimpleIndoorFurniture.DeluxeFishTank -> furniture(x = 8, y = 72, w = 5, h = 3)
    SimpleIndoorFurniture.AquaticSanctuary -> furniture(x = 0, y = 75, w = 7, h = 3)

    SimpleIndoorFurniture.ChinaCabinet -> furniture(x = 0, y = 40, w = 3, h = 3)


    is Couch.BlueCouch -> when (entity.rotation) {
        R1 -> furniture(id = 416, x = 0, y = 13, w = 3, h = 2, r = entity.rotation)
        R2 -> TODO()
        R3 -> TODO()
        R4 -> TODO()
    }

    is Couch.BrownCouch -> when (entity.rotation) {
        R1 -> furniture(id = 512, x = 0, y = 16, w = 3, h = 2, r = entity.rotation)
        R2 -> TODO()
        R3 -> TODO()
        R4 -> TODO()
    }

    is Couch.GreenCouch -> when (entity.rotation) {
        R1 -> furniture(id = 432, x = 16, y = 13, w = 3, h = 2, r = entity.rotation)
        R2 -> TODO()
        R3 -> TODO()
        R4 -> TODO()
    }

    is Couch.RedCouch -> when (entity.rotation) {
        R1 -> furniture(id = 424, x = 8, y = 13, w = 3, h = 2, r = entity.rotation)
        R2 -> TODO()
        R3 -> TODO()
        R4 -> TODO()
    }

    is Couch.YellowCouch -> when (entity.rotation) {
        R1 -> furniture(id = 440, x = 24, y = 13, w = 3, h = 2, r = entity.rotation)
        R2 -> TODO()
        R3 -> TODO()
        R4 -> TODO()
    }

    is Couch.DarkCouch -> when (entity.rotation) {
        R1 -> furniture(id = 520, x = 8, y = 16, w = 3, h = 2, r = entity.rotation)
        R2 -> TODO()
        R3 -> TODO()
        R4 -> TODO()
    }

    is Couch.WoodsyCouch -> when (entity.rotation) {
        R1 -> furniture(id = 536, x = 24, y = 16, w = 3, h = 2, r = entity.rotation)
        R2 -> TODO()
        R3 -> TODO()
        R4 -> TODO()
    }

    is Couch.WizardCouch -> when (entity.rotation) {
        R1 -> furniture(id = 528, x = 16, y = 16, w = 3, h = 2, r = entity.rotation)
        R2 -> TODO()
        R3 -> TODO()
        R4 -> TODO()
    }

    is LargeBrownCouch -> when (entity.rotation) {
        R1 -> furniture(id = 2720, x = 0, y = 85, w = 4, h = 2, r = entity.rotation)
        R2 -> TODO()
        R3 -> TODO()
        R4 -> TODO()
    }


    is Armchair.BlueArmchair -> when (entity.rotation) {
        R1 -> furniture(id = 288, x = 0, y = 9, w = 2, h = 2, r = entity.rotation)
        R2 -> TODO()
        R3 -> TODO()
        R4 -> TODO()
    }

    is Armchair.BrownArmchair -> when (entity.rotation) {
        R1 -> furniture(id = 312, x = 24, y = 9, w = 2, h = 2, r = entity.rotation)
        R2 -> TODO()
        R3 -> TODO()
        R4 -> TODO()
    }

    is Armchair.GreenArmchair -> when (entity.rotation) {
        R1 -> furniture(id = 300, x = 12, y = 9, w = 2, h = 2, r = entity.rotation)
        R2 -> TODO()
        R3 -> TODO()
        R4 -> TODO()
    }

    is Armchair.RedArmchair -> when (entity.rotation) {
        R1 -> furniture(id = 294, x = 6, y = 9, w = 2, h = 2, r = entity.rotation)
        R2 -> TODO()
        R3 -> TODO()
        R4 -> TODO()
    }

    is Armchair.YellowArmchair -> when (entity.rotation) {
        R1 -> furniture(id = 294, x = 18, y = 9, w = 2, h = 2, r = entity.rotation)
        R2 -> TODO()
        R3 -> TODO()
        R4 -> TODO()
    }


    is Dresser.BirchDresser -> when (entity.rotation) {
        R1 -> furniture(id = 714, x = 10, y = 22, w = 2, h = 2, r = entity.rotation)
        R2 -> TODO()
        R3 -> TODO()
        R4 -> TODO()
    }

    is Dresser.OakDresser -> when (entity.rotation) {
        R1 -> furniture(id = 704, x = 0, y = 22, w = 2, h = 2, r = entity.rotation)
        R2 -> TODO()
        R3 -> TODO()
        R4 -> TODO()
    }

    is Dresser.WalnutDresser -> when (entity.rotation) {
        R1 -> furniture(id = 709, x = 5, y = 22, w = 2, h = 2, r = entity.rotation)
        R2 -> TODO()
        R3 -> TODO()
        R4 -> TODO()
    }

    is Dresser.MahoganyDresser -> when (entity.rotation) {
        R1 -> furniture(id = 719, x = 15, y = 22, w = 2, h = 2, r = entity.rotation)
        R2 -> TODO()
        R3 -> TODO()
        R4 -> TODO()
    }
}

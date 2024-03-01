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

package io.stardewvalleydesigner.data.internal

import io.stardewvalleydesigner.data.QualifiedEntity
import io.stardewvalleydesigner.data.QualifiedEntityData
import io.stardewvalleydesigner.engine.entity.IndoorFurniture
import io.stardewvalleydesigner.engine.entity.IndoorFurniture.Armchair.*
import io.stardewvalleydesigner.engine.entity.IndoorFurniture.Couch.*
import io.stardewvalleydesigner.engine.entity.IndoorFurniture.Dresser.*
import io.stardewvalleydesigner.engine.entity.IndoorFurniture.LargeBrownCouch
import io.stardewvalleydesigner.engine.entity.IndoorFurniture.SimpleIndoorFurniture.*
import io.stardewvalleydesigner.engine.entity.Rotations
import io.stardewvalleydesigner.engine.entity.Rotations.Rotations4.*


internal fun indoorFurniture(qe: QualifiedEntity<IndoorFurniture>): QualifiedEntityData {
    val entity = qe.entity

    fun furniture(x: Int, y: Int, w: Int, h: Int) =
        furniture(entity, x, y, w, h)

    fun furniture(id: Int, x: Int, y: Int, w: Int, h: Int, r: Rotations) =
        furniture(entity, id, x, y, w, h, r)

    return when (entity) {
        ArtistBookcase -> furniture(x = 3, y = 40, w = 2, h = 3)
        ModernBookcase -> furniture(x = 5, y = 40, w = 2, h = 3)
        LuxuryBookcase -> furniture(x = 7, y = 40, w = 2, h = 3)
        DarkBookcase -> furniture(x = 9, y = 40, w = 2, h = 3)

        BrickFireplace -> furniture(x = 0, y = 56, w = 2, h = 5)
        StoneFireplace -> furniture(x = 2, y = 56, w = 2, h = 5)
        IridiumFireplace -> furniture(x = 4, y = 56, w = 2, h = 5)
        ElegantFireplace -> furniture(x = 10, y = 58, w = 2, h = 5)
        MonsterFireplace -> furniture(x = 8, y = 56, w = 2, h = 5)
        StoveFireplace -> furniture(x = 6, y = 56, w = 2, h = 5)

        CountryLamp -> furniture(x = 3, y = 45, w = 1, h = 3)
        ModernLamp -> furniture(x = 7, y = 45, w = 1, h = 3)
        ClassicLamp -> furniture(x = 9, y = 45, w = 1, h = 3)
        BoxLamp -> furniture(x = 5, y = 45, w = 1, h = 3)
        CandleLamp -> furniture(x = 23, y = 54, w = 1, h = 3)
        OrnateLamp -> furniture(x = 30, y = 54, w = 1, h = 3)

        FloorTV -> furniture(x = 16, y = 52, w = 2, h = 2)
        BudgetTV -> furniture(x = 26, y = 45, w = 2, h = 3)
        PlasmaTV -> furniture(x = 28, y = 45, w = 3, h = 3)
        TropicalTV -> furniture(x = 22, y = 72, w = 3, h = 3)

        SmallFishTank -> furniture(x = 18, y = 72, w = 2, h = 3)
        ModernFishTank -> furniture(x = 14, y = 75, w = 2, h = 3)
        LargeFishTank -> furniture(x = 0, y = 72, w = 4, h = 3)
        DeluxeFishTank -> furniture(x = 8, y = 72, w = 5, h = 3)
        AquaticSanctuary -> furniture(x = 0, y = 75, w = 7, h = 3)

        ChinaCabinet -> furniture(x = 0, y = 40, w = 3, h = 3)


        is BlueCouch -> when (entity.rotation) {
            R1 -> furniture(id = 416, x = 0, y = 13, w = 3, h = 2, r = entity.rotation)
            R2 -> TODO()
            R3 -> TODO()
            R4 -> TODO()
        }

        is BrownCouch -> when (entity.rotation) {
            R1 -> furniture(id = 512, x = 0, y = 16, w = 3, h = 2, r = entity.rotation)
            R2 -> TODO()
            R3 -> TODO()
            R4 -> TODO()
        }

        is GreenCouch -> when (entity.rotation) {
            R1 -> furniture(id = 432, x = 16, y = 13, w = 3, h = 2, r = entity.rotation)
            R2 -> TODO()
            R3 -> TODO()
            R4 -> TODO()
        }

        is RedCouch -> when (entity.rotation) {
            R1 -> furniture(id = 424, x = 8, y = 13, w = 3, h = 2, r = entity.rotation)
            R2 -> TODO()
            R3 -> TODO()
            R4 -> TODO()
        }

        is YellowCouch -> when (entity.rotation) {
            R1 -> furniture(id = 440, x = 24, y = 13, w = 3, h = 2, r = entity.rotation)
            R2 -> TODO()
            R3 -> TODO()
            R4 -> TODO()
        }

        is DarkCouch -> when (entity.rotation) {
            R1 -> furniture(id = 520, x = 8, y = 16, w = 3, h = 2, r = entity.rotation)
            R2 -> TODO()
            R3 -> TODO()
            R4 -> TODO()
        }

        is WoodsyCouch -> when (entity.rotation) {
            R1 -> furniture(id = 536, x = 24, y = 16, w = 3, h = 2, r = entity.rotation)
            R2 -> TODO()
            R3 -> TODO()
            R4 -> TODO()
        }

        is WizardCouch -> when (entity.rotation) {
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


        is BlueArmchair -> when (entity.rotation) {
            R1 -> furniture(id = 288, x = 0, y = 9, w = 2, h = 2, r = entity.rotation)
            R2 -> TODO()
            R3 -> TODO()
            R4 -> TODO()
        }

        is BrownArmchair -> when (entity.rotation) {
            R1 -> furniture(id = 312, x = 24, y = 9, w = 2, h = 2, r = entity.rotation)
            R2 -> TODO()
            R3 -> TODO()
            R4 -> TODO()
        }

        is GreenArmchair -> when (entity.rotation) {
            R1 -> furniture(id = 300, x = 12, y = 9, w = 2, h = 2, r = entity.rotation)
            R2 -> TODO()
            R3 -> TODO()
            R4 -> TODO()
        }

        is RedArmchair -> when (entity.rotation) {
            R1 -> furniture(id = 294, x = 6, y = 9, w = 2, h = 2, r = entity.rotation)
            R2 -> TODO()
            R3 -> TODO()
            R4 -> TODO()
        }

        is YellowArmchair -> when (entity.rotation) {
            R1 -> furniture(id = 294, x = 18, y = 9, w = 2, h = 2, r = entity.rotation)
            R2 -> TODO()
            R3 -> TODO()
            R4 -> TODO()
        }


        is BirchDresser -> when (entity.rotation) {
            R1 -> furniture(id = 714, x = 10, y = 22, w = 2, h = 2, r = entity.rotation)
            R2 -> TODO()
            R3 -> TODO()
            R4 -> TODO()
        }

        is OakDresser -> when (entity.rotation) {
            R1 -> furniture(id = 704, x = 0, y = 22, w = 2, h = 2, r = entity.rotation)
            R2 -> TODO()
            R3 -> TODO()
            R4 -> TODO()
        }

        is WalnutDresser -> when (entity.rotation) {
            R1 -> furniture(id = 709, x = 5, y = 22, w = 2, h = 2, r = entity.rotation)
            R2 -> TODO()
            R3 -> TODO()
            R4 -> TODO()
        }

        is MahoganyDresser -> when (entity.rotation) {
            R1 -> furniture(id = 719, x = 15, y = 22, w = 2, h = 2, r = entity.rotation)
            R2 -> TODO()
            R3 -> TODO()
            R4 -> TODO()
        }
    }
}

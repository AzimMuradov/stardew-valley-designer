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

@file:Suppress("PackageDirectoryMismatch")

package me.azimmuradov.svc.engine.entity

import me.azimmuradov.svc.engine.entity.ObjectType.FurnitureType.IndoorFurnitureType
import me.azimmuradov.svc.engine.entity.RectsProvider.rectOf
import me.azimmuradov.svc.engine.entity.Rotatable.Rotatable4
import me.azimmuradov.svc.engine.entity.Rotations.Rotations4
import me.azimmuradov.svc.engine.entity.Rotations.Rotations4.R0
import me.azimmuradov.svc.engine.geometry.Rect


sealed interface IndoorFurniture : Entity<IndoorFurnitureType> {

    enum class SimpleIndoorFurniture(override val size: Rect) : IndoorFurniture {

        // Bookcases

        ArtistBookcase(bookcaseSize),
        ModernBookcase(bookcaseSize),
        LuxuryBookcase(bookcaseSize),
        DarkBookcase(bookcaseSize),


        // Fireplaces

        BrickFireplace(fireplaceSize),
        ElegantFireplace(fireplaceSize),
        IridiumFireplace(fireplaceSize),
        MonsterFireplace(fireplaceSize),
        StoneFireplace(fireplaceSize),
        StoveFireplace(fireplaceSize),


        // Floor Lamps

        CountryLamp(floorLampSize),
        ModernLamp(floorLampSize),
        ClassicLamp(floorLampSize),
        BoxLamp(floorLampSize),
        CandleLamp(floorLampSize),
        OrnateLamp(floorLampSize),


        // TVs

        FloorTV(rectOf(w = 2, h = 1)),
        BudgetTV(rectOf(w = 2, h = 2)),
        PlasmaTV(rectOf(w = 3, h = 1)),
        TropicalTV(rectOf(w = 3, h = 1)),


        // Fish Tanks

        SmallFishTank(rectOf(w = 2, h = 1)),
        ModernFishTank(rectOf(w = 2, h = 1)),
        LargeFishTank(rectOf(w = 4, h = 1)),
        DeluxeFishTank(rectOf(w = 5, h = 1)),
        AquaticSanctuary(rectOf(w = 7, h = 1)),


        // Misc

        ChinaCabinet(rectOf(w = 3, h = 1)),

        // TODO : IndustrialPipe(RECT_1x1),
    }

    sealed class Couch : IndoorFurniture, Rotatable4(
        regularSize = rectOf(w = 3, h = 1),
        rotatedSize = rectOf(w = 2, h = 2),
    ) {

        data class BlueCouch(override var rotation: Rotations4 = R0) : Couch()
        data class BrownCouch(override var rotation: Rotations4 = R0) : Couch()
        data class GreenCouch(override var rotation: Rotations4 = R0) : Couch()
        data class RedCouch(override var rotation: Rotations4 = R0) : Couch()
        data class YellowCouch(override var rotation: Rotations4 = R0) : Couch()
        data class DarkCouch(override var rotation: Rotations4 = R0) : Couch()
        data class WoodsyCouch(override var rotation: Rotations4 = R0) : Couch()
        data class WizardCouch(override var rotation: Rotations4 = R0) : Couch()
    }

    data class LargeBrownCouch(override var rotation: Rotations4 = R0) : IndoorFurniture, Rotatable4(
        regularSize = rectOf(w = 4, h = 1),
        rotatedSize = rectOf(w = 2, h = 3),
    )

    sealed class Armchair : IndoorFurniture, Rotatable4(
        regularSize = rectOf(w = 2, h = 1),
        rotatedSize = rectOf(w = 2, h = 1),
    ) {

        data class BlueArmchair(override var rotation: Rotations4 = R0) : Armchair()
        data class BrownArmchair(override var rotation: Rotations4 = R0) : Armchair()
        data class GreenArmchair(override var rotation: Rotations4 = R0) : Armchair()
        data class RedArmchair(override var rotation: Rotations4 = R0) : Armchair()
        data class YellowArmchair(override var rotation: Rotations4 = R0) : Armchair()
    }

    sealed class Dresser : IndoorFurniture, Rotatable4(
        regularSize = rectOf(w = 2, h = 1),
        rotatedSize = rectOf(w = 1, h = 2),
    ) {

        data class BirchDresser(override var rotation: Rotations4 = R0) : Dresser()
        data class OakDresser(override var rotation: Rotations4 = R0) : Dresser()
        data class WalnutDresser(override var rotation: Rotations4 = R0) : Dresser()
        data class MahoganyDresser(override var rotation: Rotations4 = R0) : Dresser()
    }


    override val type: IndoorFurnitureType get() = IndoorFurnitureType


    companion object {

        val all by lazy {
            IndoorFurniture.SimpleIndoorFurniture.values().toSet() + setOf(
                Couch.BlueCouch(),
                Couch.BrownCouch(),
                Couch.GreenCouch(),
                Couch.RedCouch(),
                Couch.YellowCouch(),
                Couch.DarkCouch(),
                Couch.WoodsyCouch(),
                Couch.WizardCouch(),
                LargeBrownCouch(),
                Armchair.BlueArmchair(),
                Armchair.BrownArmchair(),
                Armchair.GreenArmchair(),
                Armchair.RedArmchair(),
                Armchair.YellowArmchair(),
                Dresser.BirchDresser(),
                Dresser.OakDresser(),
                Dresser.WalnutDresser(),
                Dresser.MahoganyDresser(),
            )
        }
    }
}

private val bookcaseSize = rectOf(w = 2, h = 1)
private val fireplaceSize = rectOf(w = 2, h = 1)
private val floorLampSize = rectOf(w = 1, h = 1)

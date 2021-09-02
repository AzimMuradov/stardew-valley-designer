/*
 * Copyright 2021-2021 Azim Muradov
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

package me.azimmuradov.svc.engine.impl.entity.ids

import me.azimmuradov.svc.engine.Rect
import me.azimmuradov.svc.engine.impl.Rects.RECT_1x1
import me.azimmuradov.svc.engine.impl.Rects.RECT_1x2
import me.azimmuradov.svc.engine.impl.Rects.RECT_2x1
import me.azimmuradov.svc.engine.impl.Rects.RECT_2x2
import me.azimmuradov.svc.engine.impl.Rects.RECT_2x3
import me.azimmuradov.svc.engine.impl.Rects.RECT_3x1
import me.azimmuradov.svc.engine.impl.entity.ObjectType.FurnitureType.IndoorFurnitureType
import me.azimmuradov.svc.engine.impl.entity.ids.RotatableFlavor.RotatableFlavor4
import me.azimmuradov.svc.engine.impl.entity.ids.RotatableFlavor.Rotations.Rotations4
import me.azimmuradov.svc.engine.impl.entity.ids.RotatableFlavor.Rotations.Rotations4.R0


sealed interface IndoorFurniture : CartographerEntityId<IndoorFurnitureType> {

    enum class SimpleIndoorFurniture(override val size: Rect) : IndoorFurniture {

        // Bookcases

        ArtistBookcase(RECT_2x1),
        ModernBookcase(RECT_2x1),
        LuxuryBookcase(RECT_2x1),
        DarkBookcase(RECT_2x1),


        // Fireplaces

        BrickFireplace(RECT_2x1),
        ElegantFireplace(RECT_2x1),
        IridiumFireplace(RECT_2x1),
        MonsterFireplace(RECT_2x1),
        StoneFireplace(RECT_2x1),
        StoveFireplace(RECT_2x1),


        // Floor Lamps

        CountryLamp(RECT_1x1),
        ModernLamp(RECT_1x1),
        ClassicLamp(RECT_1x1),
        BoxLamp(RECT_1x1),
        CandleLamp(RECT_1x1),
        OrnateLamp(RECT_1x1),


        // TVs

        FloorTV(RECT_2x1),
        BudgetTV(RECT_2x2),
        PlasmaTV(RECT_3x1),
        TropicalTV(RECT_3x1),


        // Fish Tanks

        SmallFishTank(RECT_2x1),
        ModernFishTank(RECT_2x1),
        LargeFishTank(size = Rect(w = 4, h = 1)),
        DeluxeFishTank(size = Rect(w = 5, h = 1)),
        AquaticSanctuaryRect(size = Rect(w = 7, h = 1)),


        // Misc

        ChinaCabinet(RECT_3x1),

        // TODO : IndustrialPipe(RECT_1x1),
    }

    sealed class Couch : IndoorFurniture, RotatableFlavor4(
        regularSize = RECT_3x1,
        rotatedSize = RECT_2x2,
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

    data class LargeBrownCouch(override var rotation: Rotations4 = R0) : IndoorFurniture, RotatableFlavor4(
        regularSize = Rect(w = 4, h = 1),
        rotatedSize = RECT_2x3,
    )

    sealed class Armchair : IndoorFurniture, RotatableFlavor4(
        regularSize = RECT_2x1,
        rotatedSize = RECT_2x1,
    ) {
        data class BlueArmchair(override var rotation: Rotations4 = R0) : Armchair()
        data class BrownArmchair(override var rotation: Rotations4 = R0) : Armchair()
        data class GreenArmchair(override var rotation: Rotations4 = R0) : Armchair()
        data class RedArmchair(override var rotation: Rotations4 = R0) : Armchair()
        data class YellowArmchair(override var rotation: Rotations4 = R0) : Armchair()
    }

    sealed class Dresser : IndoorFurniture, RotatableFlavor4(
        regularSize = RECT_2x1,
        rotatedSize = RECT_1x2,
    ) {
        data class BirchDresser(override var rotation: Rotations4 = R0) : Dresser()
        data class OakDresser(override var rotation: Rotations4 = R0) : Dresser()
        data class WalnutDresser(override var rotation: Rotations4 = R0) : Dresser()
        data class MahoganyDresser(override var rotation: Rotations4 = R0) : Dresser()
    }


    override val type: IndoorFurnitureType get() = IndoorFurnitureType
}
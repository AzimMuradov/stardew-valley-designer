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
import me.azimmuradov.svc.engine.impl.Rects.RECT_3x1
import me.azimmuradov.svc.engine.impl.Rects.RECT_3x2
import me.azimmuradov.svc.engine.impl.entity.ObjectType.FurnitureType.UniversalFurnitureType
import me.azimmuradov.svc.engine.impl.entity.ids.RotatableFlavor.RotatableFlavor2
import me.azimmuradov.svc.engine.impl.entity.ids.RotatableFlavor.RotatableFlavor4
import me.azimmuradov.svc.engine.impl.entity.ids.RotatableFlavor.Rotations.Rotations2
import me.azimmuradov.svc.engine.impl.entity.ids.RotatableFlavor.Rotations.Rotations4


sealed interface UniversalFurniture : CartographerEntityId<UniversalFurnitureType> {

    enum class SimpleUniversalFurniture(override val size: Rect) : UniversalFurniture {

        // Stools

        GreenOfficeStool(RECT_1x1),
        OrangeOfficeStool(RECT_1x1),
        GreenStool(RECT_1x1),
        BlueStool(RECT_1x1),


        // Tables

        OakTable(RECT_2x2),
        OakTeaTable(RECT_2x2),
        BirchTable(RECT_2x2),
        BirchTeaTable(RECT_2x2),
        MahoganyTable(RECT_2x2),
        MahoganyTeaTable(RECT_2x2),
        WalnutTable(RECT_2x2),
        WalnutTeaTable(RECT_2x2),
        ModernTable(RECT_2x2),
        ModernTeaTable(RECT_2x2),
        PuzzleTable(RECT_2x2),
        SunTable(RECT_2x2),
        MoonTable(RECT_2x2),
        LuxuryTable(RECT_2x2),
        DivinerTable(RECT_2x2),
        GrandmotherEndTable(RECT_1x1),
        PubTable(RECT_2x2),
        LuauTable(RECT_2x2),
        DarkTable(RECT_2x2),
        CandyTable(RECT_2x2),
        WinterTable(RECT_2x2),
        WinterEndTable(RECT_1x1),
        NeolithicTable(RECT_2x2),


        // House Plants

        HousePlant1(RECT_1x1),
        HousePlant2(RECT_1x1),
        HousePlant3(RECT_1x1),
        HousePlant4(RECT_1x1),
        HousePlant5(RECT_1x1),
        HousePlant6(RECT_1x1),
        HousePlant7(RECT_1x1),
        HousePlant8(RECT_1x1),
        HousePlant9(RECT_1x1),
        HousePlant10(RECT_1x1),
        HousePlant11(RECT_1x1),
        HousePlant12(RECT_1x1),
        HousePlant13(RECT_1x1),
        HousePlant14(RECT_1x1),
        HousePlant15(RECT_1x1),


        // Freestanding Decorative Plants

        DriedSunflowers(RECT_1x1),
        BonsaiTree(RECT_1x1),
        SmallPine(RECT_1x1),
        TreeColumn(RECT_1x1),
        SmallPlant(RECT_1x1),
        TablePlant(RECT_1x1),
        DeluxeTree(RECT_3x1),
        ExoticTree(RECT_3x1),
        IndoorPalm(RECT_1x1),
        TopiaryTree(RECT_1x1),
        ManicuredPine(RECT_1x1),
        TreeOfTheWinterStar(RECT_3x2),
        LongCactus(RECT_1x1),
        LongPalm(RECT_1x1),


        // Torches

        JungleTorch(RECT_1x1),
        PlainTorch(RECT_1x1),
        StumpTorch(RECT_1x1),


        // Misc

        CeramicPillar(RECT_1x1),
        GoldPillar(RECT_1x1),
        TotemPole(RECT_1x1),
        DecorativeBowl(RECT_1x1),
        DecorativeLantern(RECT_1x1),
        Globe(RECT_1x1),
        ModelShip(RECT_1x1),
        SmallCrystal(RECT_1x1),
        FutanBear(RECT_1x1),
        DecorativeTrashCan(RECT_1x1),


        // Other Decorations

        BearStatue(RECT_2x1),
        ChickenStatue(RECT_1x1),
        LgFutanBear(RECT_2x1),
        ObsidianVase(RECT_1x1),
        SkeletonStatue(RECT_1x1),
        SlothSkeletonL(RECT_1x1),
        SlothSkeletonM(RECT_1x1),
        SlothSkeletonR(RECT_1x1),
        StandingGeode(RECT_1x1),
        ButterflyHutch(RECT_2x1),
        LeahSculpture(RECT_1x1),
        SamBoombox(RECT_1x1),
        FutanRabbit(RECT_1x1),
        SmallJunimoPlush1(RECT_1x1),
        SmallJunimoPlush2(RECT_1x1),
        SmallJunimoPlush3(RECT_1x1),
        SmallJunimoPlush4(RECT_1x1),
        GreenSerpentStatue(RECT_1x1),
        PurpleSerpentStatue(RECT_1x1),
        BoboStatue(RECT_2x1),
        WumbusStatue(RECT_2x1),
        JunimoPlush(RECT_2x1),
        GourmandStatue(RECT_2x1),
        IridiumKrobus(RECT_1x1),
        SquirrelFigurine(RECT_1x1),


        // Catalogues

        Catalogue(RECT_1x1),
        FurnitureCatalogue(RECT_2x2),
    }

    sealed class Chair : UniversalFurniture, RotatableFlavor4(
        regularSize = RECT_1x1,
        rotatedSize = RECT_1x1,
    ) {
        data class OakChair(override var rotation: Rotations4 = Rotations4.R0) : Chair()
        data class WalnutChair(override var rotation: Rotations4 = Rotations4.R0) : Chair()
        data class BirchChair(override var rotation: Rotations4 = Rotations4.R0) : Chair()
        data class MahoganyChair(override var rotation: Rotations4 = Rotations4.R0) : Chair()
        data class RedDinerChair(override var rotation: Rotations4 = Rotations4.R0) : Chair()
        data class BlueDinerChair(override var rotation: Rotations4 = Rotations4.R0) : Chair()
        data class CountryChair(override var rotation: Rotations4 = Rotations4.R0) : Chair()
        data class BreakfastChair(override var rotation: Rotations4 = Rotations4.R0) : Chair()
        data class PinkOfficeChair(override var rotation: Rotations4 = Rotations4.R0) : Chair()
        data class PurpleOfficeChair(override var rotation: Rotations4 = Rotations4.R0) : Chair()
        data class DarkThrone(override var rotation: Rotations4 = Rotations4.R0) : Chair()
        data class DiningChairYellow(override var rotation: Rotations4 = Rotations4.R0) : Chair()
        data class DiningChairRed(override var rotation: Rotations4 = Rotations4.R0) : Chair()
        data class GreenPlushSeat(override var rotation: Rotations4 = Rotations4.R0) : Chair()
        data class PinkPlushSeat(override var rotation: Rotations4 = Rotations4.R0) : Chair()
        data class WinterChair(override var rotation: Rotations4 = Rotations4.R0) : Chair()
        data class GroovyChair(override var rotation: Rotations4 = Rotations4.R0) : Chair()
        data class CuteChair(override var rotation: Rotations4 = Rotations4.R0) : Chair()
        data class StumpSeat(override var rotation: Rotations4 = Rotations4.R0) : Chair()
        data class MetalChair(override var rotation: Rotations4 = Rotations4.R0) : Chair()
        data class KingChair(override var rotation: Rotations4 = Rotations4.R0) : Chair()
        data class CrystalChair(override var rotation: Rotations4 = Rotations4.R0) : Chair()
        data class TropicalChair(override var rotation: Rotations4 = Rotations4.R0) : Chair()
    }

    sealed class Bench : UniversalFurniture, RotatableFlavor4(
        regularSize = RECT_2x1,
        rotatedSize = RECT_1x2,
    ) {
        data class BirchBench(override var rotation: Rotations4 = Rotations4.R0) : Bench()
        data class OakBench(override var rotation: Rotations4 = Rotations4.R0) : Bench()
        data class WalnutBench(override var rotation: Rotations4 = Rotations4.R0) : Bench()
        data class MahoganyBench(override var rotation: Rotations4 = Rotations4.R0) : Bench()
        data class ModernBench(override var rotation: Rotations4 = Rotations4.R0) : Bench()
    }

    sealed class WoodenEndTable : UniversalFurniture, RotatableFlavor2(
        regularSize = RECT_1x1,
        rotatedSize = RECT_1x1,
    ) {
        data class OakEndTable(override var rotation: Rotations2 = Rotations2.R0) : WoodenEndTable()
        data class BirchEndTable(override var rotation: Rotations2 = Rotations2.R0) : WoodenEndTable()
        data class MahoganyEndTable(override var rotation: Rotations2 = Rotations2.R0) : WoodenEndTable()
        data class WalnutEndTable(override var rotation: Rotations2 = Rotations2.R0) : WoodenEndTable()
        data class ModernEndTable(override var rotation: Rotations2 = Rotations2.R0) : WoodenEndTable()
    }

    data class CoffeeTable(override var rotation: Rotations2 = Rotations2.R0) : UniversalFurniture, RotatableFlavor2(
        regularSize = RECT_1x2,
        rotatedSize = RECT_2x1,
    )

    data class StoneSlab(override var rotation: Rotations2 = Rotations2.R0) : UniversalFurniture, RotatableFlavor2(
        regularSize = RECT_1x2,
        rotatedSize = RECT_2x1,
    )

    sealed class LongTable : UniversalFurniture, RotatableFlavor2(
        regularSize = Rect(w = 2, h = 4),
        rotatedSize = Rect(w = 5, h = 2),
    ) {
        data class ModernDiningTable(override var rotation: Rotations2 = Rotations2.R0) : LongTable()
        data class MahoganyDiningTable(override var rotation: Rotations2 = Rotations2.R0) : LongTable()
        data class FestiveDiningTable(override var rotation: Rotations2 = Rotations2.R0) : LongTable()
        data class WinterDiningTable(override var rotation: Rotations2 = Rotations2.R0) : LongTable()
    }


    override val type: UniversalFurnitureType get() = UniversalFurnitureType
}
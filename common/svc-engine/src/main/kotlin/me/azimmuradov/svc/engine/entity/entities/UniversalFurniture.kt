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

import me.azimmuradov.svc.engine.entity.ObjectType.FurnitureType.UniversalFurnitureType
import me.azimmuradov.svc.engine.entity.RectsProvider.rectOf
import me.azimmuradov.svc.engine.entity.Rotatable.Rotatable2
import me.azimmuradov.svc.engine.entity.Rotatable.Rotatable4
import me.azimmuradov.svc.engine.entity.Rotations.Rotations2
import me.azimmuradov.svc.engine.entity.Rotations.Rotations4
import me.azimmuradov.svc.engine.geometry.Rect


sealed interface UniversalFurniture : Entity<UniversalFurnitureType> {

    enum class SimpleUniversalFurniture(override val size: Rect) : UniversalFurniture {

        // Stools

        GreenOfficeStool(stoolSize),
        OrangeOfficeStool(stoolSize),
        GreenStool(stoolSize),
        BlueStool(stoolSize),


        // Tables

        OakTable(tableSize),
        OakTeaTable(tableSize),
        BirchTable(tableSize),
        BirchTeaTable(tableSize),
        MahoganyTable(tableSize),
        MahoganyTeaTable(tableSize),
        WalnutTable(tableSize),
        WalnutTeaTable(tableSize),
        ModernTable(tableSize),
        ModernTeaTable(tableSize),
        ModernEndTable(endTableSize),
        PuzzleTable(tableSize),
        SunTable(tableSize),
        MoonTable(tableSize),
        LuxuryTable(tableSize),
        DivinerTable(tableSize),
        GrandmotherEndTable(endTableSize),
        PubTable(tableSize),
        LuauTable(tableSize),
        DarkTable(tableSize),
        CandyTable(tableSize),
        WinterTable(tableSize),
        WinterEndTable(endTableSize),
        NeolithicTable(tableSize),


        // House Plants

        HousePlant1(housePlantSize),
        HousePlant2(housePlantSize),
        HousePlant3(housePlantSize),
        HousePlant4(housePlantSize),
        HousePlant5(housePlantSize),
        HousePlant6(housePlantSize),
        HousePlant7(housePlantSize),
        HousePlant8(housePlantSize),
        HousePlant9(housePlantSize),
        HousePlant10(housePlantSize),
        HousePlant11(housePlantSize),
        HousePlant12(housePlantSize),
        HousePlant13(housePlantSize),
        HousePlant14(housePlantSize),
        HousePlant15(housePlantSize),


        // Freestanding Decorative Plants

        DriedSunflowers(decorativePlantSize),
        BonsaiTree(decorativePlantSize),
        SmallPine(decorativePlantSize),
        TreeColumn(decorativePlantSize),
        SmallPlant(decorativePlantSize),
        TablePlant(decorativePlantSize),
        DeluxeTree(decorativeTreeSize),
        ExoticTree(decorativeTreeSize),
        IndoorPalm(decorativePlantSize),
        TopiaryTree(decorativePlantSize),
        ManicuredPine(decorativePlantSize),
        TreeOfTheWinterStar(rectOf(w = 3, h = 2)),
        LongCactus(decorativePlantSize),
        LongPalm(decorativePlantSize),


        // Torches

        JungleTorch(torchSize),
        PlainTorch(torchSize),
        StumpTorch(torchSize),


        // Misc

        CeramicPillar(miscSize),
        GoldPillar(miscSize),
        TotemPole(miscSize),
        DecorativeBowl(miscSize),
        DecorativeLantern(miscSize),
        Globe(miscSize),
        ModelShip(miscSize),
        SmallCrystal(miscSize),
        FutanBear(miscSize),
        DecorativeTrashCan(miscSize),


        // Other Decorations

        BearStatue(bigOtherDecorSize),
        ChickenStatue(otherDecorSize),
        LgFutanBear(bigOtherDecorSize),
        ObsidianVase(otherDecorSize),
        SkeletonStatue(otherDecorSize),
        SlothSkeletonL(otherDecorSize),
        SlothSkeletonM(otherDecorSize),
        SlothSkeletonR(otherDecorSize),
        StandingGeode(otherDecorSize),
        ButterflyHutch(bigOtherDecorSize),
        LeahSculpture(otherDecorSize),
        SamBoombox(otherDecorSize),
        FutanRabbit(otherDecorSize),
        SmallJunimoPlush1(otherDecorSize),
        SmallJunimoPlush2(otherDecorSize),
        SmallJunimoPlush3(otherDecorSize),
        SmallJunimoPlush4(otherDecorSize),
        GreenSerpentStatue(otherDecorSize),
        PurpleSerpentStatue(otherDecorSize),
        BoboStatue(bigOtherDecorSize),
        WumbusStatue(bigOtherDecorSize),
        JunimoPlush(bigOtherDecorSize),
        GourmandStatue(bigOtherDecorSize),
        IridiumKrobus(otherDecorSize),
        SquirrelFigurine(otherDecorSize),


        // Catalogues

        Catalogue(rectOf(w = 1, h = 1)),
        FurnitureCatalogue(rectOf(w = 2, h = 2)),
    }

    sealed class Chair : UniversalFurniture, Rotatable4(
        regularSize = chairSize,
        rotatedSize = chairSize,
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

    sealed class Bench : UniversalFurniture, Rotatable4(
        regularSize = rectOf(w = 2, h = 1),
        rotatedSize = rectOf(w = 1, h = 2),
    ) {

        data class BirchBench(override var rotation: Rotations4 = Rotations4.R0) : Bench()
        data class OakBench(override var rotation: Rotations4 = Rotations4.R0) : Bench()
        data class WalnutBench(override var rotation: Rotations4 = Rotations4.R0) : Bench()
        data class MahoganyBench(override var rotation: Rotations4 = Rotations4.R0) : Bench()
        data class ModernBench(override var rotation: Rotations4 = Rotations4.R0) : Bench()
    }

    sealed class WoodenEndTable : UniversalFurniture, Rotatable2(
        regularSize = endTableSize,
        rotatedSize = endTableSize,
    ) {

        data class OakEndTable(override var rotation: Rotations2 = Rotations2.R0) : WoodenEndTable()
        data class BirchEndTable(override var rotation: Rotations2 = Rotations2.R0) : WoodenEndTable()
        data class MahoganyEndTable(override var rotation: Rotations2 = Rotations2.R0) : WoodenEndTable()
        data class WalnutEndTable(override var rotation: Rotations2 = Rotations2.R0) : WoodenEndTable()
    }

    data class CoffeeTable(override var rotation: Rotations2 = Rotations2.R0) : UniversalFurniture, Rotatable2(
        regularSize = rectOf(w = 1, h = 2),
        rotatedSize = rectOf(w = 2, h = 1),
    )

    data class StoneSlab(override var rotation: Rotations2 = Rotations2.R0) : UniversalFurniture, Rotatable2(
        regularSize = rectOf(w = 1, h = 2),
        rotatedSize = rectOf(w = 2, h = 1),
    )

    sealed class LongTable : UniversalFurniture, Rotatable2(
        regularSize = rectOf(w = 2, h = 4),
        rotatedSize = rectOf(w = 5, h = 2),
    ) {

        data class ModernDiningTable(override var rotation: Rotations2 = Rotations2.R0) : LongTable()
        data class MahoganyDiningTable(override var rotation: Rotations2 = Rotations2.R0) : LongTable()
        data class FestiveDiningTable(override var rotation: Rotations2 = Rotations2.R0) : LongTable()
        data class WinterDiningTable(override var rotation: Rotations2 = Rotations2.R0) : LongTable()
    }


    override val type: UniversalFurnitureType get() = UniversalFurnitureType


    companion object {

        val all by lazy {
            UniversalFurniture.SimpleUniversalFurniture.values().toSet() + setOf(
                Chair.OakChair(),
                Chair.WalnutChair(),
                Chair.BirchChair(),
                Chair.MahoganyChair(),
                Chair.RedDinerChair(),
                Chair.BlueDinerChair(),
                Chair.CountryChair(),
                Chair.BreakfastChair(),
                Chair.PinkOfficeChair(),
                Chair.PurpleOfficeChair(),
                Chair.DarkThrone(),
                Chair.DiningChairYellow(),
                Chair.DiningChairRed(),
                Chair.GreenPlushSeat(),
                Chair.PinkPlushSeat(),
                Chair.WinterChair(),
                Chair.GroovyChair(),
                Chair.CuteChair(),
                Chair.StumpSeat(),
                Chair.MetalChair(),
                Chair.KingChair(),
                Chair.CrystalChair(),
                Chair.TropicalChair(),
                Bench.BirchBench(),
                Bench.OakBench(),
                Bench.WalnutBench(),
                Bench.MahoganyBench(),
                Bench.ModernBench(),
                WoodenEndTable.OakEndTable(),
                WoodenEndTable.BirchEndTable(),
                WoodenEndTable.MahoganyEndTable(),
                WoodenEndTable.WalnutEndTable(),
                CoffeeTable(),
                StoneSlab(),
                LongTable.ModernDiningTable(),
                LongTable.MahoganyDiningTable(),
                LongTable.FestiveDiningTable(),
                LongTable.WinterDiningTable(),
            )
        }
    }
}

private val stoolSize = rectOf(w = 1, h = 1)
private val tableSize = rectOf(w = 2, h = 2)
private val endTableSize = rectOf(w = 1, h = 1)
private val housePlantSize = rectOf(w = 1, h = 1)
private val decorativePlantSize = rectOf(w = 1, h = 1)
private val decorativeTreeSize = rectOf(w = 3, h = 1)
private val torchSize = rectOf(w = 1, h = 1)
private val miscSize = rectOf(w = 1, h = 1)
private val otherDecorSize = rectOf(w = 1, h = 1)
private val bigOtherDecorSize = rectOf(w = 2, h = 1)
private val chairSize = rectOf(w = 1, h = 1)

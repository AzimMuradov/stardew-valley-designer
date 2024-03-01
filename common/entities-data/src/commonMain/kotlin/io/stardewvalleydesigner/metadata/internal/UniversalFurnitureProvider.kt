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

import io.stardewvalleydesigner.engine.entity.Rotations
import io.stardewvalleydesigner.engine.entity.Rotations.Rotations2
import io.stardewvalleydesigner.engine.entity.Rotations.Rotations4
import io.stardewvalleydesigner.engine.entity.UniversalFurniture
import io.stardewvalleydesigner.engine.entity.UniversalFurniture.Bench.*
import io.stardewvalleydesigner.engine.entity.UniversalFurniture.Chair.*
import io.stardewvalleydesigner.engine.entity.UniversalFurniture.CoffeeTable
import io.stardewvalleydesigner.engine.entity.UniversalFurniture.LongTable.*
import io.stardewvalleydesigner.engine.entity.UniversalFurniture.SimpleUniversalFurniture.*
import io.stardewvalleydesigner.engine.entity.UniversalFurniture.StoneSlab
import io.stardewvalleydesigner.engine.entity.UniversalFurniture.WoodenEndTable.*
import io.stardewvalleydesigner.metadata.QualifiedEntity
import io.stardewvalleydesigner.metadata.QualifiedEntityData


internal fun universalFurniture(qe: QualifiedEntity<UniversalFurniture>): QualifiedEntityData {
    val entity = qe.entity

    fun furniture(x: Int, y: Int, w: Int, h: Int) =
        furniture(entity, x, y, w, h)

    fun furniture(id: Int, x: Int, y: Int, w: Int, h: Int, r: Rotations) =
        furniture(entity, id, x, y, w, h, r)

    return when (entity) {
        GreenOfficeStool -> furniture(x = 30, y = 0, w = 1, h = 2)
        OrangeOfficeStool -> furniture(x = 31, y = 0, w = 1, h = 2)
        GreenStool -> furniture(x = 30, y = 2, w = 1, h = 2)
        BlueStool -> furniture(x = 31, y = 2, w = 1, h = 2)

        OakTable -> furniture(x = 0, y = 35, w = 2, h = 3)
        OakTeaTable -> furniture(x = 0, y = 38, w = 2, h = 2)
        BirchTable -> furniture(x = 4, y = 35, w = 2, h = 3)
        BirchTeaTable -> furniture(x = 4, y = 38, w = 2, h = 2)
        MahoganyTable -> furniture(x = 6, y = 35, w = 2, h = 3)
        MahoganyTeaTable -> furniture(x = 6, y = 38, w = 2, h = 2)
        WalnutTable -> furniture(x = 2, y = 35, w = 2, h = 3)
        WalnutTeaTable -> furniture(x = 2, y = 38, w = 2, h = 2)
        ModernTable -> furniture(x = 12, y = 35, w = 2, h = 3)
        ModernTeaTable -> furniture(x = 8, y = 38, w = 2, h = 2)
        ModernEndTable -> furniture(x = 23, y = 43, w = 1, h = 2)
        PuzzleTable -> furniture(x = 22, y = 35, w = 2, h = 3)
        SunTable -> furniture(x = 8, y = 35, w = 2, h = 3)
        MoonTable -> furniture(x = 10, y = 35, w = 2, h = 3)
        LuxuryTable -> furniture(x = 16, y = 35, w = 2, h = 3)
        DivinerTable -> furniture(x = 18, y = 35, w = 2, h = 3)
        GrandmotherEndTable -> furniture(x = 24, y = 43, w = 1, h = 2)
        PubTable -> furniture(x = 14, y = 35, w = 2, h = 3)
        LuauTable -> furniture(x = 28, y = 35, w = 2, h = 3)
        DarkTable -> furniture(x = 30, y = 35, w = 2, h = 3)
        CandyTable -> furniture(x = 26, y = 35, w = 2, h = 3)
        WinterTable -> furniture(x = 24, y = 35, w = 2, h = 3)
        WinterEndTable -> furniture(x = 25, y = 43, w = 1, h = 2)
        NeolithicTable -> furniture(x = 20, y = 35, w = 2, h = 3)

        HousePlant1 -> furniture(x = 0, y = 43, w = 1, h = 2)
        HousePlant2 -> furniture(x = 1, y = 43, w = 1, h = 2)
        HousePlant3 -> furniture(x = 2, y = 43, w = 1, h = 2)
        HousePlant4 -> furniture(x = 3, y = 43, w = 1, h = 2)
        HousePlant5 -> furniture(x = 4, y = 43, w = 1, h = 2)
        HousePlant6 -> furniture(x = 5, y = 43, w = 1, h = 2)
        HousePlant7 -> furniture(x = 6, y = 43, w = 1, h = 2)
        HousePlant8 -> furniture(x = 7, y = 43, w = 1, h = 2)
        HousePlant9 -> furniture(x = 8, y = 43, w = 1, h = 2)
        HousePlant10 -> furniture(x = 9, y = 43, w = 1, h = 2)
        HousePlant11 -> furniture(x = 10, y = 43, w = 1, h = 2)
        HousePlant12 -> furniture(x = 11, y = 43, w = 1, h = 2)
        HousePlant13 -> furniture(x = 12, y = 43, w = 1, h = 2)
        HousePlant14 -> furniture(x = 13, y = 43, w = 1, h = 2)
        HousePlant15 -> furniture(x = 14, y = 43, w = 1, h = 2)

        DriedSunflowers -> furniture(x = 27, y = 40, w = 1, h = 2)
        BonsaiTree -> furniture(x = 20, y = 54, w = 1, h = 2)
        SmallPine -> furniture(x = 19, y = 54, w = 1, h = 2)
        TreeColumn -> furniture(x = 16, y = 54, w = 1, h = 3)
        SmallPlant -> furniture(x = 18, y = 42, w = 1, h = 1)
        TablePlant -> furniture(x = 19, y = 42, w = 1, h = 1)
        DeluxeTree -> furniture(x = 29, y = 30, w = 3, h = 5)
        ExoticTree -> furniture(x = 26, y = 30, w = 3, h = 5)
        IndoorPalm -> furniture(x = 14, y = 40, w = 1, h = 3)
        TopiaryTree -> furniture(x = 17, y = 40, w = 1, h = 3)
        ManicuredPine -> furniture(x = 16, y = 40, w = 1, h = 3)
        TreeOfTheWinterStar -> furniture(x = 0, y = 45, w = 3, h = 5)
        LongCactus -> furniture(x = 24, y = 30, w = 1, h = 3)
        LongPalm -> furniture(x = 25, y = 30, w = 1, h = 4)

        JungleTorch -> furniture(x = 27, y = 72, w = 1, h = 2)
        PlainTorch -> furniture(x = 29, y = 74, w = 1, h = 2)
        StumpTorch -> furniture(x = 30, y = 74, w = 1, h = 2)

        CeramicPillar -> furniture(x = 11, y = 40, w = 1, h = 3)
        GoldPillar -> furniture(x = 12, y = 40, w = 1, h = 3)
        TotemPole -> furniture(x = 15, y = 40, w = 1, h = 3)
        DecorativeBowl -> furniture(x = 20, y = 42, w = 1, h = 1)
        DecorativeLantern -> furniture(x = 25, y = 42, w = 1, h = 1)
        Globe -> furniture(x = 22, y = 42, w = 1, h = 1)
        ModelShip -> furniture(x = 23, y = 42, w = 1, h = 1)
        SmallCrystal -> furniture(x = 24, y = 42, w = 1, h = 1)
        FutanBear -> furniture(x = 21, y = 42, w = 1, h = 1)
        DecorativeTrashCan -> furniture(x = 27, y = 75, w = 1, h = 2)

        BearStatue -> furniture(x = 7, y = 52, w = 2, h = 4)
        ChickenStatue -> furniture(x = 25, y = 40, w = 1, h = 2)
        LgFutanBear -> furniture(x = 5, y = 52, w = 2, h = 2)
        ObsidianVase -> furniture(x = 19, y = 40, w = 1, h = 2)
        SkeletonStatue -> furniture(x = 24, y = 40, w = 1, h = 2)
        SlothSkeletonL -> furniture(x = 21, y = 40, w = 1, h = 2)
        SlothSkeletonM -> furniture(x = 22, y = 40, w = 1, h = 2)
        SlothSkeletonR -> furniture(x = 23, y = 40, w = 1, h = 2)
        StandingGeode -> furniture(x = 18, y = 40, w = 1, h = 2)
        ButterflyHutch -> furniture(x = 19, y = 61, w = 2, h = 3)
        LeahSculpture -> furniture(x = 26, y = 40, w = 1, h = 2)
        SamBoombox -> furniture(x = 29, y = 40, w = 1, h = 2)
        FutanRabbit -> furniture(x = 4, y = 55, w = 1, h = 1)
        SmallJunimoPlush1 -> furniture(x = 0, y = 55, w = 1, h = 1)
        SmallJunimoPlush2 -> furniture(x = 1, y = 55, w = 1, h = 1)
        SmallJunimoPlush3 -> furniture(x = 2, y = 55, w = 1, h = 1)
        SmallJunimoPlush4 -> furniture(x = 3, y = 55, w = 1, h = 1)
        GreenSerpentStatue -> furniture(x = 31, y = 45, w = 1, h = 3)
        PurpleSerpentStatue -> furniture(x = 31, y = 42, w = 1, h = 3)
        BoboStatue -> furniture(x = 29, y = 42, w = 2, h = 3)
        WumbusStatue -> furniture(x = 27, y = 42, w = 2, h = 3)
        JunimoPlush -> furniture(x = 5, y = 54, w = 2, h = 2)
        GourmandStatue -> furniture(x = 28, y = 72, w = 2, h = 2)
        IridiumKrobus -> furniture(x = 28, y = 74, w = 1, h = 3)
        SquirrelFigurine -> furniture(x = 30, y = 87, w = 1, h = 1)

        Catalogue -> furniture(x = 28, y = 40, w = 1, h = 2)
        FurnitureCatalogue -> furniture(x = 10, y = 38, w = 2, h = 2)


        is OakChair -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 0, x = 0, y = 0, w = 1, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is WalnutChair -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 3, x = 3, y = 0, w = 1, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is BirchChair -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 6, x = 6, y = 0, w = 1, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is MahoganyChair -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 9, x = 9, y = 0, w = 1, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is RedDinerChair -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 12, x = 12, y = 0, w = 1, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is BlueDinerChair -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 15, x = 15, y = 0, w = 1, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is CountryChair -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 18, x = 18, y = 0, w = 1, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is BreakfastChair -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 21, x = 21, y = 0, w = 1, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is PinkOfficeChair -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 24, x = 24, y = 0, w = 1, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is PurpleOfficeChair -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 27, x = 27, y = 0, w = 1, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is DarkThrone -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 64, x = 0, y = 2, w = 1, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is DiningChairYellow -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 67, x = 3, y = 2, w = 1, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is DiningChairRed -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 70, x = 6, y = 2, w = 1, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is GreenPlushSeat -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 73, x = 9, y = 2, w = 1, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is PinkPlushSeat -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 76, x = 12, y = 2, w = 1, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is WinterChair -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 79, x = 15, y = 2, w = 1, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is GroovyChair -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 82, x = 18, y = 2, w = 1, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is CuteChair -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 85, x = 21, y = 2, w = 1, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is StumpSeat -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 88, x = 24, y = 2, w = 1, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is MetalChair -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 91, x = 27, y = 2, w = 1, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is KingChair -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 128, x = 0, y = 4, w = 1, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is CrystalChair -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 131, x = 3, y = 4, w = 1, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is TropicalChair -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 134, x = 6, y = 4, w = 1, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }


        is BirchBench -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 202, x = 10, y = 6, w = 2, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is OakBench -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 192, x = 0, y = 6, w = 2, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is WalnutBench -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 197, x = 5, y = 6, w = 2, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is MahoganyBench -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 207, x = 15, y = 6, w = 2, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is ModernBench -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 212, x = 20, y = 6, w = 2, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }


        is OakEndTable -> when (entity.rotation) {
            Rotations2.R1 -> furniture(id = 1391, x = 15, y = 43, w = 1, h = 2, r = entity.rotation)
            Rotations2.R2 -> TODO()
        }

        is BirchEndTable -> when (entity.rotation) {
            Rotations2.R1 -> furniture(id = 1395, x = 19, y = 43, w = 1, h = 2, r = entity.rotation)
            Rotations2.R2 -> TODO()
        }

        is MahoganyEndTable -> when (entity.rotation) {
            Rotations2.R1 -> furniture(id = 1397, x = 21, y = 43, w = 1, h = 2, r = entity.rotation)
            Rotations2.R2 -> TODO()
        }

        is WalnutEndTable -> when (entity.rotation) {
            Rotations2.R1 -> furniture(id = 1393, x = 17, y = 43, w = 1, h = 2, r = entity.rotation)
            Rotations2.R2 -> TODO()
        }


        is CoffeeTable -> when (entity.rotation) {
            Rotations2.R1 -> furniture(id = 724, x = 20, y = 22, w = 2, h = 2, r = entity.rotation)
            Rotations2.R2 -> TODO()
        }


        is StoneSlab -> when (entity.rotation) {
            Rotations2.R1 -> furniture(id = 727, x = 23, y = 22, w = 2, h = 2, r = entity.rotation)
            Rotations2.R2 -> TODO()
        }


        is ModernDiningTable -> when (entity.rotation) {
            Rotations2.R1 -> furniture(id = 826, x = 26, y = 25, w = 2, h = 5, r = entity.rotation)
            Rotations2.R2 -> TODO()
        }

        is MahoganyDiningTable -> when (entity.rotation) {
            Rotations2.R1 -> furniture(id = 819, x = 19, y = 25, w = 2, h = 5, r = entity.rotation)
            Rotations2.R2 -> TODO()
        }

        is FestiveDiningTable -> when (entity.rotation) {
            Rotations2.R1 -> furniture(id = 812, x = 12, y = 25, w = 2, h = 5, r = entity.rotation)
            Rotations2.R2 -> TODO()
        }

        is WinterDiningTable -> when (entity.rotation) {
            Rotations2.R1 -> furniture(id = 805, x = 5, y = 25, w = 2, h = 5, r = entity.rotation)
            Rotations2.R2 -> TODO()
        }
    }
}

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
import io.stardewvalleydesigner.engine.entity.UniversalFurniture.*
import io.stardewvalleydesigner.metadata.QualifiedEntity
import io.stardewvalleydesigner.metadata.QualifiedEntityData


internal fun universalFurniture(qe: QualifiedEntity<UniversalFurniture>): QualifiedEntityData {
    val entity = qe.entity

    fun furniture(x: Int, y: Int, w: Int, h: Int) =
        furniture(entity, x, y, w, h)

    fun furniture(id: Int, x: Int, y: Int, w: Int, h: Int, r: Rotations) =
        furniture(entity, id, x, y, w, h, r)

    return when (entity) {
        SimpleUniversalFurniture.GreenOfficeStool -> furniture(x = 30, y = 0, w = 1, h = 2)
        SimpleUniversalFurniture.OrangeOfficeStool -> furniture(x = 31, y = 0, w = 1, h = 2)
        SimpleUniversalFurniture.GreenStool -> furniture(x = 30, y = 2, w = 1, h = 2)
        SimpleUniversalFurniture.BlueStool -> furniture(x = 31, y = 2, w = 1, h = 2)

        SimpleUniversalFurniture.OakTable -> furniture(x = 0, y = 35, w = 2, h = 3)
        SimpleUniversalFurniture.OakTeaTable -> furniture(x = 0, y = 38, w = 2, h = 2)
        SimpleUniversalFurniture.BirchTable -> furniture(x = 4, y = 35, w = 2, h = 3)
        SimpleUniversalFurniture.BirchTeaTable -> furniture(x = 4, y = 38, w = 2, h = 2)
        SimpleUniversalFurniture.MahoganyTable -> furniture(x = 6, y = 35, w = 2, h = 3)
        SimpleUniversalFurniture.MahoganyTeaTable -> furniture(x = 6, y = 38, w = 2, h = 2)
        SimpleUniversalFurniture.WalnutTable -> furniture(x = 2, y = 35, w = 2, h = 3)
        SimpleUniversalFurniture.WalnutTeaTable -> furniture(x = 2, y = 38, w = 2, h = 2)
        SimpleUniversalFurniture.ModernTable -> furniture(x = 12, y = 35, w = 2, h = 3)
        SimpleUniversalFurniture.ModernTeaTable -> furniture(x = 8, y = 38, w = 2, h = 2)
        SimpleUniversalFurniture.ModernEndTable -> furniture(x = 23, y = 43, w = 1, h = 2)
        SimpleUniversalFurniture.PuzzleTable -> furniture(x = 22, y = 35, w = 2, h = 3)
        SimpleUniversalFurniture.SunTable -> furniture(x = 8, y = 35, w = 2, h = 3)
        SimpleUniversalFurniture.MoonTable -> furniture(x = 10, y = 35, w = 2, h = 3)
        SimpleUniversalFurniture.LuxuryTable -> furniture(x = 16, y = 35, w = 2, h = 3)
        SimpleUniversalFurniture.DivinerTable -> furniture(x = 18, y = 35, w = 2, h = 3)
        SimpleUniversalFurniture.GrandmotherEndTable -> furniture(x = 24, y = 43, w = 1, h = 2)
        SimpleUniversalFurniture.PubTable -> furniture(x = 14, y = 35, w = 2, h = 3)
        SimpleUniversalFurniture.LuauTable -> furniture(x = 28, y = 35, w = 2, h = 3)
        SimpleUniversalFurniture.DarkTable -> furniture(x = 30, y = 35, w = 2, h = 3)
        SimpleUniversalFurniture.CandyTable -> furniture(x = 26, y = 35, w = 2, h = 3)
        SimpleUniversalFurniture.WinterTable -> furniture(x = 24, y = 35, w = 2, h = 3)
        SimpleUniversalFurniture.WinterEndTable -> furniture(x = 25, y = 43, w = 1, h = 2)
        SimpleUniversalFurniture.NeolithicTable -> furniture(x = 20, y = 35, w = 2, h = 3)

        SimpleUniversalFurniture.HousePlant1 -> furniture(x = 0, y = 43, w = 1, h = 2)
        SimpleUniversalFurniture.HousePlant2 -> furniture(x = 1, y = 43, w = 1, h = 2)
        SimpleUniversalFurniture.HousePlant3 -> furniture(x = 2, y = 43, w = 1, h = 2)
        SimpleUniversalFurniture.HousePlant4 -> furniture(x = 3, y = 43, w = 1, h = 2)
        SimpleUniversalFurniture.HousePlant5 -> furniture(x = 4, y = 43, w = 1, h = 2)
        SimpleUniversalFurniture.HousePlant6 -> furniture(x = 5, y = 43, w = 1, h = 2)
        SimpleUniversalFurniture.HousePlant7 -> furniture(x = 6, y = 43, w = 1, h = 2)
        SimpleUniversalFurniture.HousePlant8 -> furniture(x = 7, y = 43, w = 1, h = 2)
        SimpleUniversalFurniture.HousePlant9 -> furniture(x = 8, y = 43, w = 1, h = 2)
        SimpleUniversalFurniture.HousePlant10 -> furniture(x = 9, y = 43, w = 1, h = 2)
        SimpleUniversalFurniture.HousePlant11 -> furniture(x = 10, y = 43, w = 1, h = 2)
        SimpleUniversalFurniture.HousePlant12 -> furniture(x = 11, y = 43, w = 1, h = 2)
        SimpleUniversalFurniture.HousePlant13 -> furniture(x = 12, y = 43, w = 1, h = 2)
        SimpleUniversalFurniture.HousePlant14 -> furniture(x = 13, y = 43, w = 1, h = 2)
        SimpleUniversalFurniture.HousePlant15 -> furniture(x = 14, y = 43, w = 1, h = 2)

        SimpleUniversalFurniture.DriedSunflowers -> furniture(x = 27, y = 40, w = 1, h = 2)
        SimpleUniversalFurniture.BonsaiTree -> furniture(x = 20, y = 54, w = 1, h = 2)
        SimpleUniversalFurniture.SmallPine -> furniture(x = 19, y = 54, w = 1, h = 2)
        SimpleUniversalFurniture.TreeColumn -> furniture(x = 16, y = 54, w = 1, h = 3)
        SimpleUniversalFurniture.SmallPlant -> furniture(x = 18, y = 42, w = 1, h = 1)
        SimpleUniversalFurniture.TablePlant -> furniture(x = 19, y = 42, w = 1, h = 1)
        SimpleUniversalFurniture.DeluxeTree -> furniture(x = 29, y = 30, w = 3, h = 5)
        SimpleUniversalFurniture.ExoticTree -> furniture(x = 26, y = 30, w = 3, h = 5)
        SimpleUniversalFurniture.IndoorPalm -> furniture(x = 14, y = 40, w = 1, h = 3)
        SimpleUniversalFurniture.TopiaryTree -> furniture(x = 17, y = 40, w = 1, h = 3)
        SimpleUniversalFurniture.ManicuredPine -> furniture(x = 16, y = 40, w = 1, h = 3)
        SimpleUniversalFurniture.TreeOfTheWinterStar -> furniture(x = 0, y = 45, w = 3, h = 5)
        SimpleUniversalFurniture.LongCactus -> furniture(x = 24, y = 30, w = 1, h = 3)
        SimpleUniversalFurniture.LongPalm -> furniture(x = 25, y = 30, w = 1, h = 4)

        SimpleUniversalFurniture.JungleTorch -> furniture(x = 27, y = 72, w = 1, h = 2)
        SimpleUniversalFurniture.PlainTorch -> furniture(x = 29, y = 74, w = 1, h = 2)
        SimpleUniversalFurniture.StumpTorch -> furniture(x = 30, y = 74, w = 1, h = 2)

        SimpleUniversalFurniture.CeramicPillar -> furniture(x = 11, y = 40, w = 1, h = 3)
        SimpleUniversalFurniture.GoldPillar -> furniture(x = 12, y = 40, w = 1, h = 3)
        SimpleUniversalFurniture.TotemPole -> furniture(x = 15, y = 40, w = 1, h = 3)
        SimpleUniversalFurniture.DecorativeBowl -> furniture(x = 20, y = 42, w = 1, h = 1)
        SimpleUniversalFurniture.DecorativeLantern -> furniture(x = 25, y = 42, w = 1, h = 1)
        SimpleUniversalFurniture.Globe -> furniture(x = 22, y = 42, w = 1, h = 1)
        SimpleUniversalFurniture.ModelShip -> furniture(x = 23, y = 42, w = 1, h = 1)
        SimpleUniversalFurniture.SmallCrystal -> furniture(x = 24, y = 42, w = 1, h = 1)
        SimpleUniversalFurniture.FutanBear -> furniture(x = 21, y = 42, w = 1, h = 1)
        SimpleUniversalFurniture.DecorativeTrashCan -> furniture(x = 27, y = 75, w = 1, h = 2)

        SimpleUniversalFurniture.BearStatue -> furniture(x = 7, y = 52, w = 2, h = 4)
        SimpleUniversalFurniture.ChickenStatue -> furniture(x = 25, y = 40, w = 1, h = 2)
        SimpleUniversalFurniture.LgFutanBear -> furniture(x = 5, y = 52, w = 2, h = 2)
        SimpleUniversalFurniture.ObsidianVase -> furniture(x = 19, y = 40, w = 1, h = 2)
        SimpleUniversalFurniture.SkeletonStatue -> furniture(x = 24, y = 40, w = 1, h = 2)
        SimpleUniversalFurniture.SlothSkeletonL -> furniture(x = 21, y = 40, w = 1, h = 2)
        SimpleUniversalFurniture.SlothSkeletonM -> furniture(x = 22, y = 40, w = 1, h = 2)
        SimpleUniversalFurniture.SlothSkeletonR -> furniture(x = 23, y = 40, w = 1, h = 2)
        SimpleUniversalFurniture.StandingGeode -> furniture(x = 18, y = 40, w = 1, h = 2)
        SimpleUniversalFurniture.ButterflyHutch -> furniture(x = 19, y = 61, w = 2, h = 3)
        SimpleUniversalFurniture.LeahSculpture -> furniture(x = 26, y = 40, w = 1, h = 2)
        SimpleUniversalFurniture.SamBoombox -> furniture(x = 29, y = 40, w = 1, h = 2)
        SimpleUniversalFurniture.FutanRabbit -> furniture(x = 4, y = 55, w = 1, h = 1)
        SimpleUniversalFurniture.SmallJunimoPlush1 -> furniture(x = 0, y = 55, w = 1, h = 1)
        SimpleUniversalFurniture.SmallJunimoPlush2 -> furniture(x = 1, y = 55, w = 1, h = 1)
        SimpleUniversalFurniture.SmallJunimoPlush3 -> furniture(x = 2, y = 55, w = 1, h = 1)
        SimpleUniversalFurniture.SmallJunimoPlush4 -> furniture(x = 3, y = 55, w = 1, h = 1)
        SimpleUniversalFurniture.GreenSerpentStatue -> furniture(x = 31, y = 45, w = 1, h = 3)
        SimpleUniversalFurniture.PurpleSerpentStatue -> furniture(x = 31, y = 42, w = 1, h = 3)
        SimpleUniversalFurniture.BoboStatue -> furniture(x = 29, y = 42, w = 2, h = 3)
        SimpleUniversalFurniture.WumbusStatue -> furniture(x = 27, y = 42, w = 2, h = 3)
        SimpleUniversalFurniture.JunimoPlush -> furniture(x = 5, y = 54, w = 2, h = 2)
        SimpleUniversalFurniture.GourmandStatue -> furniture(x = 28, y = 72, w = 2, h = 2)
        SimpleUniversalFurniture.IridiumKrobus -> furniture(x = 28, y = 74, w = 1, h = 3)
        SimpleUniversalFurniture.SquirrelFigurine -> furniture(x = 30, y = 87, w = 1, h = 1)

        SimpleUniversalFurniture.Catalogue -> furniture(x = 28, y = 40, w = 1, h = 2)
        SimpleUniversalFurniture.FurnitureCatalogue -> furniture(x = 10, y = 38, w = 2, h = 2)


        is Chair.OakChair -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 0, x = 0, y = 0, w = 1, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is Chair.WalnutChair -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 3, x = 3, y = 0, w = 1, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is Chair.BirchChair -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 6, x = 6, y = 0, w = 1, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is Chair.MahoganyChair -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 9, x = 9, y = 0, w = 1, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is Chair.RedDinerChair -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 12, x = 12, y = 0, w = 1, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is Chair.BlueDinerChair -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 15, x = 15, y = 0, w = 1, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is Chair.CountryChair -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 18, x = 18, y = 0, w = 1, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is Chair.BreakfastChair -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 21, x = 21, y = 0, w = 1, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is Chair.PinkOfficeChair -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 24, x = 24, y = 0, w = 1, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is Chair.PurpleOfficeChair -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 27, x = 27, y = 0, w = 1, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is Chair.DarkThrone -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 64, x = 0, y = 2, w = 1, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is Chair.DiningChairYellow -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 67, x = 3, y = 2, w = 1, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is Chair.DiningChairRed -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 70, x = 6, y = 2, w = 1, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is Chair.GreenPlushSeat -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 73, x = 9, y = 2, w = 1, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is Chair.PinkPlushSeat -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 76, x = 12, y = 2, w = 1, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is Chair.WinterChair -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 79, x = 15, y = 2, w = 1, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is Chair.GroovyChair -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 82, x = 18, y = 2, w = 1, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is Chair.CuteChair -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 85, x = 21, y = 2, w = 1, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is Chair.StumpSeat -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 88, x = 24, y = 2, w = 1, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is Chair.MetalChair -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 91, x = 27, y = 2, w = 1, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is Chair.KingChair -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 128, x = 0, y = 4, w = 1, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is Chair.CrystalChair -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 131, x = 3, y = 4, w = 1, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is Chair.TropicalChair -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 134, x = 6, y = 4, w = 1, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }


        is Bench.BirchBench -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 202, x = 10, y = 6, w = 2, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is Bench.OakBench -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 192, x = 0, y = 6, w = 2, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is Bench.WalnutBench -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 197, x = 5, y = 6, w = 2, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is Bench.MahoganyBench -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 207, x = 15, y = 6, w = 2, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }

        is Bench.ModernBench -> when (entity.rotation) {
            Rotations4.R1 -> furniture(id = 212, x = 20, y = 6, w = 2, h = 2, r = entity.rotation)
            Rotations4.R2 -> TODO()
            Rotations4.R3 -> TODO()
            Rotations4.R4 -> TODO()
        }


        is WoodenEndTable.OakEndTable -> when (entity.rotation) {
            Rotations2.R1 -> furniture(id = 1391, x = 15, y = 43, w = 1, h = 2, r = entity.rotation)
            Rotations2.R2 -> TODO()
        }

        is WoodenEndTable.BirchEndTable -> when (entity.rotation) {
            Rotations2.R1 -> furniture(id = 1395, x = 19, y = 43, w = 1, h = 2, r = entity.rotation)
            Rotations2.R2 -> TODO()
        }

        is WoodenEndTable.MahoganyEndTable -> when (entity.rotation) {
            Rotations2.R1 -> furniture(id = 1397, x = 21, y = 43, w = 1, h = 2, r = entity.rotation)
            Rotations2.R2 -> TODO()
        }

        is WoodenEndTable.WalnutEndTable -> when (entity.rotation) {
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


        is LongTable.ModernDiningTable -> when (entity.rotation) {
            Rotations2.R1 -> furniture(id = 826, x = 26, y = 25, w = 2, h = 5, r = entity.rotation)
            Rotations2.R2 -> TODO()
        }

        is LongTable.MahoganyDiningTable -> when (entity.rotation) {
            Rotations2.R1 -> furniture(id = 819, x = 19, y = 25, w = 2, h = 5, r = entity.rotation)
            Rotations2.R2 -> TODO()
        }

        is LongTable.FestiveDiningTable -> when (entity.rotation) {
            Rotations2.R1 -> furniture(id = 812, x = 12, y = 25, w = 2, h = 5, r = entity.rotation)
            Rotations2.R2 -> TODO()
        }

        is LongTable.WinterDiningTable -> when (entity.rotation) {
            Rotations2.R1 -> furniture(id = 805, x = 5, y = 25, w = 2, h = 5, r = entity.rotation)
            Rotations2.R2 -> TODO()
        }
    }
}

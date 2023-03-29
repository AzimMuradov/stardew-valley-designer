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

package me.azimmuradov.svc.cartographer.menus.entityselection

import me.azimmuradov.svc.cartographer.menus.EntitySelectionMenu
import me.azimmuradov.svc.engine.entity.*
import me.azimmuradov.svc.utils.menu.menu
import me.azimmuradov.svc.cartographer.menus.EntitySelectionRoot as Root


val FurnitureMenu: EntitySelectionMenu = menu(root = Root.Furniture) {
    submenu(root = Root.FurnitureOutdoor) {
        items(
            Equipment.SimpleEquipment.BasicLog,
            Equipment.SimpleEquipment.LogSection,
            Equipment.SimpleEquipment.OrnamentalHayBale,
            Equipment.SimpleEquipment.SignOfTheVessel,
            Equipment.SimpleEquipment.WickedStatue,
            Equipment.SimpleEquipment.BigGreenCane,
            Equipment.SimpleEquipment.BigRedCane,
            Equipment.SimpleEquipment.GreenCanes,
            Equipment.SimpleEquipment.RedCanes,
            Equipment.SimpleEquipment.MixedCane,
            Equipment.SimpleEquipment.LawnFlamingo,
            Equipment.SimpleEquipment.PlushBunny,
            Equipment.SimpleEquipment.SeasonalDecor,
            Equipment.SimpleEquipment.TubOFlowers,
            Equipment.SimpleEquipment.SeasonalPlant1,
            Equipment.SimpleEquipment.SeasonalPlant2,
            Equipment.SimpleEquipment.SeasonalPlant3,
            Equipment.SimpleEquipment.SeasonalPlant4,
            Equipment.SimpleEquipment.SeasonalPlant5,
            Equipment.SimpleEquipment.SeasonalPlant6,
            Equipment.SimpleEquipment.DrumBlock,
            Equipment.SimpleEquipment.FluteBlock,
            Equipment.SimpleEquipment.GraveStone,
            Equipment.SimpleEquipment.StoneCairn,
            Equipment.SimpleEquipment.StoneFrog,
            Equipment.SimpleEquipment.StoneJunimo,
            Equipment.SimpleEquipment.StoneOwl,
            Equipment.SimpleEquipment.StoneParrot,
            Equipment.SimpleEquipment.SuitOfArmor,
            Equipment.SimpleEquipment.Foroguemon,
            Equipment.SimpleEquipment.HMTGF,
            Equipment.SimpleEquipment.PinkyLemon,
            Equipment.SimpleEquipment.SolidGoldLewis,
            Equipment.SimpleEquipment.StatueOfEndlessFortune,
            Equipment.SimpleEquipment.StatueOfPerfection,
            Equipment.SimpleEquipment.StatueOfTruePerfection,
            Equipment.SimpleEquipment.SodaMachine,
            Equipment.SimpleEquipment.StardewHeroTrophy,
            Equipment.SimpleEquipment.JunimoKartArcadeSystem,
            Equipment.SimpleEquipment.PrairieKingArcadeSystem,
            Equipment.SimpleEquipment.SingingStone,
            Equipment.SimpleEquipment.SecretStoneOwl,
            Equipment.SimpleEquipment.SecretStrangeCapsule,
            Equipment.SimpleEquipment.SecretEmptyCapsule,
        )
    }

    submenu(root = Root.FurnitureUniversal) {
        submenu(root = Root.FurnitureUniversalChairs) {
            items(
                UniversalFurniture.Chair.OakChair(),
                UniversalFurniture.Chair.WalnutChair(),
                UniversalFurniture.Chair.BirchChair(),
                UniversalFurniture.Chair.MahoganyChair(),
                UniversalFurniture.Chair.RedDinerChair(),
                UniversalFurniture.Chair.BlueDinerChair(),
                UniversalFurniture.Chair.CountryChair(),
                UniversalFurniture.Chair.BreakfastChair(),
                UniversalFurniture.Chair.PinkOfficeChair(),
                UniversalFurniture.Chair.PurpleOfficeChair(),
                UniversalFurniture.SimpleUniversalFurniture.GreenOfficeStool,
                UniversalFurniture.SimpleUniversalFurniture.OrangeOfficeStool,
            )

            items(
                UniversalFurniture.Chair.DarkThrone(),
                UniversalFurniture.Chair.DiningChairYellow(),
                UniversalFurniture.Chair.DiningChairRed(),
                UniversalFurniture.Chair.GreenPlushSeat(),
                UniversalFurniture.Chair.PinkPlushSeat(),
                UniversalFurniture.Chair.WinterChair(),
                UniversalFurniture.Chair.GroovyChair(),
                UniversalFurniture.Chair.CuteChair(),
                UniversalFurniture.Chair.StumpSeat(),
                UniversalFurniture.Chair.MetalChair(),
                UniversalFurniture.SimpleUniversalFurniture.GreenStool,
                UniversalFurniture.SimpleUniversalFurniture.BlueStool,
            )

            items(
                UniversalFurniture.Chair.KingChair(),
                UniversalFurniture.Chair.CrystalChair(),
                UniversalFurniture.Chair.TropicalChair(),
            )
        }

        submenu(root = Root.FurnitureUniversalBenches) {
            items(
                UniversalFurniture.Bench.OakBench(),
                UniversalFurniture.Bench.WalnutBench(),
                UniversalFurniture.Bench.BirchBench(),
                UniversalFurniture.Bench.MahoganyBench(),
                UniversalFurniture.Bench.ModernBench(),
            )
        }

        submenu(root = Root.FurnitureUniversalTables) {
            items(
                UniversalFurniture.SimpleUniversalFurniture.OakTable,
                UniversalFurniture.SimpleUniversalFurniture.OakTeaTable,
                UniversalFurniture.WoodenEndTable.OakEndTable(),
                UniversalFurniture.SimpleUniversalFurniture.WalnutTable,
                UniversalFurniture.SimpleUniversalFurniture.WalnutTeaTable,
                UniversalFurniture.WoodenEndTable.WalnutEndTable(),
                UniversalFurniture.SimpleUniversalFurniture.BirchTable,
                UniversalFurniture.SimpleUniversalFurniture.BirchTeaTable,
                UniversalFurniture.WoodenEndTable.BirchEndTable(),
                UniversalFurniture.SimpleUniversalFurniture.MahoganyTable,
                UniversalFurniture.SimpleUniversalFurniture.MahoganyTeaTable,
                UniversalFurniture.WoodenEndTable.MahoganyEndTable(),
                UniversalFurniture.SimpleUniversalFurniture.SunTable,
                UniversalFurniture.SimpleUniversalFurniture.MoonTable,
                UniversalFurniture.SimpleUniversalFurniture.ModernTable,
                UniversalFurniture.SimpleUniversalFurniture.ModernTeaTable,
                UniversalFurniture.SimpleUniversalFurniture.ModernEndTable,
                UniversalFurniture.SimpleUniversalFurniture.PubTable,
                UniversalFurniture.SimpleUniversalFurniture.LuxuryTable,
                UniversalFurniture.SimpleUniversalFurniture.DivinerTable,
                UniversalFurniture.SimpleUniversalFurniture.GrandmotherEndTable,
                UniversalFurniture.SimpleUniversalFurniture.NeolithicTable,
                UniversalFurniture.SimpleUniversalFurniture.PuzzleTable,
                UniversalFurniture.SimpleUniversalFurniture.WinterTable,
                UniversalFurniture.SimpleUniversalFurniture.WinterEndTable,
                UniversalFurniture.SimpleUniversalFurniture.CandyTable,
                UniversalFurniture.SimpleUniversalFurniture.LuauTable,
                UniversalFurniture.SimpleUniversalFurniture.DarkTable,
            )

            items(
                UniversalFurniture.CoffeeTable(),
                UniversalFurniture.StoneSlab(),
            )

            items(
                UniversalFurniture.LongTable.WinterDiningTable(),
                UniversalFurniture.LongTable.FestiveDiningTable(),
                UniversalFurniture.LongTable.MahoganyDiningTable(),
                UniversalFurniture.LongTable.ModernDiningTable(),
            )
        }

        submenu(root = Root.FurnitureUniversalHousePlants) {
            items(
                UniversalFurniture.SimpleUniversalFurniture.HousePlant1,
                UniversalFurniture.SimpleUniversalFurniture.HousePlant2,
                UniversalFurniture.SimpleUniversalFurniture.HousePlant3,
                UniversalFurniture.SimpleUniversalFurniture.HousePlant4,
                UniversalFurniture.SimpleUniversalFurniture.HousePlant5,
                UniversalFurniture.SimpleUniversalFurniture.HousePlant6,
                UniversalFurniture.SimpleUniversalFurniture.HousePlant7,
                UniversalFurniture.SimpleUniversalFurniture.HousePlant8,
                UniversalFurniture.SimpleUniversalFurniture.HousePlant9,
                UniversalFurniture.SimpleUniversalFurniture.HousePlant10,
                UniversalFurniture.SimpleUniversalFurniture.HousePlant11,
                UniversalFurniture.SimpleUniversalFurniture.HousePlant12,
                UniversalFurniture.SimpleUniversalFurniture.HousePlant13,
                UniversalFurniture.SimpleUniversalFurniture.HousePlant14,
                UniversalFurniture.SimpleUniversalFurniture.HousePlant15,
            )
        }

        submenu(root = Root.FurnitureUniversalFreestandingDecorativePlants) {
            items(
                UniversalFurniture.SimpleUniversalFurniture.DriedSunflowers,
                UniversalFurniture.SimpleUniversalFurniture.BonsaiTree,
                UniversalFurniture.SimpleUniversalFurniture.SmallPine,
                UniversalFurniture.SimpleUniversalFurniture.TreeColumn,
                UniversalFurniture.SimpleUniversalFurniture.SmallPlant,
                UniversalFurniture.SimpleUniversalFurniture.TablePlant,
                UniversalFurniture.SimpleUniversalFurniture.DeluxeTree,
                UniversalFurniture.SimpleUniversalFurniture.ExoticTree,
                UniversalFurniture.SimpleUniversalFurniture.IndoorPalm,
                UniversalFurniture.SimpleUniversalFurniture.TopiaryTree,
                UniversalFurniture.SimpleUniversalFurniture.ManicuredPine,
                UniversalFurniture.SimpleUniversalFurniture.TreeOfTheWinterStar,
                UniversalFurniture.SimpleUniversalFurniture.LongCactus,
                UniversalFurniture.SimpleUniversalFurniture.LongPalm,
            )
        }

        submenu(root = Root.FurnitureUniversalTorches) {
            items(
                UniversalFurniture.SimpleUniversalFurniture.JungleTorch,
                UniversalFurniture.SimpleUniversalFurniture.PlainTorch,
                UniversalFurniture.SimpleUniversalFurniture.StumpTorch,
            )
        }

        submenu(root = Root.FurnitureUniversalOtherDecorations) {
            items(
                UniversalFurniture.SimpleUniversalFurniture.CeramicPillar,
                UniversalFurniture.SimpleUniversalFurniture.GoldPillar,
                UniversalFurniture.SimpleUniversalFurniture.TotemPole,
                UniversalFurniture.SimpleUniversalFurniture.DecorativeBowl,
                UniversalFurniture.SimpleUniversalFurniture.DecorativeLantern,
                UniversalFurniture.SimpleUniversalFurniture.Globe,
                UniversalFurniture.SimpleUniversalFurniture.ModelShip,
                UniversalFurniture.SimpleUniversalFurniture.SmallCrystal,
                UniversalFurniture.SimpleUniversalFurniture.FutanBear,
                UniversalFurniture.SimpleUniversalFurniture.DecorativeTrashCan,
                UniversalFurniture.SimpleUniversalFurniture.BearStatue,
                UniversalFurniture.SimpleUniversalFurniture.ChickenStatue,
                UniversalFurniture.SimpleUniversalFurniture.LgFutanBear,
                UniversalFurniture.SimpleUniversalFurniture.ObsidianVase,
                UniversalFurniture.SimpleUniversalFurniture.SkeletonStatue,
                UniversalFurniture.SimpleUniversalFurniture.SlothSkeletonL,
                UniversalFurniture.SimpleUniversalFurniture.SlothSkeletonM,
                UniversalFurniture.SimpleUniversalFurniture.SlothSkeletonR,
                UniversalFurniture.SimpleUniversalFurniture.StandingGeode,
                UniversalFurniture.SimpleUniversalFurniture.ButterflyHutch,
                UniversalFurniture.SimpleUniversalFurniture.LeahSculpture,
                UniversalFurniture.SimpleUniversalFurniture.SamBoombox,
                UniversalFurniture.SimpleUniversalFurniture.FutanRabbit,
                UniversalFurniture.SimpleUniversalFurniture.SmallJunimoPlush1,
                UniversalFurniture.SimpleUniversalFurniture.SmallJunimoPlush2,
                UniversalFurniture.SimpleUniversalFurniture.SmallJunimoPlush3,
                UniversalFurniture.SimpleUniversalFurniture.SmallJunimoPlush4,
                UniversalFurniture.SimpleUniversalFurniture.GreenSerpentStatue,
                UniversalFurniture.SimpleUniversalFurniture.PurpleSerpentStatue,
                UniversalFurniture.SimpleUniversalFurniture.BoboStatue,
                UniversalFurniture.SimpleUniversalFurniture.WumbusStatue,
                UniversalFurniture.SimpleUniversalFurniture.JunimoPlush,
                UniversalFurniture.SimpleUniversalFurniture.GourmandStatue,
                UniversalFurniture.SimpleUniversalFurniture.IridiumKrobus,
                UniversalFurniture.SimpleUniversalFurniture.SquirrelFigurine,
            )
        }

        submenu(root = Root.FurnitureUniversalCatalogues) {
            items(
                UniversalFurniture.SimpleUniversalFurniture.Catalogue,
                UniversalFurniture.SimpleUniversalFurniture.FurnitureCatalogue,
            )
        }
    }

    submenu(root = Root.FurnitureIndoor) {
        submenu(root = Root.FurnitureIndoorBookcases) {
            items(
                IndoorFurniture.SimpleIndoorFurniture.ArtistBookcase,
                IndoorFurniture.SimpleIndoorFurniture.ModernBookcase,
                IndoorFurniture.SimpleIndoorFurniture.LuxuryBookcase,
                IndoorFurniture.SimpleIndoorFurniture.DarkBookcase,
            )
        }

        submenu(root = Root.FurnitureIndoorFireplaces) {
            items(
                IndoorFurniture.SimpleIndoorFurniture.BrickFireplace,
                IndoorFurniture.SimpleIndoorFurniture.ElegantFireplace,
                IndoorFurniture.SimpleIndoorFurniture.IridiumFireplace,
                IndoorFurniture.SimpleIndoorFurniture.MonsterFireplace,
                IndoorFurniture.SimpleIndoorFurniture.StoneFireplace,
                IndoorFurniture.SimpleIndoorFurniture.StoveFireplace,
            )
        }

        submenu(root = Root.FurnitureIndoorLamps) {
            items(
                IndoorFurniture.SimpleIndoorFurniture.CountryLamp,
                IndoorFurniture.SimpleIndoorFurniture.ModernLamp,
                IndoorFurniture.SimpleIndoorFurniture.ClassicLamp,
                IndoorFurniture.SimpleIndoorFurniture.BoxLamp,
                IndoorFurniture.SimpleIndoorFurniture.CandleLamp,
                IndoorFurniture.SimpleIndoorFurniture.OrnateLamp,
            )
        }

        submenu(root = Root.FurnitureIndoorTVs) {
            items(
                IndoorFurniture.SimpleIndoorFurniture.FloorTV,
                IndoorFurniture.SimpleIndoorFurniture.BudgetTV,
                IndoorFurniture.SimpleIndoorFurniture.PlasmaTV,
                IndoorFurniture.SimpleIndoorFurniture.TropicalTV,
            )
        }

        submenu(root = Root.FurnitureIndoorFishTanks) {
            items(
                IndoorFurniture.SimpleIndoorFurniture.SmallFishTank,
                IndoorFurniture.SimpleIndoorFurniture.ModernFishTank,
                IndoorFurniture.SimpleIndoorFurniture.LargeFishTank,
                IndoorFurniture.SimpleIndoorFurniture.DeluxeFishTank,
                IndoorFurniture.SimpleIndoorFurniture.AquaticSanctuary,
            )
        }

        items(
            IndoorFurniture.SimpleIndoorFurniture.ChinaCabinet,
        )

        submenu(root = Root.FurnitureIndoorCouches) {
            items(
                IndoorFurniture.Couch.BlueCouch(),
                IndoorFurniture.Couch.BrownCouch(),
                IndoorFurniture.Couch.GreenCouch(),
                IndoorFurniture.Couch.RedCouch(),
                IndoorFurniture.Couch.YellowCouch(),
                IndoorFurniture.Couch.DarkCouch(),
                IndoorFurniture.Couch.WoodsyCouch(),
                IndoorFurniture.Couch.WizardCouch(),
                IndoorFurniture.LargeBrownCouch(),
            )
        }

        submenu(root = Root.FurnitureIndoorArmchairs) {
            items(
                IndoorFurniture.Armchair.BlueArmchair(),
                IndoorFurniture.Armchair.BrownArmchair(),
                IndoorFurniture.Armchair.GreenArmchair(),
                IndoorFurniture.Armchair.RedArmchair(),
                IndoorFurniture.Armchair.YellowArmchair(),
            )
        }

        submenu(root = Root.FurnitureIndoorDressers) {
            items(
                IndoorFurniture.Dresser.BirchDresser(),
                IndoorFurniture.Dresser.OakDresser(),
                IndoorFurniture.Dresser.WalnutDresser(),
                IndoorFurniture.Dresser.MahoganyDresser(),
            )
        }
    }

    submenu(root = Root.FurnitureHouse) {
        // TODO
    }

    submenu(root = Root.FurnitureFloor) {
        items(
            FloorFurniture.RotatableRug.BambooMat(),
            FloorFurniture.SimpleRug.BurlapRug,
            FloorFurniture.SimpleRug.WoodcutRug,
            FloorFurniture.RotatableRug.NauticalRug(),
            FloorFurniture.RotatableRug.DarkRug(),
            FloorFurniture.RotatableRug.RedRug(),
            FloorFurniture.SimpleRug.LargeRedRug,
            FloorFurniture.SimpleRug.MonsterRug,
            FloorFurniture.RotatableRug.LightGreenRug(),
            FloorFurniture.SimpleRug.BlossomRug,
            FloorFurniture.SimpleRug.LargeGreenRug,
            FloorFurniture.SimpleRug.OldWorldRug,
            FloorFurniture.SimpleRug.LargeCottageRug,
            FloorFurniture.RotatableRug.GreenCottageRug(),
            FloorFurniture.RotatableRug.RedCottageRug(),
            FloorFurniture.RotatableRug.MysticRug(),
            FloorFurniture.RotatableRug.BoneRug(),
            FloorFurniture.RotatableRug.SnowyRug(),
            FloorFurniture.RotatableRug.PirateRug(),
            FloorFurniture.RotatableRug.PatchworkRug(),
            FloorFurniture.RotatableRug.FruitSaladRug(),
            FloorFurniture.SimpleRug.OceanicRug,
            FloorFurniture.SimpleRug.IcyRug,
            FloorFurniture.SimpleRug.FunkyRug,
            FloorFurniture.SimpleRug.ModernRug,
        )
    }
}

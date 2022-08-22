/*
 * Copyright 2021-2022 Azim Muradov
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

package me.azimmuradov.svc.components.screens.cartographer.res

import me.azimmuradov.svc.engine.entity.*


object EntitySpritesProvider {

    fun spriteBy(entity: Entity<*>): Sprite = sprites[entity] ?: when (entity) {

        // Floor

        Floor.WoodFloor -> common(328)
        Floor.RusticPlankFloor -> common(840)
        Floor.StrawFloor -> common(401)
        Floor.WeatheredFloor -> common(331)
        Floor.CrystalFloor -> common(333)
        Floor.StoneFloor -> common(329)
        Floor.StoneWalkwayFloor -> common(841)
        Floor.BrickFloor -> common(293)

        Floor.WoodPath -> common(405)
        Floor.GravelPath -> common(407)
        Floor.CobblestonePath -> common(411)
        Floor.SteppingStonePath -> common(415)
        Floor.CrystalPath -> common(409)

        Floor.Grass -> common(297)


        // Floor Furniture

        is FloorFurniture -> TODO()


        // Equipment

        // Common Equipment
        // (Artisan Equipment + Other Tools + Refining Equipment + Misc + Storage Equipment)

        Equipment.SimpleEquipment.MayonnaiseMachine -> craftable(24)
        Equipment.SimpleEquipment.BeeHouse -> craftable(11)
        Equipment.SimpleEquipment.PreservesJar -> craftable(15)
        Equipment.SimpleEquipment.CheesePress -> craftable(16)
        Equipment.SimpleEquipment.Loom -> craftable(18)
        Equipment.SimpleEquipment.Keg -> craftable(12)
        Equipment.SimpleEquipment.OilMaker -> craftable(19)
        Equipment.SimpleEquipment.Cask -> craftable(163)

        Equipment.SimpleEquipment.GardenPot -> craftable(62)
        Equipment.SimpleEquipment.Heater -> craftable(104)
        Equipment.SimpleEquipment.AutoGrabber -> craftable(166)
        Equipment.SimpleEquipment.AutoPetter -> craftable(272)

        Equipment.SimpleEquipment.CharcoalKiln -> craftable(114)
        Equipment.SimpleEquipment.Crystalarium -> craftable(21)
        Equipment.SimpleEquipment.Furnace -> craftable(14)
        Equipment.SimpleEquipment.LightningRod -> craftable(9)
        Equipment.SimpleEquipment.SolarPanel -> craftable(231)
        Equipment.SimpleEquipment.RecyclingMachine -> craftable(20)
        Equipment.SimpleEquipment.SeedMaker -> craftable(25)
        Equipment.SimpleEquipment.SlimeIncubator -> craftable(156)
        Equipment.SimpleEquipment.OstrichIncubator -> craftable(254)
        Equipment.SimpleEquipment.SlimeEggPress -> craftable(158)
        Equipment.SimpleEquipment.WormBin -> craftable(154)
        Equipment.SimpleEquipment.BoneMill -> craftable(90)
        Equipment.SimpleEquipment.GeodeCrusher -> craftable(182)
        Equipment.SimpleEquipment.WoodChipper -> craftable(211)

        Equipment.SimpleEquipment.MiniJukebox -> craftable(209)
        Equipment.SimpleEquipment.MiniObelisk -> craftable(238)
        Equipment.SimpleEquipment.FarmComputer -> craftable(239)
        Equipment.SimpleEquipment.Hopper -> craftable(275)
        Equipment.SimpleEquipment.Deconstructor -> craftable(265)
        Equipment.SimpleEquipment.CoffeeMaker -> craftable(246)
        Equipment.SimpleEquipment.Telephone -> craftable(214)
        Equipment.SimpleEquipment.SewingMachine -> craftable(247)
        Equipment.SimpleEquipment.Workbench -> craftable(208)
        Equipment.SimpleEquipment.MiniShippingBin -> craftable(248)

        // TODO (?) : CrabPot,
        // TODO (?) : Tapper,
        // TODO (?) : HeavyTapper,

        is Equipment.Chest -> when (entity.color) {
            ColoredFlavor.Colors.ChestColors.Default -> craftable(130)
            ColoredFlavor.Colors.ChestColors.Blue -> TODO()
            ColoredFlavor.Colors.ChestColors.LightBlue -> TODO()
            ColoredFlavor.Colors.ChestColors.Teal -> TODO()
            ColoredFlavor.Colors.ChestColors.Aqua -> TODO()
            ColoredFlavor.Colors.ChestColors.Green -> TODO()
            ColoredFlavor.Colors.ChestColors.LimeGreen -> TODO()
            ColoredFlavor.Colors.ChestColors.Yellow -> TODO()
            ColoredFlavor.Colors.ChestColors.LightOrange -> TODO()
            ColoredFlavor.Colors.ChestColors.Orange -> TODO()
            ColoredFlavor.Colors.ChestColors.Red -> TODO()
            ColoredFlavor.Colors.ChestColors.DarkRed -> TODO()
            ColoredFlavor.Colors.ChestColors.LightPink -> TODO()
            ColoredFlavor.Colors.ChestColors.Pink -> TODO()
            ColoredFlavor.Colors.ChestColors.Magenta -> TODO()
            ColoredFlavor.Colors.ChestColors.Purple -> TODO()
            ColoredFlavor.Colors.ChestColors.DarkPurple -> TODO()
            ColoredFlavor.Colors.ChestColors.DarkGrey -> TODO()
            ColoredFlavor.Colors.ChestColors.MediumGrey -> TODO()
            ColoredFlavor.Colors.ChestColors.LightGrey -> TODO()
            ColoredFlavor.Colors.ChestColors.White -> TODO()
        }

        is Equipment.StoneChest -> when (entity.color) {
            ColoredFlavor.Colors.ChestColors.Default -> craftable(232)
            ColoredFlavor.Colors.ChestColors.Blue -> TODO()
            ColoredFlavor.Colors.ChestColors.LightBlue -> TODO()
            ColoredFlavor.Colors.ChestColors.Teal -> TODO()
            ColoredFlavor.Colors.ChestColors.Aqua -> TODO()
            ColoredFlavor.Colors.ChestColors.Green -> TODO()
            ColoredFlavor.Colors.ChestColors.LimeGreen -> TODO()
            ColoredFlavor.Colors.ChestColors.Yellow -> TODO()
            ColoredFlavor.Colors.ChestColors.LightOrange -> TODO()
            ColoredFlavor.Colors.ChestColors.Orange -> TODO()
            ColoredFlavor.Colors.ChestColors.Red -> TODO()
            ColoredFlavor.Colors.ChestColors.DarkRed -> TODO()
            ColoredFlavor.Colors.ChestColors.LightPink -> TODO()
            ColoredFlavor.Colors.ChestColors.Pink -> TODO()
            ColoredFlavor.Colors.ChestColors.Magenta -> TODO()
            ColoredFlavor.Colors.ChestColors.Purple -> TODO()
            ColoredFlavor.Colors.ChestColors.DarkPurple -> TODO()
            ColoredFlavor.Colors.ChestColors.DarkGrey -> TODO()
            ColoredFlavor.Colors.ChestColors.MediumGrey -> TODO()
            ColoredFlavor.Colors.ChestColors.LightGrey -> TODO()
            ColoredFlavor.Colors.ChestColors.White -> TODO()
        }

        Equipment.SimpleEquipment.JunimoChest -> craftable(256)

        // Farm Elements (Scarecrows + Sprinklers)

        Equipment.SimpleEquipment.Scarecrow -> craftable(8)
        Equipment.SimpleEquipment.DeluxeScarecrow -> craftable(167)
        Equipment.SimpleEquipment.Rarecrow1 -> craftable(110)
        Equipment.SimpleEquipment.Rarecrow2 -> craftable(113)
        Equipment.SimpleEquipment.Rarecrow3 -> craftable(126)
        Equipment.SimpleEquipment.Rarecrow4 -> craftable(136)
        Equipment.SimpleEquipment.Rarecrow5 -> craftable(137)
        Equipment.SimpleEquipment.Rarecrow6 -> craftable(138)
        Equipment.SimpleEquipment.Rarecrow7 -> craftable(139)
        Equipment.SimpleEquipment.Rarecrow8 -> craftable(140)

        Equipment.SimpleEquipment.Sprinkler -> common(599)
        Equipment.SimpleEquipment.QualitySprinkler -> common(621)
        Equipment.SimpleEquipment.IridiumSprinkler -> common(645)
        Equipment.SimpleEquipment.IridiumSprinklerWithPressureNozzle -> common(916)

        // Terrain Elements (Fences + Signs + Lighting)

        Equipment.SimpleEquipment.Gate -> common(325)
        Equipment.SimpleEquipment.WoodFence -> common(322)
        Equipment.SimpleEquipment.StoneFence -> common(323)
        Equipment.SimpleEquipment.IronFence -> common(324)
        Equipment.SimpleEquipment.HardwoodFence -> common(298)

        Equipment.SimpleEquipment.WoodSign -> craftable(37)
        Equipment.SimpleEquipment.StoneSign -> craftable(38)
        Equipment.SimpleEquipment.DarkSign -> craftable(39)

        Equipment.SimpleEquipment.Torch -> common(93)
        Equipment.SimpleEquipment.Campfire -> craftable(146)
        Equipment.SimpleEquipment.WoodenBrazier -> craftable(143)
        Equipment.SimpleEquipment.StoneBrazier -> craftable(144)
        Equipment.SimpleEquipment.GoldBrazier -> craftable(145)
        Equipment.SimpleEquipment.CarvedBrazier -> craftable(148)
        Equipment.SimpleEquipment.StumpBrazier -> craftable(147)
        Equipment.SimpleEquipment.BarrelBrazier -> craftable(150)
        Equipment.SimpleEquipment.SkullBrazier -> craftable(149)
        Equipment.SimpleEquipment.MarbleBrazier -> craftable(151)
        Equipment.SimpleEquipment.WoodLampPost -> craftable(152)
        Equipment.SimpleEquipment.IronLampPost -> craftable(153)
        Equipment.SimpleEquipment.JackOLantern -> common(746)

        // Furniture

        Equipment.SimpleEquipment.BasicLog -> craftable(35)
        Equipment.SimpleEquipment.LogSection -> craftable(46)
        Equipment.SimpleEquipment.OrnamentalHayBale -> craftable(45)
        Equipment.SimpleEquipment.SignOfTheVessel -> craftable(34)
        Equipment.SimpleEquipment.WickedStatue -> craftable(83)
        Equipment.SimpleEquipment.BigGreenCane -> craftable(40)
        Equipment.SimpleEquipment.BigRedCane -> craftable(44)
        Equipment.SimpleEquipment.GreenCanes -> craftable(41)
        Equipment.SimpleEquipment.RedCanes -> craftable(43)
        Equipment.SimpleEquipment.MixedCane -> craftable(42)
        Equipment.SimpleEquipment.LawnFlamingo -> craftable(36)
        Equipment.SimpleEquipment.PlushBunny -> craftable(107)
        Equipment.SimpleEquipment.SeasonalDecor -> craftable(48)
        Equipment.SimpleEquipment.TubOFlowers -> craftable(108)
        Equipment.SimpleEquipment.SeasonalPlant1 -> craftable(184)
        Equipment.SimpleEquipment.SeasonalPlant2 -> craftable(188)
        Equipment.SimpleEquipment.SeasonalPlant3 -> craftable(192)
        Equipment.SimpleEquipment.SeasonalPlant4 -> craftable(196)
        Equipment.SimpleEquipment.SeasonalPlant5 -> craftable(200)
        Equipment.SimpleEquipment.SeasonalPlant6 -> craftable(204)
        Equipment.SimpleEquipment.DrumBlock -> common(463)
        Equipment.SimpleEquipment.FluteBlock -> common(464)
        Equipment.SimpleEquipment.GraveStone -> craftable(47)
        Equipment.SimpleEquipment.StoneCairn -> craftable(32)
        Equipment.SimpleEquipment.StoneFrog -> craftable(52)
        Equipment.SimpleEquipment.StoneJunimo -> craftable(55)
        Equipment.SimpleEquipment.StoneOwl -> craftable(54)
        Equipment.SimpleEquipment.StoneParrot -> craftable(53)
        Equipment.SimpleEquipment.SuitOfArmor -> craftable(33)
        Equipment.SimpleEquipment.Foroguemon -> craftable(162)
        Equipment.SimpleEquipment.HMTGF -> craftable(155)
        Equipment.SimpleEquipment.PinkyLemon -> craftable(161)
        Equipment.SimpleEquipment.SolidGoldLewis -> craftable(164)
        Equipment.SimpleEquipment.StatueOfEndlessFortune -> craftable(127)
        Equipment.SimpleEquipment.StatueOfPerfection -> craftable(160)
        Equipment.SimpleEquipment.StatueOfTruePerfection -> craftable(280)
        Equipment.SimpleEquipment.SodaMachine -> craftable(117)
        Equipment.SimpleEquipment.StardewHeroTrophy -> craftable(116)
        Equipment.SimpleEquipment.JunimoKartArcadeSystem -> craftable(159)
        Equipment.SimpleEquipment.PrairieKingArcadeSystem -> craftable(141)
        Equipment.SimpleEquipment.SingingStone -> craftable(94)
        Equipment.SimpleEquipment.SecretStoneOwl -> craftable(95)
        Equipment.SimpleEquipment.SecretStrangeCapsule -> craftable(96)
        Equipment.SimpleEquipment.SecretEmptyCapsule -> craftable(98)


        // House Furniture

        is HouseFurniture -> TODO()


        // Indoor Furniture

        is IndoorFurniture -> TODO()


        // Universal Furniture

        is UniversalFurniture -> TODO()


        // Buildings

        // Barns

        Building.SimpleBuilding.Barn1 -> TODO()
        Building.SimpleBuilding.Barn2 -> TODO()
        is Building.Barn3 -> TODO()

        // Coops

        Building.SimpleBuilding.Coop1 -> TODO()
        Building.SimpleBuilding.Coop2 -> TODO()
        is Building.Coop3 -> TODO()

        // Sheds

        Building.SimpleBuilding.Shed1 -> TODO()
        is Building.Shed2 -> TODO()

        // Cabins

        Building.SimpleBuilding.StoneCabin1 -> TODO()
        Building.SimpleBuilding.StoneCabin2 -> TODO()
        is Building.StoneCabin3 -> TODO()

        Building.SimpleBuilding.PlankCabin1 -> TODO()
        Building.SimpleBuilding.PlankCabin2 -> TODO()
        is Building.PlankCabin3 -> TODO()

        Building.SimpleBuilding.LogCabin1 -> TODO()
        Building.SimpleBuilding.LogCabin2 -> TODO()
        is Building.LogCabin3 -> TODO()

        // Magical Buildings

        Building.SimpleBuilding.EarthObelisk -> TODO()
        Building.SimpleBuilding.WaterObelisk -> TODO()
        Building.SimpleBuilding.DesertObelisk -> TODO()
        Building.SimpleBuilding.IslandObelisk -> TODO()
        Building.SimpleBuilding.JunimoHut -> TODO()
        Building.SimpleBuilding.GoldClock -> TODO()

        // Others

        Building.SimpleBuilding.Mill -> TODO()
        Building.SimpleBuilding.Silo -> TODO()
        Building.SimpleBuilding.Well -> TODO()
        is Building.Stable -> TODO()
        is Building.FishPond -> TODO()
        Building.SimpleBuilding.SlimeHutch -> TODO()
        Building.SimpleBuilding.ShippingBin -> TODO()
    }.also { sprites[entity] = it }


    private val sprites: MutableMap<Entity<*>, Sprite> = mutableMapOf()
}
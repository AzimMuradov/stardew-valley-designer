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

import me.azimmuradov.svc.engine.entity.Colors.ChestColors.*
import me.azimmuradov.svc.engine.entity.Equipment
import me.azimmuradov.svc.engine.entity.Equipment.*
import me.azimmuradov.svc.metadata.EntityMetadata


internal fun equipment(entity: Equipment): EntityMetadata = when (entity) {

    // Common Equipment
    // (Artisan Equipment + Other Tools + Refining Equipment + Misc + Storage Equipment)

    SimpleEquipment.MayonnaiseMachine -> craftable(24)
    SimpleEquipment.BeeHouse -> craftable(11)
    SimpleEquipment.PreservesJar -> craftable(15)
    SimpleEquipment.CheesePress -> craftable(16)
    SimpleEquipment.Loom -> craftable(18)
    SimpleEquipment.Keg -> craftable(12)
    SimpleEquipment.OilMaker -> craftable(19)
    SimpleEquipment.Cask -> craftable(163)

    SimpleEquipment.GardenPot -> craftable(62)
    SimpleEquipment.Heater -> craftable(104)
    SimpleEquipment.AutoGrabber -> craftable(166)
    SimpleEquipment.AutoPetter -> craftable(272)

    SimpleEquipment.CharcoalKiln -> craftable(114)
    SimpleEquipment.Crystalarium -> craftable(21)
    SimpleEquipment.Furnace -> craftable(14)
    SimpleEquipment.LightningRod -> craftable(9)
    SimpleEquipment.SolarPanel -> craftable(231)
    SimpleEquipment.RecyclingMachine -> craftable(20)
    SimpleEquipment.SeedMaker -> craftable(25)
    SimpleEquipment.SlimeIncubator -> craftable(156)
    SimpleEquipment.OstrichIncubator -> craftable(254)
    SimpleEquipment.SlimeEggPress -> craftable(158)
    SimpleEquipment.WormBin -> craftable(154)
    SimpleEquipment.BoneMill -> craftable(90)
    SimpleEquipment.GeodeCrusher -> craftable(182)
    SimpleEquipment.WoodChipper -> craftable(211)

    SimpleEquipment.MiniJukebox -> craftable(209)
    SimpleEquipment.MiniObelisk -> craftable(238)
    SimpleEquipment.FarmComputer -> craftable(239)
    SimpleEquipment.Hopper -> craftable(275)
    SimpleEquipment.Deconstructor -> craftable(265)
    SimpleEquipment.CoffeeMaker -> craftable(246)
    SimpleEquipment.Telephone -> craftable(214)
    SimpleEquipment.SewingMachine -> craftable(247)
    SimpleEquipment.Workbench -> craftable(208)
    SimpleEquipment.MiniShippingBin -> craftable(248)

    // TODO (?) : CrabPot,
    // TODO (?) : Tapper,
    // TODO (?) : HeavyTapper,

    is Chest -> when (entity.color) {
        Default -> craftable(130)
        Blue -> TODO()
        LightBlue -> TODO()
        Teal -> TODO()
        Aqua -> TODO()
        Green -> TODO()
        LimeGreen -> TODO()
        Yellow -> TODO()
        LightOrange -> TODO()
        Orange -> TODO()
        Red -> TODO()
        DarkRed -> TODO()
        LightPink -> TODO()
        Pink -> TODO()
        Magenta -> TODO()
        Purple -> TODO()
        DarkPurple -> TODO()
        DarkGrey -> TODO()
        MediumGrey -> TODO()
        LightGrey -> TODO()
        White -> TODO()
    }

    is StoneChest -> when (entity.color) {
        Default -> craftable(232)
        Blue -> TODO()
        LightBlue -> TODO()
        Teal -> TODO()
        Aqua -> TODO()
        Green -> TODO()
        LimeGreen -> TODO()
        Yellow -> TODO()
        LightOrange -> TODO()
        Orange -> TODO()
        Red -> TODO()
        DarkRed -> TODO()
        LightPink -> TODO()
        Pink -> TODO()
        Magenta -> TODO()
        Purple -> TODO()
        DarkPurple -> TODO()
        DarkGrey -> TODO()
        MediumGrey -> TODO()
        LightGrey -> TODO()
        White -> TODO()
    }

    SimpleEquipment.JunimoChest -> craftable(256)


    // Farm Elements (Scarecrows + Sprinklers)

    SimpleEquipment.Scarecrow -> craftable(8)
    SimpleEquipment.DeluxeScarecrow -> craftable(167)
    SimpleEquipment.Rarecrow1 -> craftable(110)
    SimpleEquipment.Rarecrow2 -> craftable(113)
    SimpleEquipment.Rarecrow3 -> craftable(126)
    SimpleEquipment.Rarecrow4 -> craftable(136)
    SimpleEquipment.Rarecrow5 -> craftable(137)
    SimpleEquipment.Rarecrow6 -> craftable(138)
    SimpleEquipment.Rarecrow7 -> craftable(139)
    SimpleEquipment.Rarecrow8 -> craftable(140)

    SimpleEquipment.Sprinkler -> common(599)
    SimpleEquipment.QualitySprinkler -> common(621)
    SimpleEquipment.IridiumSprinkler -> common(645)
    // TODO : Equipment.SimpleEquipment.IridiumSprinklerWithPressureNozzle -> common(916)


    // Terrain Elements (Fences + Signs + Lighting)

    SimpleEquipment.Gate -> common(325)
    SimpleEquipment.WoodFence -> common(322)
    SimpleEquipment.StoneFence -> common(323)
    SimpleEquipment.IronFence -> common(324)
    SimpleEquipment.HardwoodFence -> common(298)

    SimpleEquipment.WoodSign -> craftable(37)
    SimpleEquipment.StoneSign -> craftable(38)
    SimpleEquipment.DarkSign -> craftable(39)

    SimpleEquipment.Torch -> common(93)
    SimpleEquipment.Campfire -> craftable(146)
    SimpleEquipment.WoodenBrazier -> craftable(143)
    SimpleEquipment.StoneBrazier -> craftable(144)
    SimpleEquipment.GoldBrazier -> craftable(145)
    SimpleEquipment.CarvedBrazier -> craftable(148)
    SimpleEquipment.StumpBrazier -> craftable(147)
    SimpleEquipment.BarrelBrazier -> craftable(150)
    SimpleEquipment.SkullBrazier -> craftable(149)
    SimpleEquipment.MarbleBrazier -> craftable(151)
    SimpleEquipment.WoodLampPost -> craftable(152)
    SimpleEquipment.IronLampPost -> craftable(153)
    SimpleEquipment.JackOLantern -> common(746)


    // Furniture

    SimpleEquipment.BasicLog -> craftable(35)
    SimpleEquipment.LogSection -> craftable(46)
    SimpleEquipment.OrnamentalHayBale -> craftable(45)
    SimpleEquipment.SignOfTheVessel -> craftable(34)
    SimpleEquipment.WickedStatue -> craftable(83)
    SimpleEquipment.BigGreenCane -> craftable(40)
    SimpleEquipment.BigRedCane -> craftable(44)
    SimpleEquipment.GreenCanes -> craftable(41)
    SimpleEquipment.RedCanes -> craftable(43)
    SimpleEquipment.MixedCane -> craftable(42)
    SimpleEquipment.LawnFlamingo -> craftable(36)
    SimpleEquipment.PlushBunny -> craftable(107)
    SimpleEquipment.SeasonalDecor -> craftable(48)
    SimpleEquipment.TubOFlowers -> craftable(108)
    SimpleEquipment.SeasonalPlant1 -> craftable(184)
    SimpleEquipment.SeasonalPlant2 -> craftable(188)
    SimpleEquipment.SeasonalPlant3 -> craftable(192)
    SimpleEquipment.SeasonalPlant4 -> craftable(196)
    SimpleEquipment.SeasonalPlant5 -> craftable(200)
    SimpleEquipment.SeasonalPlant6 -> craftable(204)
    SimpleEquipment.DrumBlock -> common(463)
    SimpleEquipment.FluteBlock -> common(464)
    SimpleEquipment.GraveStone -> craftable(47)
    SimpleEquipment.StoneCairn -> craftable(32)
    SimpleEquipment.StoneFrog -> craftable(52)
    SimpleEquipment.StoneJunimo -> craftable(55)
    SimpleEquipment.StoneOwl -> craftable(54)
    SimpleEquipment.StoneParrot -> craftable(53)
    SimpleEquipment.SuitOfArmor -> craftable(33)
    SimpleEquipment.Foroguemon -> craftable(162)
    SimpleEquipment.HMTGF -> craftable(155)
    SimpleEquipment.PinkyLemon -> craftable(161)
    SimpleEquipment.SolidGoldLewis -> craftable(164)
    SimpleEquipment.StatueOfEndlessFortune -> craftable(127)
    SimpleEquipment.StatueOfPerfection -> craftable(160)
    SimpleEquipment.StatueOfTruePerfection -> craftable(280)
    SimpleEquipment.SodaMachine -> craftable(117)
    SimpleEquipment.StardewHeroTrophy -> craftable(116)
    SimpleEquipment.JunimoKartArcadeSystem -> craftable(159)
    SimpleEquipment.PrairieKingArcadeSystem -> craftable(141)
    SimpleEquipment.SingingStone -> craftable(94)
    SimpleEquipment.SecretStoneOwl -> craftable(95)
    SimpleEquipment.SecretStrangeCapsule -> craftable(96)
    SimpleEquipment.SecretEmptyCapsule -> craftable(98)
}

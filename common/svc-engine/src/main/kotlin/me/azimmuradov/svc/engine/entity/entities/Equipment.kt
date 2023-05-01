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

import me.azimmuradov.svc.engine.entity.Colored.ColoredChest
import me.azimmuradov.svc.engine.entity.Colors.ChestColors
import me.azimmuradov.svc.engine.entity.Colors.ChestColors.Default
import me.azimmuradov.svc.engine.entity.ObjectType.EquipmentType
import me.azimmuradov.svc.engine.entity.RectsProvider.rectOf
import me.azimmuradov.svc.engine.geometry.Rect


sealed interface Equipment : Entity<EquipmentType> {

    enum class SimpleEquipment : Equipment {

        // Common Equipment
        // (Artisan Equipment + Other Tools + Refining Equipment + Misc + Storage Equipment)

        MayonnaiseMachine,
        BeeHouse,
        PreservesJar,
        CheesePress,
        Loom,
        Keg,
        OilMaker,
        Cask,

        GardenPot,
        Heater,
        AutoGrabber,
        AutoPetter,

        CharcoalKiln,
        Crystalarium,
        Furnace,
        LightningRod,
        SolarPanel,
        RecyclingMachine,
        SeedMaker,
        SlimeIncubator,
        OstrichIncubator,
        SlimeEggPress,
        WormBin,
        BoneMill,
        GeodeCrusher,
        WoodChipper,

        MiniJukebox,
        MiniObelisk,
        FarmComputer,
        Hopper,
        Deconstructor,
        CoffeeMaker,
        Telephone,
        SewingMachine,
        Workbench,
        MiniShippingBin,

        // TODO (?) : CrabPot,
        // TODO (?) : Tapper,
        // TODO (?) : HeavyTapper,

        JunimoChest,


        // Farm Elements (Scarecrows + Sprinklers)

        Scarecrow,
        DeluxeScarecrow,
        Rarecrow1,
        Rarecrow2,
        Rarecrow3,
        Rarecrow4,
        Rarecrow5,
        Rarecrow6,
        Rarecrow7,
        Rarecrow8,

        Sprinkler,
        QualitySprinkler,
        IridiumSprinkler,
        // TODO : IridiumSprinklerWithPressureNozzle,


        // Terrain Elements (Fences + Signs + Lighting)

        Gate,
        WoodFence,
        StoneFence,
        IronFence,
        HardwoodFence,

        WoodSign,
        StoneSign,
        DarkSign,

        Torch,
        Campfire,
        WoodenBrazier,
        StoneBrazier,
        GoldBrazier,
        CarvedBrazier,
        StumpBrazier,
        BarrelBrazier,
        SkullBrazier,
        MarbleBrazier,
        WoodLampPost,
        IronLampPost,
        JackOLantern,


        // Furniture

        BasicLog,
        LogSection,
        OrnamentalHayBale,
        SignOfTheVessel,
        WickedStatue,
        BigGreenCane,
        BigRedCane,
        GreenCanes,
        RedCanes,
        MixedCane,
        LawnFlamingo,
        PlushBunny,
        SeasonalDecor,
        TubOFlowers,
        SeasonalPlant1,
        SeasonalPlant2,
        SeasonalPlant3,
        SeasonalPlant4,
        SeasonalPlant5,
        SeasonalPlant6,
        DrumBlock,
        FluteBlock,
        GraveStone,
        StoneCairn,
        StoneFrog,
        StoneJunimo,
        StoneOwl,
        StoneParrot,
        SuitOfArmor,
        Foroguemon,
        HMTGF,
        PinkyLemon,
        SolidGoldLewis,
        StatueOfEndlessFortune,
        StatueOfPerfection,
        StatueOfTruePerfection,
        SodaMachine,
        StardewHeroTrophy,
        JunimoKartArcadeSystem,
        PrairieKingArcadeSystem,
        SingingStone,
        SecretStoneOwl,
        SecretStrangeCapsule,
        SecretEmptyCapsule,
    }

    data class Chest(override var color: ChestColors = Default) : Equipment, ColoredChest()

    data class StoneChest(override var color: ChestColors = Default) : Equipment, ColoredChest()


    override val type: EquipmentType get() = EquipmentType

    override val size: Rect get() = rectOf(w = 1, h = 1)


    companion object {

        val all by lazy {
            SimpleEquipment.values().toSet() + listOf(Chest(), StoneChest())
            // ChestColors.values().mapTo(mutableSetOf(), Equipment::Chest) +
            // ChestColors.values().mapTo(mutableSetOf(), Equipment::StoneChest)
        }
    }
}

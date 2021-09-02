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
import me.azimmuradov.svc.engine.impl.entity.ObjectType.EquipmentType
import me.azimmuradov.svc.engine.impl.entity.ids.ColoredFlavor.ColoredChestFlavor
import me.azimmuradov.svc.engine.impl.entity.ids.ColoredFlavor.Colors.ChestColors
import me.azimmuradov.svc.engine.impl.entity.ids.ColoredFlavor.Colors.ChestColors.Default


sealed interface Equipment : CartographerEntityId<EquipmentType> {

    enum class SimpleEquipment : Equipment {

        // Fences

        Gate,
        WoodFence,
        StoneFence,
        IronFence,
        HardwoodFence,


        // Sprinklers

        Sprinkler,
        QualitySprinkler,
        IridiumSprinkler,
        IridiumSprinklerWithPressureNozzle,


        // Artisan Equipment

        MayonnaiseMachine,
        BeeHouse,
        PreservesJar,
        CheesePress,
        Loom,
        Keg,
        OilMaker,
        Cask,


        // TODO (?) : CrabPot,


        // Lighting

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


        // Refining Equipment

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


        // TODO (?) : Tapper,
        // TODO (?) : HeavyTapper,


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


        // Storage Equipment (without colored chests and mini-fridge)

        JunimoChest,


        // Signs

        WoodSign,
        StoneSign,
        DarkSign,


        // Misc

        GardenPot,
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


        // Other Tools

        Heater,
        AutoGrabber,
        AutoPetter,
    }

    data class Chest(override var color: ChestColors = Default) : Equipment, ColoredChestFlavor()

    data class StoneChest(override var color: ChestColors = Default) : Equipment, ColoredChestFlavor()


    override val type: EquipmentType get() = EquipmentType

    override val size: Rect get() = RECT_1x1

    companion object {

        fun values(): List<Equipment> = SimpleEquipment.values().toList() + listOf(Chest(), StoneChest())
    }
}
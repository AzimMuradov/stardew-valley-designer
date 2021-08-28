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

package me.azimmuradov.svc.svapi.editor.impl.entity

import me.azimmuradov.svc.svapi.editor.impl.entity.FloorType.DecorType
import me.azimmuradov.svc.svapi.editor.impl.entity.FloorType.GrassType
import me.azimmuradov.svc.svapi.editor.impl.entity.ObjectType.EquipmentType


sealed interface CartographerEntityId<out EType : CartographerEntityType> {

    val type: EType
}


enum class Decor : CartographerEntityId<DecorType> {
    WoodFloor,
    RusticPlankFloor,
    StrawFloor,
    WeatheredFloor,
    CrystalFloor,
    StoneFloor,
    StoneWalkwayFloor,
    BrickFloor,

    WoodPath,
    GravelPath,
    CobblestonePath,
    SteppingStonePath,
    CrystalPath;

    override val type: DecorType = DecorType
}


object Grass : CartographerEntityId<GrassType> {

    override val type: GrassType = GrassType
}


// ObjectType

enum class Equipment : CartographerEntityId<EquipmentType> {

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


    // Storage Equipment

    Chest,
    StoneChest,
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
    ;


    override val type: EquipmentType = EquipmentType
}
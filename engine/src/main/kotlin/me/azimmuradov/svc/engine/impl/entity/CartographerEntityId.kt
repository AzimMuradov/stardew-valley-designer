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

package me.azimmuradov.svc.engine.impl.entity

import me.azimmuradov.svc.engine.impl.entity.EntityWithoutFloorType.BuildingType
import me.azimmuradov.svc.engine.impl.entity.ObjectType.EquipmentType
import me.azimmuradov.svc.engine.impl.entity.ObjectType.FurnitureType.*


sealed interface CartographerEntityId<out EType : CartographerEntityType> {

    val type: EType
}


// FloorType

enum class Floor : CartographerEntityId<FloorType> {

    // Floors

    WoodFloor,
    RusticPlankFloor,
    StrawFloor,
    WeatheredFloor,
    CrystalFloor,
    StoneFloor,
    StoneWalkwayFloor,
    BrickFloor,


    // Paths

    WoodPath,
    GravelPath,
    CobblestonePath,
    SteppingStonePath,
    CrystalPath,


    // Grass

    Grass,
    ;


    override val type: FloorType = FloorType
}


// FloorFurnitureType

enum class FloorFurniture : CartographerEntityId<FloorFurnitureType> {

    // Rugs

    BambooMat,
    BurlapRug,
    WoodcutRug,
    NauticalRug,
    DarkRug,
    RedRug,
    LargeRedRug,
    MonsterRug,
    LightGreenRug,
    BlossomRug,
    LargeGreenRug,
    OldWorldRug,
    LargeCottageRug,
    GreenCottageRug,
    RedCottageRug,
    MysticRug,
    BoneRug,
    SnowyRug,
    PirateRug,
    PatchworkRug,
    FruitSaladRug,
    OceanicRug,
    IcyRug,
    FunkyRug,
    ModernRug,


    // Floor Dividers

    FloorDivider1R,
    FloorDivider1L,
    FloorDivider2R,
    FloorDivider2L,
    FloorDivider3R,
    FloorDivider3L,
    FloorDivider4R,
    FloorDivider4L,
    FloorDivider5R,
    FloorDivider5L,
    FloorDivider6R,
    FloorDivider6L,
    FloorDivider7R,
    FloorDivider7L,
    FloorDivider8R,
    FloorDivider8L,
    ;


    override val type: FloorFurnitureType = FloorFurnitureType
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

enum class HouseFurniture : CartographerEntityId<HouseFurnitureType> {

    // Beds

    ChildBed,
    SingleBed,
    DoubleBed,
    BirchDoubleBed,
    DeluxeRedDoubleBed,
    ExoticDoubleBed,
    FisherDoubleBed,
    ModernDoubleBed,
    PirateDoubleBed,
    StarryDoubleBed,
    StrawberryDoubleBed,
    TropicalBed,
    TropicalDoubleBed,
    WildDoubleBed,


    // Others

    MiniFridge, // TODO : Not a furniture
    ;


    override val type: HouseFurnitureType = HouseFurnitureType
}

enum class IndoorFurniture : CartographerEntityId<IndoorFurnitureType> {

    // Couches & Armchairs

    BlueCouch,
    BrownCouch,
    GreenCouch,
    RedCouch,
    YellowCouch,
    DarkCouch,
    WoodsyCouch,
    WizardCouch,
    LargeBrownCouch,
    BlueArmchair,
    BrownArmchair,
    GreenArmchair,
    RedArmchair,
    YellowArmchair,


    // Bookcases & Dressers

    ArtistBookcase,
    ModernBookcase,
    LuxuryBookcase,
    DarkBookcase,
    BirchDresser,
    OakDresser,
    WalnutDresser,
    MahoganyDresser,


    // Fireplaces

    BrickFireplace,
    ElegantFireplace,
    IridiumFireplace,
    MonsterFireplace,
    StoneFireplace,
    StoveFireplace,


    // Floor Lamps

    CountryLamp,
    ModernLamp,
    ClassicLamp,
    BoxLamp,
    CandleLamp,
    OrnateLamp,


    // TVs

    FloorTV,
    BudgetTV,
    PlasmaTV,
    TropicalTV,


    // Fish Tanks

    SmallFishTank,
    ModernFishTank,
    LargeFishTank,
    DeluxeFishTank,
    AquaticSanctuary,


    // Misc

    ChinaCabinet,

    // TODO : IndustrialPipe,
    ;


    override val type: IndoorFurnitureType = IndoorFurnitureType
}

enum class UniversalFurniture : CartographerEntityId<UniversalFurnitureType> {

    // Chairs

    OakChair,
    WalnutChair,
    BirchChair,
    MahoganyChair,
    RedDinerChair,
    BlueDinerChair,
    CountryChair,
    BreakfastChair,
    PinkOfficeChair,
    PurpleOfficeChair,
    GreenOfficeStool,
    OrangeOfficeStool,
    DarkThrone,
    DiningChairYellow,
    DiningChairRed,
    GreenPlushSeat,
    PinkPlushSeat,
    WinterChair,
    GroovyChair,
    CuteChair,
    StumpSeat,
    MetalChair,
    GreenStool,
    BlueStool,
    KingChair,
    CrystalChair,
    TropicalChair,


    // Benches

    BirchBench,
    OakBench,
    WalnutBench,
    MahoganyBench,
    ModernBench,


    // Tables

    OakTable,
    OakTeaTable,
    OakEndTable,
    BirchTable,
    BirchTeaTable,
    BirchEndTable,
    MahoganyTable,
    MahoganyTeaTable,
    MahoganyEndTable,
    WalnutTable,
    WalnutTeaTable,
    WalnutEndTable,
    ModernTable,
    ModernTeaTable,
    ModernEndTable,
    PuzzleTable,
    SunTable,
    MoonTable,
    LuxuryTable,
    DivinerTable,
    GrandmotherEndTable,
    PubTable,
    LuauTable,
    DarkTable,
    CandyTable,
    WinterTable,
    WinterEndTable,
    NeolithicTable,
    CoffeeTable,
    StoneSlab,


    // Long Tables

    ModernDiningTable,
    MahoganyDiningTable,
    FestiveDiningTable,
    WinterDiningTable,


    // House Plants

    HousePlant1,
    HousePlant2,
    HousePlant3,
    HousePlant4,
    HousePlant5,
    HousePlant6,
    HousePlant7,
    HousePlant8,
    HousePlant9,
    HousePlant10,
    HousePlant11,
    HousePlant12,
    HousePlant13,
    HousePlant14,
    HousePlant15,


    // Freestanding Decorative Plants

    DriedSunflowers,
    BonsaiTree,
    SmallPine,
    TreeColumn,
    SmallPlant,
    TablePlant,
    DeluxeTree,
    ExoticTree,
    IndoorPalm,
    TopiaryTree,
    ManicuredPine,
    TreeOfTheWinterStar,
    LongCactus,
    LongPalm,


    // Torches

    JungleTorch,
    PlainTorch,
    StumpTorch,


    // Misc

    CeramicPillar,
    GoldPillar,
    TotemPole,
    DecorativeBowl,
    DecorativeLantern,
    Globe,
    ModelShip,
    SmallCrystal,
    FutanBear,
    DecorativeTrashCan,


    // Other Decorations

    BearStatue,
    ChickenStatue,
    LgFutanBear,
    ObsidianVase,
    SkeletonStatue,
    SlothSkeletonL,
    SlothSkeletonM,
    SlothSkeletonR,
    StandingGeode,
    ButterflyHutch,
    LeahSculpture,
    SamBoombox,
    FutanRabbit,
    SmallJunimoPlush1,
    SmallJunimoPlush2,
    SmallJunimoPlush3,
    SmallJunimoPlush4,
    GreenSerpentStatue,
    PurpleSerpentStatue,
    BoboStatue,
    WumbusStatue,
    JunimoPlush,
    GourmandStatue,
    IridiumKrobus,
    SquirrelFigurine,


    // Catalogues

    Catalogue,
    FurnitureCatalogue,
    ;


    override val type: UniversalFurnitureType = UniversalFurnitureType
}


// EntityWithoutFloorType

enum class Building : CartographerEntityId<BuildingType> {

    // TODO
    ;


    override val type: BuildingType = BuildingType
}


// TODO (?) : Supply Crate

// TODO : WallFurniture
// Wall Lamps & Windows
// Decorative Hanging Plants
// Paintings
// Night Market Paintings
// Movie Posters
// Banners
// Wall Hangings

// TODO : Flooring [1-56]

// TODO : Wallpaper [1-112]
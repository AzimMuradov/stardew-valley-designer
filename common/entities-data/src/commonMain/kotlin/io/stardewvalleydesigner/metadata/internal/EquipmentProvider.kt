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

import io.stardewvalleydesigner.engine.entity.*
import io.stardewvalleydesigner.engine.entity.Equipment.*
import io.stardewvalleydesigner.metadata.*
import io.stardewvalleydesigner.metadata.SpritePage.Companion.UNIT


internal fun equipment(qe: QualifiedEntity<Equipment>): QualifiedEntityData {
    val entity = qe.entity

    fun craftable(
        index: Int,
        flavor: EntityFlavor? = null,
    ) = craftable(entity, index, flavor)

    fun fence(
        index: Int,
        page: SpritePage,
    ) = fence(entity, index, qe.qualifier as SpriteQualifier.FenceQualifier, page)

    fun seasonalPlant(
        index: Int,
    ) = seasonalPlant(entity, index, qe.qualifier as SpriteQualifier.SeasonQualifier)

    fun common(
        index: Int,
    ) = common(entity, index)

    fun chest(
        index: Int,
        color: Colors.ChestColors,
    ) = chest(entity, index, color)

    return when (entity) {
        // Common Equipment
        // (Artisan Equipment + Other Tools + Refining Equipment + Misc + Storage Equipment)

        SimpleEquipment.MayonnaiseMachine -> craftable(24)
        SimpleEquipment.BeeHouse -> craftable(10)
        SimpleEquipment.PreservesJar -> craftable(15)
        SimpleEquipment.CheesePress -> craftable(16)
        SimpleEquipment.Loom -> craftable(17)
        SimpleEquipment.Keg -> craftable(12)
        SimpleEquipment.OilMaker -> craftable(19)
        SimpleEquipment.Cask -> craftable(163)

        SimpleEquipment.GardenPot -> craftable(62)
        SimpleEquipment.Heater -> craftable(104)
        SimpleEquipment.AutoGrabber -> craftable(165)
        SimpleEquipment.AutoPetter -> craftable(272)

        SimpleEquipment.CharcoalKiln -> craftable(114)
        SimpleEquipment.Crystalarium -> craftable(21)
        SimpleEquipment.Furnace -> craftable(13)
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

        is Chest -> chest(130, color = entity.color)

        is StoneChest -> chest(232, color = entity.color)

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
        SimpleEquipment.WoodFence -> fence(322, SpritePage.Fence1)
        SimpleEquipment.StoneFence -> fence(323, SpritePage.Fence2)
        SimpleEquipment.IronFence -> fence(324, SpritePage.Fence3)
        SimpleEquipment.HardwoodFence -> fence(298, SpritePage.Fence5)

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
        SimpleEquipment.SeasonalDecor -> seasonalPlant(48)
        SimpleEquipment.TubOFlowers -> tubOFlowers(108, qe.qualifier as SpriteQualifier.TubOFlowersQualifier)
        SimpleEquipment.SeasonalPlant1 -> seasonalPlant(184)
        SimpleEquipment.SeasonalPlant2 -> seasonalPlant(188)
        SimpleEquipment.SeasonalPlant3 -> seasonalPlant(192)
        SimpleEquipment.SeasonalPlant4 -> seasonalPlant(196)
        SimpleEquipment.SeasonalPlant5 -> seasonalPlant(200)
        SimpleEquipment.SeasonalPlant6 -> seasonalPlant(204)
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
}


private fun common(
    entity: Entity<*>,
    index: Int,
) = QualifiedEntityData(
    qualifiedEntity = QualifiedEntity(entity),
    entityId = EntityId(
        page = EntityPage.CommonObjects,
        localId = index,
        flavor = null,
    ),
    spriteId = SpriteId.RegularSprite(
        page = SpritePage.CommonObjects,
        offset = SpriteOffset(
            x = index % (SpritePage.CommonObjects.width / UNIT) * SpritePage.CommonObjects.grain.w,
            y = index / (SpritePage.CommonObjects.width / UNIT) * SpritePage.CommonObjects.grain.h,
        ) * UNIT,
        size = SpriteSize(w = 1, h = 1) * UNIT,
    )
)

private fun craftable(
    entity: Entity<*>,
    index: Int,
    flavor: EntityFlavor? = null,
) = QualifiedEntityData(
    qualifiedEntity = QualifiedEntity(entity),
    entityId = EntityId(
        page = EntityPage.Craftables,
        localId = index,
        flavor = flavor,
    ),
    spriteId = SpriteId.RegularSprite(
        page = SpritePage.Craftables,
        offset = SpriteOffset(
            x = index % (SpritePage.Craftables.width / UNIT) * SpritePage.Craftables.grain.w,
            y = index / (SpritePage.Craftables.width / UNIT) * SpritePage.Craftables.grain.h,
        ) * UNIT,
        size = SpriteSize(w = 1, h = 2) * UNIT,
    )
)

private fun fence(
    entity: Entity<*>,
    index: Int,
    qualifier: SpriteQualifier.FenceQualifier,
    page: SpritePage,
) = QualifiedEntityData(
    qualifiedEntity = QualifiedEntity(entity, qualifier),
    entityId = EntityId(
        page = EntityPage.CommonObjects,
        localId = index,
    ),
    spriteId = SpriteId.RegularSprite(
        page = page,
        offset = when (qualifier.variant) {
            FenceVariant.Single -> SpriteOffset(x = 2, y = 2)
            FenceVariant.HasLeft -> SpriteOffset(x = 0, y = 6)
            FenceVariant.HasRight -> SpriteOffset(x = 1, y = 6)
            FenceVariant.HasLeftAndRight -> SpriteOffset(x = 1, y = 4)
            FenceVariant.HasTop -> SpriteOffset(x = 0, y = 2)
            FenceVariant.HasLeftAndBottom -> SpriteOffset(x = 2, y = 0)
            FenceVariant.HasRightAndBottom -> SpriteOffset(x = 0, y = 0)
            FenceVariant.HasLeftRightAndBottom -> SpriteOffset(x = 1, y = 2)
            FenceVariant.HasLeftAndTop -> SpriteOffset(x = 2, y = 4)
            FenceVariant.HasRightAndTop -> SpriteOffset(x = 0, y = 4)
        } * UNIT,
        size = SpriteSize(w = 1, h = 2) * UNIT,
    )
)

private fun seasonalPlant(
    entity: Entity<*>,
    index: Int,
    qualifier: SpriteQualifier.SeasonQualifier,
) = QualifiedEntityData(
    qualifiedEntity = QualifiedEntity(entity, qualifier),
    entityId = EntityId(
        page = EntityPage.Craftables,
        localId = index + qualifier.season.ordinal,
    ),
    spriteId = SpriteId.RegularSprite(
        page = SpritePage.Craftables,
        offset = SpriteOffset(
            x = (index + qualifier.season.ordinal) % (SpritePage.Craftables.width / UNIT) * SpritePage.Craftables.grain.w,
            y = (index + qualifier.season.ordinal) / (SpritePage.Craftables.width / UNIT) * SpritePage.Craftables.grain.h,
        ) * UNIT,
        size = SpriteSize(w = 1, h = 2) * UNIT,
    )
)

fun tubOFlowers(
    index: Int,
    qualifier: SpriteQualifier.TubOFlowersQualifier,
) = QualifiedEntityData(
    qualifiedEntity = QualifiedEntity(SimpleEquipment.TubOFlowers, qualifier),
    entityId = EntityId(
        page = EntityPage.Craftables,
        localId = index,
    ),
    spriteId = SpriteId.RegularSprite(
        page = SpritePage.Craftables,
        offset = SpriteOffset(
            x = index % (SpritePage.Craftables.width / UNIT) * SpritePage.Craftables.grain.w + if (!qualifier.isBlooming) 1 else 0,
            y = index / (SpritePage.Craftables.width / UNIT) * SpritePage.Craftables.grain.h,
        ) * UNIT,
        size = SpriteSize(w = 1, h = 2) * UNIT,
    )
)

private fun chest(
    entity: Entity<*>,
    index: Int,
    color: Colors.ChestColors,
): QualifiedEntityData {
    val value = color.value

    return if (value == null) {
        QualifiedEntityData(
            qualifiedEntity = QualifiedEntity(entity),
            entityId = EntityId(
                page = EntityPage.Craftables,
                localId = index,
                flavor = color,
            ),
            spriteId = SpriteId.RegularSprite(
                page = SpritePage.Craftables,
                size = SpriteSize(w = 1, h = 2) * UNIT,
                offset = SpriteOffset(
                    x = index % (SpritePage.Craftables.width / UNIT) * SpritePage.Craftables.grain.w,
                    y = index / (SpritePage.Craftables.width / UNIT) * SpritePage.Craftables.grain.h,
                ) * UNIT,
            )
        )
    } else {
        val imgIndex = if (entity is Chest) 168 else index
        QualifiedEntityData(
            qualifiedEntity = QualifiedEntity(entity),
            entityId = EntityId(
                page = EntityPage.Craftables,
                localId = index,
                flavor = color,
            ),
            spriteId = SpriteId.ChestSprite(
                tint = value,
                offset = SpriteOffset(
                    x = imgIndex % (SpritePage.Craftables.width / UNIT) * SpritePage.Craftables.grain.w,
                    y = imgIndex / (SpritePage.Craftables.width / UNIT) * SpritePage.Craftables.grain.h,
                ) * UNIT,
                coverOffset = SpriteOffset(
                    x = imgIndex % (SpritePage.Craftables.width / UNIT) * SpritePage.Craftables.grain.w,
                    y = imgIndex / (SpritePage.Craftables.width / UNIT) * SpritePage.Craftables.grain.h + 2,
                ) * UNIT,
            )
        )
    }
}

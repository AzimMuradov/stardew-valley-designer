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
import io.stardewvalleydesigner.engine.entity.Building.*
import io.stardewvalleydesigner.engine.entity.Building.SimpleBuilding.*
import io.stardewvalleydesigner.metadata.*


internal fun building(qe: QualifiedEntity<Building>): QualifiedEntityData {
    val entity = qe.entity

    fun building(
        page: EntityPage,
        spritePage: SpritePage,
    ) = building(entity, page, spritePage)

    fun coloredBuilding(
        flavor: FarmBuildingColors,
        page: EntityPage,
        spritePage: SpritePage,
    ) = coloredBuilding(entity, flavor, page, spritePage)

    fun junimoHut() = junimoHut(
        entity,
        qualifier = qe.qualifier as SpriteQualifier.SeasonQualifier,
    )

    fun cabin(
        page: EntityPage,
        spritePage: SpritePage,
        id: Int,
    ) = cabin(entity, page, spritePage, id)

    fun coloredCabin(
        flavor: FarmBuildingColors,
        page: EntityPage,
        spritePage: SpritePage,
        id: Int,
    ) = coloredCabin(entity, flavor, page, spritePage, id)

    return when (entity) {
        Barn1 -> building(EntityPage.Barn1, SpritePage.Barn1)
        Barn2 -> building(EntityPage.Barn2, SpritePage.Barn2)
        is Barn3 -> coloredBuilding(entity.farmBuildingColors, EntityPage.Barn3, SpritePage.Barn3)

        Coop1 -> building(EntityPage.Coop1, SpritePage.Coop1)
        Coop2 -> building(EntityPage.Coop2, SpritePage.Coop2)
        is Coop3 -> coloredBuilding(entity.farmBuildingColors, EntityPage.Coop3, SpritePage.Coop3)

        Shed1 -> building(EntityPage.Shed, SpritePage.Shed)
        is Shed2 -> coloredBuilding(entity.farmBuildingColors, EntityPage.BigShed, SpritePage.BigShed)

        StoneCabin1 -> cabin(EntityPage.StoneCabin, SpritePage.StoneCabin, id = 0)
        StoneCabin2 -> cabin(EntityPage.StoneCabin, SpritePage.StoneCabin, id = 1)
        is StoneCabin3 -> coloredCabin(entity.farmBuildingColors, EntityPage.StoneCabin, SpritePage.StoneCabin, id = 2)

        PlankCabin1 -> cabin(EntityPage.PlankCabin, SpritePage.PlankCabin, id = 0)
        PlankCabin2 -> cabin(EntityPage.PlankCabin, SpritePage.PlankCabin, id = 1)
        is PlankCabin3 -> coloredCabin(entity.farmBuildingColors, EntityPage.PlankCabin, SpritePage.PlankCabin, id = 2)

        LogCabin1 -> cabin(EntityPage.LogCabin, SpritePage.LogCabin, id = 0)
        LogCabin2 -> cabin(EntityPage.LogCabin, SpritePage.LogCabin, id = 1)
        is LogCabin3 -> coloredCabin(entity.farmBuildingColors, EntityPage.LogCabin, SpritePage.LogCabin, id = 2)

        EarthObelisk -> building(EntityPage.EarthObelisk, SpritePage.EarthObelisk)
        WaterObelisk -> building(EntityPage.WaterObelisk, SpritePage.WaterObelisk)
        DesertObelisk -> building(EntityPage.DesertObelisk, SpritePage.DesertObelisk)
        IslandObelisk -> building(EntityPage.IslandObelisk, SpritePage.IslandObelisk)
        JunimoHut -> junimoHut()
        GoldClock -> building(EntityPage.GoldClock, SpritePage.GoldClock)

        Mill -> building(EntityPage.Mill, SpritePage.Mill)
        Silo -> building(EntityPage.Silo, SpritePage.Silo)
        Well -> building(EntityPage.Well, SpritePage.Well)
        is Stable -> building(EntityPage.Stable, SpritePage.Stable) // TODO : Colors
        is FishPond -> building(EntityPage.FishPond, SpritePage.FishPond) // TODO : Colors and other variations
        SlimeHutch -> building(EntityPage.SlimeHutch, SpritePage.SlimeHutch)
        ShippingBin -> building(EntityPage.ShippingBin, SpritePage.ShippingBin)
    }
}


private fun building(
    entity: Entity<*>,
    page: EntityPage,
    spritePage: SpritePage,
) = QualifiedEntityData(
    qualifiedEntity = QualifiedEntity(entity),
    entityId = EntityId(page, localId = 0),
    spriteId = SpriteId.RegularSprite(
        page = spritePage,
        offset = SpriteOffset(x = 0, y = 0),
        size = SpriteSize(spritePage.width, spritePage.height),
    )
)

// TODO : Colors
private fun coloredBuilding(
    entity: Entity<*>,
    flavor: FarmBuildingColors,
    page: EntityPage,
    spritePage: SpritePage,
) = QualifiedEntityData(
    qualifiedEntity = QualifiedEntity(entity),
    entityId = EntityId(page, localId = 0, flavor),
    spriteId = SpriteId.RegularSprite(
        page = spritePage,
        offset = SpriteOffset(x = 0, y = 0),
        size = SpriteSize(spritePage.width, spritePage.height),
    )
)

private fun junimoHut(
    entity: Entity<*>,
    qualifier: SpriteQualifier.SeasonQualifier,
) = QualifiedEntityData(
    qualifiedEntity = QualifiedEntity(entity, qualifier),
    entityId = EntityId(EntityPage.JunimoHut, localId = 0),
    spriteId = SpriteId.RegularSprite(
        page = SpritePage.JunimoHut,
        offset = SpriteOffset(x = 48 * qualifier.season.ordinal, y = 0),
        size = SpriteSize(w = 48, h = SpritePage.JunimoHut.height),
    ),
)

private fun cabin(
    entity: Entity<*>,
    page: EntityPage,
    spritePage: SpritePage,
    id: Int,
) = QualifiedEntityData(
    qualifiedEntity = QualifiedEntity(entity),
    entityId = EntityId(page, id),
    spriteId = SpriteId.RegularSprite(
        page = spritePage,
        offset = SpriteOffset(x = id * (spritePage.width / 3), y = 0),
        size = SpriteSize(w = spritePage.width / 3, h = spritePage.height),
    )
)

// TODO : Colors
private fun coloredCabin(
    entity: Entity<*>,
    flavor: FarmBuildingColors,
    page: EntityPage,
    spritePage: SpritePage,
    id: Int,
) = QualifiedEntityData(
    qualifiedEntity = QualifiedEntity(entity),
    entityId = EntityId(page, id, flavor),
    spriteId = SpriteId.RegularSprite(
        page = spritePage,
        offset = SpriteOffset(x = id * (spritePage.width / 3), y = 0),
        size = SpriteSize(w = spritePage.width / 3, h = spritePage.height),
    )
)

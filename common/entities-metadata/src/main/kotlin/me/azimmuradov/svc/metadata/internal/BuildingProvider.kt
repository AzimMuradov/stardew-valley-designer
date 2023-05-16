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

import me.azimmuradov.svc.engine.entity.Building
import me.azimmuradov.svc.engine.entity.Building.*
import me.azimmuradov.svc.metadata.EntityMetadata
import me.azimmuradov.svc.metadata.EntityPage


internal fun building(entity: Building): EntityMetadata = when (entity) {

    // Barns

    SimpleBuilding.Barn1 -> building(EntityPage.Barn1)
    SimpleBuilding.Barn2 -> building(EntityPage.Barn2)
    is Barn3 -> building(EntityPage.Barn3) // TODO : Colors

    // Coops

    SimpleBuilding.Coop1 -> building(EntityPage.Coop1)
    SimpleBuilding.Coop2 -> building(EntityPage.Coop2)
    is Coop3 -> building(EntityPage.Coop3) // TODO : Colors

    // Sheds

    SimpleBuilding.Shed1 -> building(EntityPage.Shed)
    is Shed2 -> building(EntityPage.BigShed) // TODO : Colors

    // Cabins

    SimpleBuilding.StoneCabin1 -> cabin(EntityPage.StoneCabin, id = 0)
    SimpleBuilding.StoneCabin2 -> cabin(EntityPage.StoneCabin, id = 1)
    is StoneCabin3 -> cabin(EntityPage.StoneCabin, id = 2) // TODO : Colors

    SimpleBuilding.PlankCabin1 -> cabin(EntityPage.PlankCabin, id = 0)
    SimpleBuilding.PlankCabin2 -> cabin(EntityPage.PlankCabin, id = 1)
    is PlankCabin3 -> cabin(EntityPage.PlankCabin, id = 2) // TODO : Colors

    SimpleBuilding.LogCabin1 -> cabin(EntityPage.PlankCabin, id = 0)
    SimpleBuilding.LogCabin2 -> cabin(EntityPage.PlankCabin, id = 1)
    is LogCabin3 -> cabin(EntityPage.PlankCabin, id = 2) // TODO : Colors

    // Magical Buildings

    SimpleBuilding.EarthObelisk -> building(EntityPage.EarthObelisk)
    SimpleBuilding.WaterObelisk -> building(EntityPage.WaterObelisk)
    SimpleBuilding.DesertObelisk -> building(EntityPage.DesertObelisk)
    SimpleBuilding.IslandObelisk -> building(EntityPage.IslandObelisk)
    SimpleBuilding.JunimoHut -> building(EntityPage.JunimoHut)
    SimpleBuilding.GoldClock -> building(EntityPage.GoldClock)

    // Others

    SimpleBuilding.Mill -> building(EntityPage.Mill)
    SimpleBuilding.Silo -> building(EntityPage.Silo)
    SimpleBuilding.Well -> building(EntityPage.Well)
    is Stable -> building(EntityPage.Stable) // TODO : Colors
    is FishPond -> building(EntityPage.FishPond) // TODO : Colors and other variations
    SimpleBuilding.SlimeHutch -> building(EntityPage.SlimeHutch)
    SimpleBuilding.ShippingBin -> building(EntityPage.ShippingBin)
}

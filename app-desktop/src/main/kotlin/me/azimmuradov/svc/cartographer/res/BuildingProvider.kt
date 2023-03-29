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

package me.azimmuradov.svc.cartographer.res

import me.azimmuradov.svc.engine.entity.Building
import me.azimmuradov.svc.engine.entity.Building.*


fun building(entity: Building): Sprite = when (entity) {

    // Barns

    SimpleBuilding.Barn1 -> TODO()
    SimpleBuilding.Barn2 -> TODO()
    is Barn3 -> TODO()

    // Coops

    SimpleBuilding.Coop1 -> TODO()
    SimpleBuilding.Coop2 -> TODO()
    is Coop3 -> TODO()

    // Sheds

    SimpleBuilding.Shed1 -> TODO()
    is Shed2 -> TODO()

    // Cabins

    SimpleBuilding.StoneCabin1 -> TODO()
    SimpleBuilding.StoneCabin2 -> TODO()
    is StoneCabin3 -> TODO()

    SimpleBuilding.PlankCabin1 -> TODO()
    SimpleBuilding.PlankCabin2 -> TODO()
    is PlankCabin3 -> TODO()

    SimpleBuilding.LogCabin1 -> TODO()
    SimpleBuilding.LogCabin2 -> TODO()
    is LogCabin3 -> TODO()

    // Magical Buildings

    SimpleBuilding.EarthObelisk -> TODO()
    SimpleBuilding.WaterObelisk -> TODO()
    SimpleBuilding.DesertObelisk -> TODO()
    SimpleBuilding.IslandObelisk -> TODO()
    SimpleBuilding.JunimoHut -> TODO()
    SimpleBuilding.GoldClock -> TODO()

    // Others

    SimpleBuilding.Mill -> TODO()
    SimpleBuilding.Silo -> TODO()
    SimpleBuilding.Well -> TODO()
    is Stable -> TODO()
    is FishPond -> TODO()
    SimpleBuilding.SlimeHutch -> TODO()
    SimpleBuilding.ShippingBin -> TODO()
}

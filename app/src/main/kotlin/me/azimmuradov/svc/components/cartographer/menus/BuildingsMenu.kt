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

package me.azimmuradov.svc.components.cartographer.menus

import me.azimmuradov.svc.engine.impl.entity.ids.Building


@OptIn(ExperimentalStdlibApi::class)
val BuildingsMenu: MenuRoot = MenuRoot(
    title = MenuTitle.Buildings,
    elements = buildList {
        this += MenuElement.Menu(
            title = MenuTitle.BuildingsBarns,
            elements = listOf(
                MenuElement.Value(Building.SimpleBuilding.Barn1),
                MenuElement.Value(Building.SimpleBuilding.Barn2),
                MenuElement.Value(Building.Barn3()),
            )
        )

        this += MenuElement.Menu(
            title = MenuTitle.BuildingsCoops,
            elements = listOf(
                MenuElement.Value(Building.SimpleBuilding.Coop1),
                MenuElement.Value(Building.SimpleBuilding.Coop2),
                MenuElement.Value(Building.Coop3()),
            )
        )

        this += MenuElement.Menu(
            title = MenuTitle.BuildingsSheds,
            elements = listOf(
                MenuElement.Value(Building.SimpleBuilding.Shed1),
                MenuElement.Value(Building.Shed2()),
            )
        )

        this += MenuElement.Menu(
            title = MenuTitle.BuildingsCabins,
            elements = listOf(
                MenuElement.Value(Building.SimpleBuilding.StoneCabin1),
                MenuElement.Value(Building.SimpleBuilding.StoneCabin2),
                MenuElement.Value(Building.StoneCabin3()),
                MenuElement.Value(Building.SimpleBuilding.PlankCabin1),
                MenuElement.Value(Building.SimpleBuilding.PlankCabin2),
                MenuElement.Value(Building.PlankCabin3()),
                MenuElement.Value(Building.SimpleBuilding.LogCabin1),
                MenuElement.Value(Building.SimpleBuilding.LogCabin2),
                MenuElement.Value(Building.LogCabin3()),
            )
        )

        this += MenuElement.Menu(
            title = MenuTitle.BuildingsMagical,
            elements = listOf(
                MenuElement.Value(Building.SimpleBuilding.EarthObelisk),
                MenuElement.Value(Building.SimpleBuilding.WaterObelisk),
                MenuElement.Value(Building.SimpleBuilding.DesertObelisk),
                MenuElement.Value(Building.SimpleBuilding.IslandObelisk),
                MenuElement.Value(Building.SimpleBuilding.JunimoHut),
                MenuElement.Value(Building.SimpleBuilding.GoldClock),
            )
        )

        this.addAll(
            listOf(
                MenuElement.Value(Building.SimpleBuilding.Mill),
                MenuElement.Value(Building.SimpleBuilding.Silo),
                MenuElement.Value(Building.SimpleBuilding.Well),
                MenuElement.Value(Building.Stable()),
                MenuElement.Value(Building.FishPond()),
                MenuElement.Value(Building.SimpleBuilding.SlimeHutch),
                MenuElement.Value(Building.SimpleBuilding.ShippingBin),
            )
        )
    }
)
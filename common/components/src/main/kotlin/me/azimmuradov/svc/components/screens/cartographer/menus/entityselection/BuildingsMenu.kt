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

package me.azimmuradov.svc.components.screens.cartographer.menus.entityselection

import me.azimmuradov.svc.components.screens.cartographer.menus.EntitySelectionMenu
import me.azimmuradov.svc.engine.entity.ids.Building
import me.azimmuradov.svc.utils.menu.menu
import me.azimmuradov.svc.components.screens.cartographer.menus.EntitySelectionRoot as Root


val BuildingsMenu: EntitySelectionMenu =
    menu(root = Root.Buildings) {
        submenu(root = Root.BuildingsBarns) {
            items(
                Building.SimpleBuilding.Barn1,
                Building.SimpleBuilding.Barn2,
                Building.Barn3(),
            )
        }

        submenu(root = Root.BuildingsCoops) {
            items(
                Building.SimpleBuilding.Coop1,
                Building.SimpleBuilding.Coop2,
                Building.Coop3(),
            )
        }

        submenu(root = Root.BuildingsSheds) {
            items(
                Building.SimpleBuilding.Shed1,
                Building.Shed2(),
            )
        }

        submenu(root = Root.BuildingsCabins) {
            items(
                Building.SimpleBuilding.StoneCabin1,
                Building.SimpleBuilding.StoneCabin2,
                Building.StoneCabin3(),
                Building.SimpleBuilding.PlankCabin1,
                Building.SimpleBuilding.PlankCabin2,
                Building.PlankCabin3(),
                Building.SimpleBuilding.LogCabin1,
                Building.SimpleBuilding.LogCabin2,
                Building.LogCabin3(),
            )
        }

        submenu(root = Root.BuildingsMagical) {
            items(
                Building.SimpleBuilding.EarthObelisk,
                Building.SimpleBuilding.WaterObelisk,
                Building.SimpleBuilding.DesertObelisk,
                Building.SimpleBuilding.IslandObelisk,
                Building.SimpleBuilding.JunimoHut,
                Building.SimpleBuilding.GoldClock,
            )
        }

        items(
            Building.SimpleBuilding.Mill,
            Building.SimpleBuilding.Silo,
            Building.SimpleBuilding.Well,
            Building.Stable(),
            Building.FishPond(),
            Building.SimpleBuilding.SlimeHutch,
            Building.SimpleBuilding.ShippingBin,
        )
    }
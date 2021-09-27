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
import me.azimmuradov.svc.engine.entity.ids.Equipment
import me.azimmuradov.svc.utils.menu.menu
import me.azimmuradov.svc.components.screens.cartographer.menus.EntitySelectionRoot as Root


val FarmElementsMenu: EntitySelectionMenu =
    menu(root = Root.FarmElements) {
        submenu(root = Root.FarmElementsCrops) {
            // TODO
        }

        submenu(root = Root.FarmElementsForaging) {
            // TODO
        }

        submenu(root = Root.FarmElementsScarecrows) {
            items(
                Equipment.SimpleEquipment.Scarecrow,
                Equipment.SimpleEquipment.DeluxeScarecrow,
                Equipment.SimpleEquipment.Rarecrow1,
                Equipment.SimpleEquipment.Rarecrow2,
                Equipment.SimpleEquipment.Rarecrow3,
                Equipment.SimpleEquipment.Rarecrow4,
                Equipment.SimpleEquipment.Rarecrow5,
                Equipment.SimpleEquipment.Rarecrow6,
                Equipment.SimpleEquipment.Rarecrow7,
                Equipment.SimpleEquipment.Rarecrow8,
            )
        }

        submenu(root = Root.FarmElementsSprinklers) {
            items(
                Equipment.SimpleEquipment.Sprinkler,
                Equipment.SimpleEquipment.QualitySprinkler,
                Equipment.SimpleEquipment.IridiumSprinkler,
                Equipment.SimpleEquipment.IridiumSprinklerWithPressureNozzle,
            )
        }
    }
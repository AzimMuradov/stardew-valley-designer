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

package me.azimmuradov.svc.components.cartographer.menus.entityselection

import me.azimmuradov.svc.components.cartographer.menus.menu
import me.azimmuradov.svc.engine.llsvc.entity.ids.Equipment
import me.azimmuradov.svc.components.cartographer.menus.entityselection.EntitySelectionMenuTitle as T


val FarmElementsMenu: EntitySelectionMenu =
    menu(title = T.FarmElements) {
        submenu(title = T.FarmElementsCrops) {
            // TODO
        }

        submenu(title = T.FarmElementsForaging) {
            // TODO
        }

        submenu(title = T.FarmElementsScarecrows) {
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

        submenu(title = T.FarmElementsSprinklers) {
            items(
                Equipment.SimpleEquipment.Sprinkler,
                Equipment.SimpleEquipment.QualitySprinkler,
                Equipment.SimpleEquipment.IridiumSprinkler,
                Equipment.SimpleEquipment.IridiumSprinklerWithPressureNozzle,
            )
        }
    }
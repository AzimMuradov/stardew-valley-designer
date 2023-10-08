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

package io.stardewvalleydesigner.editor.menus.entityselection

import io.stardewvalleydesigner.editor.menus.EntitySelectionMenu
import io.stardewvalleydesigner.engine.entity.Equipment
import io.stardewvalleydesigner.engine.entity.Floor
import io.stardewvalleydesigner.uilib.menu.menu
import io.stardewvalleydesigner.editor.menus.EntitySelectionRoot as Root


val TerrainElementsMenu: EntitySelectionMenu = menu(root = Root.TerrainElements) {
    submenu(root = Root.TerrainElementsFloors) {
        items(
            Floor.WoodFloor,
            Floor.RusticPlankFloor,
            Floor.StrawFloor,
            Floor.WeatheredFloor,
            Floor.CrystalFloor,
            Floor.StoneFloor,
            Floor.StoneWalkwayFloor,
            Floor.BrickFloor,
            Floor.WoodPath,
            Floor.GravelPath,
            Floor.CobblestonePath,
            Floor.SteppingStonePath,
            Floor.CrystalPath,
            Floor.Grass,
        )
    }

    submenu(root = Root.TerrainElementsFences) {
        items(
            Equipment.SimpleEquipment.Gate,
            Equipment.SimpleEquipment.WoodFence,
            Equipment.SimpleEquipment.StoneFence,
            Equipment.SimpleEquipment.IronFence,
            Equipment.SimpleEquipment.HardwoodFence,
        )
    }

    submenu(root = Root.TerrainElementsSigns) {
        items(
            Equipment.SimpleEquipment.WoodSign,
            Equipment.SimpleEquipment.StoneSign,
            Equipment.SimpleEquipment.DarkSign,
        )
    }

    submenu(root = Root.TerrainElementsLighting) {
        items(
            Equipment.SimpleEquipment.Torch,
            Equipment.SimpleEquipment.Campfire,
            Equipment.SimpleEquipment.WoodenBrazier,
            Equipment.SimpleEquipment.StoneBrazier,
            Equipment.SimpleEquipment.GoldBrazier,
            Equipment.SimpleEquipment.CarvedBrazier,
            Equipment.SimpleEquipment.StumpBrazier,
            Equipment.SimpleEquipment.BarrelBrazier,
            Equipment.SimpleEquipment.SkullBrazier,
            Equipment.SimpleEquipment.MarbleBrazier,
            Equipment.SimpleEquipment.WoodLampPost,
            Equipment.SimpleEquipment.IronLampPost,
            Equipment.SimpleEquipment.JackOLantern,
        )
    }
}

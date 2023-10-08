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
import io.stardewvalleydesigner.engine.entity.Crop
import io.stardewvalleydesigner.engine.entity.Equipment
import io.stardewvalleydesigner.uilib.menu.menu
import io.stardewvalleydesigner.editor.menus.EntitySelectionRoot as Root


val FarmElementsMenu: EntitySelectionMenu = menu(root = Root.FarmElements) {
    submenu(root = Root.FarmElementsCrops) {
        submenu(Root.FarmElementsCropsSpring) {
            items(
                Crop.SimpleCrop.BlueJazz,
                Crop.SimpleCrop.Cauliflower,
                Crop.SimpleCrop.CoffeeBean,
                Crop.SimpleCrop.Garlic,
                Crop.SimpleCrop.GreenBean,
                Crop.SimpleCrop.Kale,
                Crop.SimpleCrop.Parsnip,
                Crop.SimpleCrop.Potato,
                Crop.SimpleCrop.Rhubarb,
                Crop.SimpleCrop.Strawberry,
                Crop.SimpleCrop.Tulip,
                Crop.SimpleCrop.Rice,
            )
        }

        submenu(Root.FarmElementsCropsSummer) {
            items(
                Crop.SimpleCrop.Blueberry,
                Crop.SimpleCrop.Corn,
                Crop.SimpleCrop.Hops,
                Crop.SimpleCrop.HotPepper,
                Crop.SimpleCrop.Melon,
                Crop.SimpleCrop.Poppy,
                Crop.SimpleCrop.Radish,
                Crop.SimpleCrop.RedCabbage,
                Crop.SimpleCrop.Starfruit,
                Crop.SimpleCrop.SummerSpangle,
                Crop.SimpleCrop.Sunflower,
                Crop.SimpleCrop.Tomato,
                Crop.SimpleCrop.Wheat,
            )
        }

        submenu(Root.FarmElementsCropsFall) {
            items(
                Crop.SimpleCrop.Amaranth,
                Crop.SimpleCrop.Artichoke,
                Crop.SimpleCrop.Beet,
                Crop.SimpleCrop.BokChoy,
                Crop.SimpleCrop.Cranberries,
                Crop.SimpleCrop.Eggplant,
                Crop.SimpleCrop.FairyRose,
                Crop.SimpleCrop.Grape,
                Crop.SimpleCrop.Pumpkin,
                Crop.SimpleCrop.Yam,
            )
        }

        submenu(Root.FarmElementsCropsSpecial) {
            items(
                Crop.SimpleCrop.AncientFruit,
                Crop.SimpleCrop.CactusFruit,
                Crop.SimpleCrop.Pineapple,
                Crop.SimpleCrop.TaroRoot,
                Crop.SimpleCrop.SweetGemBerry,
            )
        }
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
            // TODO : Equipment.SimpleEquipment.IridiumSprinklerWithPressureNozzle,
        )
    }
}

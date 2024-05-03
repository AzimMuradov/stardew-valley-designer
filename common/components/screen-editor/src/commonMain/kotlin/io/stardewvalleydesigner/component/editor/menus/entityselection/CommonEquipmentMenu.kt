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

package io.stardewvalleydesigner.component.editor.menus.entityselection

import io.stardewvalleydesigner.cmplib.menu.menu
import io.stardewvalleydesigner.component.editor.menus.EntitySelectionMenu
import io.stardewvalleydesigner.engine.entity.Equipment
import io.stardewvalleydesigner.component.editor.menus.EntitySelectionRoot as Root


// TODO : Add submenus
val CommonEquipmentMenu: EntitySelectionMenu = menu(root = Root.CommonEquipment) {
    items(
        Equipment.SimpleEquipment.MayonnaiseMachine,
        Equipment.SimpleEquipment.BeeHouse,
        Equipment.SimpleEquipment.PreservesJar,
        Equipment.SimpleEquipment.CheesePress,
        Equipment.SimpleEquipment.Loom,
        Equipment.SimpleEquipment.Keg,
        Equipment.SimpleEquipment.OilMaker,
        Equipment.SimpleEquipment.Cask,

        Equipment.SimpleEquipment.GardenPot,
        Equipment.SimpleEquipment.Heater,
        Equipment.SimpleEquipment.AutoGrabber,
        Equipment.SimpleEquipment.AutoPetter,

        Equipment.SimpleEquipment.CharcoalKiln,
        Equipment.SimpleEquipment.Crystalarium,
        Equipment.SimpleEquipment.Furnace,
        Equipment.SimpleEquipment.LightningRod,
        Equipment.SimpleEquipment.SolarPanel,
        Equipment.SimpleEquipment.RecyclingMachine,
        Equipment.SimpleEquipment.SeedMaker,
        Equipment.SimpleEquipment.SlimeIncubator,
        Equipment.SimpleEquipment.OstrichIncubator,
        Equipment.SimpleEquipment.SlimeEggPress,
        Equipment.SimpleEquipment.WormBin,
        Equipment.SimpleEquipment.BoneMill,
        Equipment.SimpleEquipment.GeodeCrusher,
        Equipment.SimpleEquipment.WoodChipper,

        Equipment.SimpleEquipment.MiniJukebox,
        Equipment.SimpleEquipment.MiniObelisk,
        Equipment.SimpleEquipment.FarmComputer,
        Equipment.SimpleEquipment.Hopper,
        Equipment.SimpleEquipment.Deconstructor,
        Equipment.SimpleEquipment.CoffeeMaker,
        Equipment.SimpleEquipment.Telephone,
        Equipment.SimpleEquipment.SewingMachine,
        Equipment.SimpleEquipment.Workbench,
        Equipment.SimpleEquipment.MiniShippingBin,

        // TODO (?) : CrabPot,
        // TODO (?) : Tapper,
        // TODO (?) : HeavyTapper,

        Equipment.Chest(),
        Equipment.StoneChest(),
        Equipment.SimpleEquipment.JunimoChest,
    )
}

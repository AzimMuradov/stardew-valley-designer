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

import io.stardewvalleydesigner.engine.entity.Crop
import io.stardewvalleydesigner.metadata.EntityMetadata


internal fun crop(entity: Crop): EntityMetadata = when (entity) {
    Crop.SimpleCrop.Parsnip -> crop(id = 0)
    Crop.SimpleCrop.GreenBean -> crop(id = 1)
    Crop.SimpleCrop.Cauliflower -> crop(id = 2)
    Crop.SimpleCrop.Potato -> crop(id = 3)
    Crop.SimpleCrop.Garlic -> crop(id = 4)
    Crop.SimpleCrop.Kale -> crop(id = 5)
    Crop.SimpleCrop.Rhubarb -> crop(id = 6)
    Crop.SimpleCrop.Melon -> crop(id = 7)
    Crop.SimpleCrop.Tomato -> crop(id = 8)
    Crop.SimpleCrop.Blueberry -> crop(id = 9)
    Crop.SimpleCrop.HotPepper -> crop(id = 10)
    Crop.SimpleCrop.Wheat -> crop(id = 11)
    Crop.SimpleCrop.Radish -> crop(id = 12)
    Crop.SimpleCrop.RedCabbage -> crop(id = 13)
    Crop.SimpleCrop.Starfruit -> crop(id = 14)
    Crop.SimpleCrop.Corn -> crop(id = 15)
    Crop.SimpleCrop.Eggplant -> crop(id = 16)
    Crop.SimpleCrop.Artichoke -> crop(id = 17)
    Crop.SimpleCrop.Pumpkin -> crop(id = 18)
    Crop.SimpleCrop.BokChoy -> crop(id = 19)
    Crop.SimpleCrop.Yam -> crop(id = 20)
    Crop.SimpleCrop.Cranberries -> crop(id = 21)
    Crop.SimpleCrop.Beet -> crop(id = 22)
    Crop.SimpleCrop.AncientFruit -> crop(id = 24)
    Crop.SimpleCrop.Tulip -> crop(id = 26)
    Crop.SimpleCrop.BlueJazz -> crop(id = 27)
    Crop.SimpleCrop.Poppy -> crop(id = 28)
    Crop.SimpleCrop.SummerSpangle -> crop(id = 29)
    Crop.SimpleCrop.Sunflower -> crop(id = 30)
    Crop.SimpleCrop.FairyRose -> crop(id = 31)
    Crop.SimpleCrop.SweetGemBerry -> crop(id = 32)
    Crop.SimpleCrop.Rice -> crop(id = 34)
    Crop.SimpleCrop.Strawberry -> crop(id = 36)
    Crop.SimpleCrop.Hops -> crop(id = 37)
    Crop.SimpleCrop.Grape -> crop(id = 38)
    Crop.SimpleCrop.Amaranth -> crop(id = 39)
    Crop.SimpleCrop.CoffeeBean -> crop(id = 40)
    Crop.SimpleCrop.CactusFruit -> crop(id = 41)
    Crop.SimpleCrop.TaroRoot -> crop(id = 42)
    Crop.SimpleCrop.Pineapple -> crop(id = 43)
}

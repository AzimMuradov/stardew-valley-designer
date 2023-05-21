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

@file:Suppress("PackageDirectoryMismatch")

package io.stardewvalleydesigner.engine.entity

import io.stardewvalleydesigner.engine.entity.EntityWithoutFloorType.CropType
import io.stardewvalleydesigner.engine.geometry.Rect


sealed interface Crop : Entity<CropType> {

    enum class SimpleCrop : Crop {
        Parsnip,
        GreenBean,
        Cauliflower,
        Potato,
        Garlic,
        Kale,
        Rhubarb,
        Melon,
        Tomato,
        Blueberry,
        HotPepper,
        Wheat,
        Radish,
        RedCabbage,
        Starfruit,
        Corn,
        Eggplant,
        Artichoke,
        Pumpkin,
        BokChoy,
        Yam,
        Cranberries,
        Beet,
        AncientFruit,
        Tulip,
        BlueJazz,
        Poppy,
        SummerSpangle,
        Sunflower,
        FairyRose,
        SweetGemBerry,
        Rice,
        Strawberry,
        Hops,
        Grape,
        Amaranth,
        CoffeeBean,
        CactusFruit,
        TaroRoot,
        Pineapple,
    }


    override val type: CropType get() = CropType

    override val size: Rect get() = RectsProvider.rectOf(1, 1)


    companion object {

        val all by lazy {
            buildSet {
                addAll(SimpleCrop.values())
            }
        }
    }
}

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

package io.stardewvalleydesigner.data.internal

import io.stardewvalleydesigner.data.*
import io.stardewvalleydesigner.data.SpritePage.Companion.UNIT
import io.stardewvalleydesigner.data.SpritePage.Crops
import io.stardewvalleydesigner.engine.entity.Crop
import io.stardewvalleydesigner.engine.entity.Crop.SimpleCrop.*
import io.stardewvalleydesigner.engine.entity.Entity


internal fun crop(qe: QualifiedEntity<Crop>): QualifiedEntityData {
    val entity = qe.entity

    fun crop(id: Int) = crop(entity, id)

    return when (entity) {
        Parsnip -> crop(id = 0)
        GreenBean -> crop(id = 1)
        Cauliflower -> crop(id = 2)
        Potato -> crop(id = 3)
        Garlic -> crop(id = 4)
        Kale -> crop(id = 5)
        Rhubarb -> crop(id = 6)
        Melon -> crop(id = 7)
        Tomato -> crop(id = 8)
        Blueberry -> crop(id = 9)
        HotPepper -> crop(id = 10)
        Wheat -> crop(id = 11)
        Radish -> crop(id = 12)
        RedCabbage -> crop(id = 13)
        Starfruit -> crop(id = 14)
        Corn -> crop(id = 15)
        Eggplant -> crop(id = 16)
        Artichoke -> crop(id = 17)
        Pumpkin -> crop(id = 18)
        BokChoy -> crop(id = 19)
        Yam -> crop(id = 20)
        Cranberries -> crop(id = 21)
        Beet -> crop(id = 22)
        AncientFruit -> crop(id = 24)
        Tulip -> crop(id = 26)
        BlueJazz -> crop(id = 27)
        Poppy -> crop(id = 28)
        SummerSpangle -> crop(id = 29)
        Sunflower -> crop(id = 30)
        FairyRose -> crop(id = 31)
        SweetGemBerry -> crop(id = 32)
        Rice -> crop(id = 34)
        Strawberry -> crop(id = 36)
        Hops -> crop(id = 37)
        Grape -> crop(id = 38)
        Amaranth -> crop(id = 39)
        CoffeeBean -> crop(id = 40)
        CactusFruit -> crop(id = 41)
        TaroRoot -> crop(id = 42)
        Pineapple -> crop(id = 43)
    }
}


private fun crop(
    entity: Entity<*>,
    id: Int,
) = QualifiedEntityData(
    qualifiedEntity = QualifiedEntity(entity),
    entityId = EntityId(EntityPage.Crops, id),
    spriteId = SpriteId.RegularSprite(
        page = Crops,
        offset = SpriteOffset(
            x = id % (Crops.width / UNIT) * Crops.grain.w,
            y = id / (Crops.width / UNIT) * Crops.grain.h,
        ) * UNIT,
        size = SpriteSize(w = 1, h = 2) * UNIT,
    )
)

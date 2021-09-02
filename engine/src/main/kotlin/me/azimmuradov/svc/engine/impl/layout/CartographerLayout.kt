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

package me.azimmuradov.svc.engine.impl.layout

import me.azimmuradov.svc.engine.Rect
import me.azimmuradov.svc.engine.RectMap
import me.azimmuradov.svc.engine.impl.entity.EntityWithoutFloorType
import me.azimmuradov.svc.engine.impl.entity.FloorFurnitureType
import me.azimmuradov.svc.engine.impl.entity.FloorType
import me.azimmuradov.svc.engine.impl.entity.ObjectType
import me.azimmuradov.svc.engine.impl.layout.layouts.BigShed
import me.azimmuradov.svc.engine.impl.layout.layouts.Shed
import me.azimmuradov.svc.engine.impl.rectmap.rectMapOf


data class CartographerLayout(
    val type: CartographerLayoutType,
    val size: Rect,
    val rulesForFlooringLayer: RectMap<Set<FloorType>?> = rectMapOf(size),
    val rulesForFloorFurnitureLayer: RectMap<Set<FloorFurnitureType>?> = rectMapOf(size),
    val rulesForObjectsLayer: RectMap<Set<ObjectType>?> = rectMapOf(size),
    val rulesForBigEntitiesLayer: RectMap<Set<EntityWithoutFloorType>?> = rectMapOf(size),
) {

    init {
        val listOfRules = listOf(
            rulesForFlooringLayer,
            rulesForFloorFurnitureLayer,
            rulesForObjectsLayer,
            rulesForBigEntitiesLayer,
        )

        require(listOfRules.all { it.rect == size }) { "Wrong `CartographerLayout` definition" }
    }
}


fun layout(type: CartographerLayoutType): CartographerLayout =
    when (type) {
        CartographerLayoutType.Shed -> Shed
        CartographerLayoutType.BigShed -> BigShed
    }
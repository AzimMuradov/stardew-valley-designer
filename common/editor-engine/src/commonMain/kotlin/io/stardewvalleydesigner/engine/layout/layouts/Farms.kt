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

package io.stardewvalleydesigner.engine.layout.layouts

import io.stardewvalleydesigner.engine.entity.*
import io.stardewvalleydesigner.engine.geometry.Rect
import io.stardewvalleydesigner.engine.geometry.rectOf
import io.stardewvalleydesigner.engine.layout.*
import io.stardewvalleydesigner.engine.layout.LayoutProviderUtils.withData
import io.stardewvalleydesigner.engine.layout.restrictions.*


internal val StandardFarmLayout: Layout by lazy {
    farmLayoutOf(
        type = LayoutType.StandardFarm,
        restrictions = StandardFarmRestrictions.restrictions,
    )
}

internal val RiverlandFarmLayout: Layout by lazy {
    farmLayoutOf(
        type = LayoutType.RiverlandFarm,
        restrictions = RiverlandFarmRestrictions.restrictions,
    )
}

internal val ForestFarmLayout: Layout by lazy {
    farmLayoutOf(
        type = LayoutType.ForestFarm,
        restrictions = ForestFarmRestrictions.restrictions,
    )
}

internal val HillTopFarmLayout: Layout by lazy {
    farmLayoutOf(
        type = LayoutType.HillTopFarm,
        restrictions = HillTopFarmRestrictions.restrictions,
    )
}

internal val WildernessFarmLayout: Layout by lazy {
    farmLayoutOf(
        type = LayoutType.WildernessFarm,
        restrictions = WildernessFarmRestrictions.restrictions,
    )
}

internal val FourCornersFarmLayout: Layout by lazy {
    farmLayoutOf(
        type = LayoutType.FourCornersFarm,
        restrictions = FourCornersFarmRestrictions.restrictions,
        size = rectOf(w = 80, h = 80),
    )
}


private fun farmLayoutOf(
    type: LayoutType,
    restrictions: FarmLayoutRestrictions,
    size: Rect = rectOf(w = 80, h = 65),
) = Layout(
    type,
    size,
    disallowedTypes = setOf(
        FloorFurnitureType,
        ObjectType.FurnitureType.HouseFurnitureType,
        ObjectType.FurnitureType.IndoorFurnitureType,
    ),
    disallowedTypesMap = buildMap {
        val canPlaceFloorObjectAndBuildings = setOf(EntityWithoutFloorType.CropType)
        val canPlaceFloorAndObject = canPlaceFloorObjectAndBuildings + EntityWithoutFloorType.BuildingType
        val canPlaceFloor = canPlaceFloorAndObject + ObjectType.all

        putAll(restrictions.canPlaceFloorOrObjectOrBuilding withData canPlaceFloorObjectAndBuildings)
        putAll(restrictions.canPlaceFloorOrObject withData canPlaceFloorAndObject)
        putAll(restrictions.canPlaceFloor withData canPlaceFloor)
    },
    disallowedCoordinates = restrictions.canPlaceNothing.toSet(),
)

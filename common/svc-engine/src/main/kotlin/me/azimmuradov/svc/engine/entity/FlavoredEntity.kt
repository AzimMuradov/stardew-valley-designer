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

package me.azimmuradov.svc.engine.entity

import me.azimmuradov.svc.engine.entity.Colors.ChestColors
import me.azimmuradov.svc.engine.entity.Colors.FishPondColors
import me.azimmuradov.svc.engine.entity.EntityWithoutFloorType.BuildingType
import me.azimmuradov.svc.engine.entity.ObjectType.EquipmentType
import me.azimmuradov.svc.engine.entity.Rotations.Rotations2
import me.azimmuradov.svc.engine.entity.Rotations.Rotations4
import me.azimmuradov.svc.engine.geometry.Rect


sealed interface FlavoredEntity


sealed class Rotatable private constructor(
    private val regularSize: Rect,
    private val rotatedSize: Rect,
) : FlavoredEntity {

    abstract val rotation: Rotations

    val size: Rect get() = if (rotation.ordinal % 2 == 0) regularSize else rotatedSize


    sealed class Rotatable2(
        regularSize: Rect,
        rotatedSize: Rect,
    ) : Rotatable(regularSize, rotatedSize) {

        abstract override val rotation: Rotations2
    }

    sealed class Rotatable4(
        regularSize: Rect,
        rotatedSize: Rect,
    ) : Rotatable(regularSize, rotatedSize) {

        abstract override val rotation: Rotations4
    }
}


sealed class TripleColoredFarmBuilding : FlavoredEntity {

    abstract val farmBuildingColors: FarmBuildingColors
}


sealed class Colored<out EType : EntityType> private constructor() : FlavoredEntity {

    abstract val color: Colors


    sealed class ColoredFishPond : Colored<BuildingType>() {

        abstract override val color: FishPondColors
    }

    sealed class ColoredChest : Colored<EquipmentType>() {

        abstract override val color: ChestColors
    }
}

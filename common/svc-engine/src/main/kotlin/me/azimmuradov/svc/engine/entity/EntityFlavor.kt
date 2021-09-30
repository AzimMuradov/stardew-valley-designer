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

package me.azimmuradov.svc.engine.entity

import me.azimmuradov.svc.engine.entity.ColoredFlavor.Colors.ChestColors
import me.azimmuradov.svc.engine.entity.ColoredFlavor.Colors.FishPondColors
import me.azimmuradov.svc.engine.entity.EntityWithoutFloorType.BuildingType
import me.azimmuradov.svc.engine.entity.ObjectType.EquipmentType
import me.azimmuradov.svc.engine.entity.RotatableFlavor.Rotations.Rotations2
import me.azimmuradov.svc.engine.entity.RotatableFlavor.Rotations.Rotations4
import me.azimmuradov.svc.engine.rectmap.Rect


sealed interface EntityFlavor


sealed class RotatableFlavor private constructor(
    private val regularSize: Rect,
    private val rotatedSize: Rect,
) : EntityFlavor {

    abstract val rotation: Rotations

    val size: Rect get() = if (rotation.ordinal % 2 == 0) regularSize else rotatedSize


    sealed class RotatableFlavor2(
        regularSize: Rect,
        rotatedSize: Rect,
    ) : RotatableFlavor(regularSize, rotatedSize) {

        abstract override val rotation: Rotations2
    }

    sealed class RotatableFlavor4(
        regularSize: Rect,
        rotatedSize: Rect,
    ) : RotatableFlavor(regularSize, rotatedSize) {

        abstract override val rotation: Rotations4
    }

    sealed interface Rotations {

        val ordinal: Int


        enum class Rotations2 : Rotations { R0, R1 }

        enum class Rotations4 : Rotations { R0, R1, R2, R3 }
    }
}


sealed class ColoredFarmBuildingFlavor : EntityFlavor {
    abstract val building: Color?
    abstract val roof: Color?
    abstract val trim: Color?
}

data class Color(val r: UByte, val g: UByte, val b: UByte)


sealed class ColoredFlavor<out EType : EntityType> private constructor() : EntityFlavor {

    abstract val color: Colors


    sealed class ColoredFishPondFlavor : ColoredFlavor<BuildingType>() {

        abstract override val color: FishPondColors
    }

    sealed class ColoredChestFlavor : ColoredFlavor<EquipmentType>() {

        abstract override val color: ChestColors
    }

    sealed interface Colors {

        enum class FishPondColors : Colors {
            Default,
            LavaEel,
            SuperCucumber,
            Slimejack,
            VoidSalmon,
        }

        enum class ChestColors : Colors {
            Default,
            Blue,
            LightBlue,
            Teal,
            Aqua,
            Green,
            LimeGreen,
            Yellow,
            LightOrange,
            Orange,
            Red,
            DarkRed,
            LightPink,
            Pink,
            Magenta,
            Purple,
            DarkPurple,
            DarkGrey,
            MediumGrey,
            LightGrey,
            White,
        }
    }
}
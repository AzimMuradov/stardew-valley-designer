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

package me.azimmuradov.svc.engine.llsvc.entity.ids

import me.azimmuradov.svc.engine.Rect
import me.azimmuradov.svc.engine.llsvc.Color
import me.azimmuradov.svc.engine.llsvc.entity.EntityWithoutFloorType.BuildingType
import me.azimmuradov.svc.engine.llsvc.entity.ObjectType.EquipmentType
import me.azimmuradov.svc.engine.llsvc.entity.SvcEntityType
import me.azimmuradov.svc.engine.llsvc.entity.ids.ColoredFlavor.Colors.ChestColors
import me.azimmuradov.svc.engine.llsvc.entity.ids.ColoredFlavor.Colors.FishPondColors
import me.azimmuradov.svc.engine.llsvc.entity.ids.RotatableFlavor.Rotations.Rotations2
import me.azimmuradov.svc.engine.llsvc.entity.ids.RotatableFlavor.Rotations.Rotations4


sealed interface SvcEntityIdFlavor


sealed class RotatableFlavor private constructor(
    private val regularSize: Rect,
    private val rotatedSize: Rect,
) : SvcEntityIdFlavor {

    abstract val rotation: Rotations

    val size: Rect get() = if (rotation.ordinal % 2 == 0) regularSize else rotatedSize


    sealed class RotatableFlavor2(
        regularSize: Rect,
        rotatedSize: Rect,
    ) : RotatableFlavor(regularSize, rotatedSize) {

        abstract override var rotation: Rotations2
    }

    sealed class RotatableFlavor4(
        regularSize: Rect,
        rotatedSize: Rect,
    ) : RotatableFlavor(regularSize, rotatedSize) {

        abstract override var rotation: Rotations4
    }

    sealed interface Rotations {

        val ordinal: Int


        enum class Rotations2 : Rotations { R0, R1 }

        enum class Rotations4 : Rotations { R0, R1, R2, R3 }
    }
}

sealed class ColoredFarmBuildingFlavor : SvcEntityIdFlavor {
    abstract var building: Color?
    abstract var roof: Color?
    abstract var trim: Color?
}

sealed class ColoredFlavor<out EType : SvcEntityType> private constructor() : SvcEntityIdFlavor {

    abstract val color: Colors


    sealed class ColoredFishPondFlavor : ColoredFlavor<BuildingType>() {

        abstract override var color: FishPondColors
    }

    sealed class ColoredChestFlavor : ColoredFlavor<EquipmentType>() {

        abstract override var color: ChestColors
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
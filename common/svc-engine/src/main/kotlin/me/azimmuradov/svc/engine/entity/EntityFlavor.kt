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


sealed interface EntityFlavor


sealed interface Rotations : EntityFlavor {

    val ordinal: Int


    enum class Rotations2 : Rotations { R1, R2 }

    enum class Rotations4 : Rotations { R1, R2, R3, R4 }
}


data class FarmBuildingColors(
    val building: Color? = null,
    val roof: Color? = null,
    val trim: Color? = null,
) : EntityFlavor

data class Color(val r: UByte, val g: UByte, val b: UByte)


sealed interface Colors : EntityFlavor {

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

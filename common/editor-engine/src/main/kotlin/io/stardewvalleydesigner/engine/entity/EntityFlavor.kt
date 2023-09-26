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

package io.stardewvalleydesigner.engine.entity


sealed interface EntityFlavor {

    fun default(): EntityFlavor
}


sealed interface Rotations : EntityFlavor {

    val ordinal: Int


    enum class Rotations2 : Rotations {
        R1, R2;

        override fun default(): Rotations2 = R1
    }

    enum class Rotations4 : Rotations {
        R1, R2, R3, R4;

        override fun default(): Rotations4 = R1
    }
}


sealed interface Colors : EntityFlavor {

    val value: Color?


    enum class FishPondColors : Colors {
        Default,
        LavaEel,
        SuperCucumber,
        Slimejack,
        VoidSalmon;

        override val value: Color get() = TODO("Not yet implemented")

        override fun default(): FishPondColors = Default
    }

    enum class ChestColors(override val value: Color?) : Colors {
        Default(value = null),
        Blue(Color(r = 85u, g = 85u, b = 255u)),
        LightBlue(Color(r = 119u, g = 191u, b = 255u)),
        Teal(Color(r = 0u, g = 170u, b = 170u)),
        Aqua(Color(r = 0u, g = 234u, b = 175u)),
        Green(Color(r = 0u, g = 170u, b = 0u)),
        LimeGreen(Color(r = 159u, g = 236u, b = 0u)),
        Yellow(Color(r = 255u, g = 234u, b = 18u)),
        LightOrange(Color(r = 255u, g = 167u, b = 18u)),
        Orange(Color(r = 255u, g = 105u, b = 18u)),
        Red(Color(r = 255u, g = 0u, b = 0u)),
        DarkRed(Color(r = 135u, g = 0u, b = 35u)),
        LightPink(Color(r = 255u, g = 173u, b = 199u)),
        Pink(Color(r = 255u, g = 117u, b = 195u)),
        Magenta(Color(r = 172u, g = 0u, b = 198u)),
        Purple(Color(r = 143u, g = 0u, b = 255u)),
        DarkPurple(Color(r = 89u, g = 11u, b = 142u)),
        DarkGrey(Color(r = 64u, g = 64u, b = 64u)),
        MediumGrey(Color(r = 100u, g = 100u, b = 100u)),
        LightGrey(Color(r = 200u, g = 200u, b = 200u)),
        White(Color(r = 254u, g = 254u, b = 254u));

        override fun default(): ChestColors = Default
    }

    enum class TulipColors : Colors {
        Color1,
        Color2,
        Color3,
        Color4,
        Color5;

        override val value: Color get() = TODO("Not yet implemented")

        override fun default(): TulipColors = Color1
    }

    enum class SummerSpangleColors : Colors {
        Color1,
        Color2,
        Color3,
        Color4,
        Color5,
        Color6;

        override val value: Color get() = TODO("Not yet implemented")

        override fun default(): SummerSpangleColors = Color1
    }

    enum class FairyRoseColors : Colors {
        Color1,
        Color2,
        Color3,
        Color4,
        Color5,
        Color6;

        override val value: Color get() = TODO("Not yet implemented")

        override fun default(): FairyRoseColors = Color1
    }

    enum class BlueJazzColors : Colors {
        Color1,
        Color2,
        Color3,
        Color4,
        Color5,
        Color6;

        override val value: Color get() = TODO("Not yet implemented")

        override fun default(): BlueJazzColors = Color1
    }

    enum class PoppyColors : Colors {
        Color1,
        Color2,
        Color3;

        override val value: Color get() = TODO("Not yet implemented")

        override fun default(): PoppyColors = Color1
    }
}


data class FarmBuildingColors(
    val building: Color? = null,
    val roof: Color? = null,
    val trim: Color? = null,
) : EntityFlavor {

    override fun default(): FarmBuildingColors = FarmBuildingColors()
}


data class Color(val r: UByte, val g: UByte, val b: UByte)

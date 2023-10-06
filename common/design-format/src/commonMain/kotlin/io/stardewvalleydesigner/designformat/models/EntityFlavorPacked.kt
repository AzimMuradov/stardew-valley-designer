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

package io.stardewvalleydesigner.designformat.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
internal sealed class EntityFlavorPacked


@Serializable
@SerialName("rotation")
internal data class RotationFlavorModel(
    val rotations: Int,
    val currentRotation: Int,
) : EntityFlavorPacked()

@Serializable
internal sealed class ColorFlavorModel : EntityFlavorPacked() {

    internal abstract val color: ColorPacked?


    @Serializable
    @SerialName("fish-pond-color")
    internal data class FishPondColors(override val color: ColorPacked) : ColorFlavorModel()

    @Serializable
    @SerialName("chest-color")
    internal data class ChestColors(override val color: ColorPacked? = null) : ColorFlavorModel()

    @Serializable
    @SerialName("tulip-color")
    internal data class TulipColors(override val color: ColorPacked) : ColorFlavorModel()

    @Serializable
    @SerialName("summer-spangle-color")
    internal data class SummerSpangleColors(override val color: ColorPacked) : ColorFlavorModel()

    @Serializable
    @SerialName("fairy-rose-color")
    internal data class FairyRoseColors(override val color: ColorPacked) : ColorFlavorModel()

    @Serializable
    @SerialName("blue-jazz-color")
    internal data class BlueJazzColors(override val color: ColorPacked) : ColorFlavorModel()

    @Serializable
    @SerialName("poppy-color")
    internal data class PoppyColors(override val color: ColorPacked) : ColorFlavorModel()
}

@Serializable
@SerialName("farm-building-colors")
internal data class FarmBuildingColorsFlavorModel(
    val building: ColorPacked? = null,
    val roof: ColorPacked? = null,
    val trim: ColorPacked? = null,
) : EntityFlavorPacked()

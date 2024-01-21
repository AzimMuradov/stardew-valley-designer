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

package io.stardewvalleydesigner.designformat.mappers

import io.stardewvalleydesigner.designformat.models.*
import io.stardewvalleydesigner.engine.entity.*


internal fun EntityFlavor.toEntityFlavorModel() = when (this) {
    is Colors -> when (this) {
        is Colors.ChestColors -> ColorFlavorModel.ChestColors(value)
        is Colors.FishPondColors -> ColorFlavorModel.FishPondColors(value)
        is Colors.BlueJazzColors -> ColorFlavorModel.BlueJazzColors(value)
        is Colors.FairyRoseColors -> ColorFlavorModel.FairyRoseColors(value)
        is Colors.PoppyColors -> ColorFlavorModel.PoppyColors(value)
        is Colors.SummerSpangleColors -> ColorFlavorModel.SummerSpangleColors(value)
        is Colors.TulipColors -> ColorFlavorModel.TulipColors(value)
    }

    is FarmBuildingColors -> FarmBuildingColorsFlavorModel(building, roof, trim)

    is Rotations.Rotations2 -> RotationFlavorModel(
        rotations = 2,
        currentRotation = ordinal,
    )

    is Rotations.Rotations4 -> RotationFlavorModel(
        rotations = 4,
        currentRotation = ordinal,
    )
}

internal fun EntityFlavorPacked.toEntityFlavor() = when (this) {
    is ColorFlavorModel -> when (this) {
        is ColorFlavorModel.ChestColors -> colorFlavorOf<Colors.ChestColors>(color)
        is ColorFlavorModel.FishPondColors -> colorFlavorOf<Colors.FishPondColors>(color)
        is ColorFlavorModel.BlueJazzColors -> colorFlavorOf<Colors.BlueJazzColors>(color)
        is ColorFlavorModel.FairyRoseColors -> colorFlavorOf<Colors.FairyRoseColors>(color)
        is ColorFlavorModel.PoppyColors -> colorFlavorOf<Colors.PoppyColors>(color)
        is ColorFlavorModel.SummerSpangleColors -> colorFlavorOf<Colors.SummerSpangleColors>(color)
        is ColorFlavorModel.TulipColors -> colorFlavorOf<Colors.TulipColors>(color)
    }

    is FarmBuildingColorsFlavorModel -> FarmBuildingColors(building, roof, trim)

    is RotationFlavorModel -> when (rotations) {
        2 -> Rotations.Rotations2.entries[currentRotation]
        4 -> Rotations.Rotations4.entries[currentRotation]
        else -> error("")
    }
}

private inline fun <reified T> colorFlavorOf(value: Color?): T where T : Enum<T>, T : Colors =
    enumValues<T>().first { it.value == value }

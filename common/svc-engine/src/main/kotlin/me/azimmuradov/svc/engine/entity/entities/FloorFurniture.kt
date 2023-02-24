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

import me.azimmuradov.svc.engine.entity.RectsProvider.rectOf
import me.azimmuradov.svc.engine.entity.RectsProvider.rotated
import me.azimmuradov.svc.engine.entity.RotatableFlavor.RotatableFlavor2
import me.azimmuradov.svc.engine.entity.RotatableFlavor.Rotations.Rotations2
import me.azimmuradov.svc.engine.geometry.Rect


sealed interface FloorFurniture : Entity<FloorFurnitureType> {

    // Rugs

    enum class SimpleRug(override val size: Rect) : FloorFurniture {
        BurlapRug(rectOf(w = 2, h = 2)),
        WoodcutRug(rectOf(w = 2, h = 2)),
        LargeRedRug(rectOf(w = 4, h = 3)),
        MonsterRug(rectOf(w = 2, h = 2)),
        BlossomRug(rectOf(w = 6, h = 4)),
        LargeGreenRug(rectOf(w = 4, h = 3)),
        OldWorldRug(rectOf(w = 4, h = 3)),
        LargeCottageRug(rectOf(w = 4, h = 3)),
        OceanicRug(rectOf(w = 3, h = 2)),
        IcyRug(rectOf(w = 4, h = 3)),
        FunkyRug(rectOf(w = 5, h = 4)),
        ModernRug(rectOf(w = 5, h = 4)),
    }

    sealed class RotatableRug(regularSize: Rect = rectOf(w = 2, h = 3)) : FloorFurniture, RotatableFlavor2(
        regularSize,
        rotatedSize = regularSize.rotated(),
    ) {
        data class BambooMat(override var rotation: Rotations2) : RotatableRug(regularSize = rectOf(w = 1, h = 2))

        data class NauticalRug(override var rotation: Rotations2) : RotatableRug()
        data class DarkRug(override var rotation: Rotations2) : RotatableRug()
        data class RedRug(override var rotation: Rotations2) : RotatableRug()
        data class LightGreenRug(override var rotation: Rotations2) : RotatableRug()
        data class GreenCottageRug(override var rotation: Rotations2) : RotatableRug()
        data class RedCottageRug(override var rotation: Rotations2) : RotatableRug()
        data class MysticRug(override var rotation: Rotations2) : RotatableRug()
        data class BoneRug(override var rotation: Rotations2) : RotatableRug()
        data class SnowyRug(override var rotation: Rotations2) : RotatableRug()
        data class PirateRug(override var rotation: Rotations2) : RotatableRug()
        data class PatchworkRug(override var rotation: Rotations2) : RotatableRug()
        data class FruitSaladRug(override var rotation: Rotations2) : RotatableRug()
    }


    // Floor Dividers

    enum class FloorDivider : FloorFurniture {

        FloorDivider1L, FloorDivider1R,
        FloorDivider2L, FloorDivider2R,
        FloorDivider3L, FloorDivider3R,
        FloorDivider4L, FloorDivider4R,
        FloorDivider5L, FloorDivider5R,
        FloorDivider6L, FloorDivider6R,
        FloorDivider7L, FloorDivider7R,
        FloorDivider8L, FloorDivider8R,
        ;


        override val size: Rect = rectOf(w = 1, h = 1)
    }


    override val type: FloorFurnitureType get() = FloorFurnitureType
}

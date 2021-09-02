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

package me.azimmuradov.svc.engine.impl.entity.ids

import me.azimmuradov.svc.engine.Rect
import me.azimmuradov.svc.engine.impl.Rects.RECT_1x1
import me.azimmuradov.svc.engine.impl.Rects.RECT_1x2
import me.azimmuradov.svc.engine.impl.Rects.RECT_2x2
import me.azimmuradov.svc.engine.impl.Rects.RECT_2x3
import me.azimmuradov.svc.engine.impl.Rects.RECT_3x2
import me.azimmuradov.svc.engine.impl.entity.FloorFurnitureType
import me.azimmuradov.svc.engine.impl.entity.ids.RotatableFlavor.RotatableFlavor2
import me.azimmuradov.svc.engine.impl.entity.ids.RotatableFlavor.Rotations.Rotations2


sealed interface FloorFurniture : CartographerEntityId<FloorFurnitureType> {

    // Rugs

    enum class SimpleRug(override val size: Rect) : FloorFurniture {
        BurlapRug(size = RECT_2x2),
        WoodcutRug(size = RECT_2x2),
        LargeRedRug(size = Rect(4, 3)),
        MonsterRug(size = RECT_2x2),
        BlossomRug(size = Rect(6, 4)),
        LargeGreenRug(size = Rect(4, 3)),
        OldWorldRug(size = Rect(4, 3)),
        LargeCottageRug(size = Rect(4, 3)),
        OceanicRug(size = RECT_3x2),
        IcyRug(size = Rect(4, 3)),
        FunkyRug(size = Rect(5, 4)),
        ModernRug(size = Rect(5, 4)),
    }

    sealed class RotatableRug(regularSize: Rect = RECT_2x3) : FloorFurniture, RotatableFlavor2(
        regularSize,
        rotatedSize = regularSize.rotated(),
    ) {
        data class BambooMat(override var rotation: Rotations2) : RotatableRug(regularSize = RECT_1x2)

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


        override val type: FloorFurnitureType = FloorFurnitureType

        override val size: Rect = RECT_1x1
    }


    override val type: FloorFurnitureType get() = FloorFurnitureType
}
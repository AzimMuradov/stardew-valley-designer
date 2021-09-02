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
import me.azimmuradov.svc.engine.impl.Rects.RECT_2x3
import me.azimmuradov.svc.engine.impl.Rects.RECT_3x3
import me.azimmuradov.svc.engine.impl.entity.ObjectType.FurnitureType.HouseFurnitureType


enum class HouseFurniture(override val size: Rect) : CartographerEntityId<HouseFurnitureType> {

    // Beds

    ChildBed(size = RECT_2x3),
    SingleBed(size = RECT_2x3),
    DoubleBed(size = RECT_3x3),
    BirchDoubleBed(size = RECT_3x3),
    DeluxeRedDoubleBed(size = RECT_3x3),
    ExoticDoubleBed(size = RECT_3x3),
    FisherDoubleBed(size = RECT_3x3),
    ModernDoubleBed(size = RECT_3x3),
    PirateDoubleBed(size = RECT_3x3),
    StarryDoubleBed(size = RECT_3x3),
    StrawberryDoubleBed(size = RECT_3x3),
    TropicalBed(size = RECT_2x3),
    TropicalDoubleBed(size = RECT_3x3),
    WildDoubleBed(size = RECT_3x3),


    // Others

    MiniFridge(size = RECT_1x1), // TODO : Not a furniture
    ;


    override val type: HouseFurnitureType = HouseFurnitureType
}
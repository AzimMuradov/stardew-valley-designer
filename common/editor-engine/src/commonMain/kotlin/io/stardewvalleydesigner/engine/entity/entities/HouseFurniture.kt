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

@file:Suppress("PackageDirectoryMismatch")

package io.stardewvalleydesigner.engine.entity

import io.stardewvalleydesigner.engine.entity.ObjectType.FurnitureType.HouseFurnitureType
import io.stardewvalleydesigner.engine.entity.RectsProvider.rectOf
import io.stardewvalleydesigner.engine.geometry.Rect


enum class HouseFurniture(override val size: Rect) : Entity<HouseFurnitureType> {

    // Beds

    ChildBed(bedSize),
    SingleBed(bedSize),
    DoubleBed(doubleBedSize),
    BirchDoubleBed(doubleBedSize),
    DeluxeRedDoubleBed(doubleBedSize),
    ExoticDoubleBed(doubleBedSize),
    FisherDoubleBed(doubleBedSize),
    ModernDoubleBed(doubleBedSize),
    PirateDoubleBed(doubleBedSize),
    StarryDoubleBed(doubleBedSize),
    StrawberryDoubleBed(doubleBedSize),
    TropicalBed(bedSize),
    TropicalDoubleBed(doubleBedSize),
    WildDoubleBed(doubleBedSize),


    // Others

    MiniFridge(rectOf(w = 1, h = 1)), // TODO : Not a furniture
    ;


    override val type: HouseFurnitureType = HouseFurnitureType


    companion object {

        val all by lazy { HouseFurniture.entries.toSet() }
    }
}

private val bedSize = rectOf(w = 2, h = 3)
private val doubleBedSize = rectOf(w = 3, h = 3)

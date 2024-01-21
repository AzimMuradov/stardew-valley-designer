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

import io.stardewvalleydesigner.engine.entity.RectsProvider.rectOf
import io.stardewvalleydesigner.engine.geometry.Rect


enum class Floor : Entity<FloorType> {

    // Floors (Floors + Paths + Grass)

    WoodFloor,
    RusticPlankFloor,
    StrawFloor,
    WeatheredFloor,
    CrystalFloor,
    StoneFloor,
    StoneWalkwayFloor,
    BrickFloor,

    WoodPath,
    GravelPath,
    CobblestonePath,
    SteppingStonePath,
    CrystalPath,

    Grass,
    ;


    override val type: FloorType = FloorType

    override val size: Rect = rectOf(w = 1, h = 1)


    companion object {

        val all by lazy { Floor.entries.toSet() }
    }
}

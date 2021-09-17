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

package me.azimmuradov.svc.engine.entity.ids

import me.azimmuradov.svc.engine.entity.FloorType
import me.azimmuradov.svc.engine.entity.ids.RectsProvider.rectOf
import me.azimmuradov.svc.engine.rectmap.Rect


enum class Floor : EntityId<FloorType> {

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
}
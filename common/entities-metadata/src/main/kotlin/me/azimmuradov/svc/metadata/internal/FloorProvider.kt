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

package me.azimmuradov.svc.metadata.internal

import me.azimmuradov.svc.engine.entity.Floor
import me.azimmuradov.svc.engine.entity.Floor.*
import me.azimmuradov.svc.metadata.EntityMetadata


internal fun floor(entity: Floor): EntityMetadata = when (entity) {

    // Floors

    WoodFloor -> common(328)
    RusticPlankFloor -> common(840)
    StrawFloor -> common(401)
    WeatheredFloor -> common(331)
    CrystalFloor -> common(333)
    StoneFloor -> common(329)
    StoneWalkwayFloor -> common(841)
    BrickFloor -> common(293)


    // Paths

    WoodPath -> common(405)
    GravelPath -> common(407)
    CobblestonePath -> common(411)
    SteppingStonePath -> common(415)
    CrystalPath -> common(409)


    // Grass

    Grass -> common(297)
}

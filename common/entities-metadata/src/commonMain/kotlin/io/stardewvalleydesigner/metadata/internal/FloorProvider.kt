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

package io.stardewvalleydesigner.metadata.internal

import io.stardewvalleydesigner.engine.entity.Floor
import io.stardewvalleydesigner.engine.entity.Floor.*
import io.stardewvalleydesigner.metadata.EntityMetadata


internal fun floor(entity: Floor): EntityMetadata = when (entity) {

    // Floors

    WoodFloor -> flooring(0)
    RusticPlankFloor -> flooring(11)
    StrawFloor -> flooring(4)
    WeatheredFloor -> flooring(2)
    CrystalFloor -> flooring(3)
    StoneFloor -> flooring(1)
    StoneWalkwayFloor -> flooring(12)
    BrickFloor -> flooring(10)


    // Paths

    WoodPath -> flooring(6)
    GravelPath -> flooring(5)
    CobblestonePath -> flooring(8)
    SteppingStonePath -> flooring(9)
    CrystalPath -> flooring(7)


    // Grass

    Grass -> common(297)
}

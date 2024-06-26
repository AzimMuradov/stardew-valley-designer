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

package io.stardewvalleydesigner.engine.layers

import io.stardewvalleydesigner.engine.entity.*
import io.stardewvalleydesigner.engine.layer.Layer
import io.stardewvalleydesigner.engine.layer.LayerType


class Layers {

    @Suppress("UNCHECKED_CAST")
    fun <T : EntityType> layerBy(type: LayerType<T>): Layer<T> =
        all.getValue(type) as Layer<T>


    private val all = mapOf(
        LayerType.Floor to Layer<FloorType>(),
        LayerType.FloorFurniture to Layer<FloorFurnitureType>(),
        LayerType.Object to Layer<ObjectType>(),
        LayerType.EntityWithoutFloor to Layer<EntityWithoutFloorType>(),
    )
}

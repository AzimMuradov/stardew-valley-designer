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

@file:Suppress("UNCHECKED_CAST")

package io.stardewvalleydesigner.engine.layers

import io.stardewvalleydesigner.engine.entity.*
import io.stardewvalleydesigner.engine.layer.*
import io.stardewvalleydesigner.engine.layout.Layout


fun layersOf(layout: Layout): Layers = LayersImpl(MutableLayersImpl(layout))

fun mutableLayersOf(layout: Layout): MutableLayers = MutableLayersImpl(layout)


// Actual private implementations

private class LayersImpl(layers: Layers) : Layers by layers

private class MutableLayersImpl(override val layout: Layout) : MutableLayers {

    private val layersMap = mapOf(
        LayerType.Floor to mutableLayerOf<FloorType>(layout),
        LayerType.FloorFurniture to mutableLayerOf<FloorFurnitureType>(layout),
        LayerType.Object to mutableLayerOf<ObjectType>(layout),
        LayerType.EntityWithoutFloor to mutableLayerOf<EntityWithoutFloorType>(layout),
    )


    override val all = layersMap.toList()

    override fun <EType : EntityType> layerBy(type: LayerType<EType>) =
        layersMap.getValue(type) as MutableLayer<EType>
}

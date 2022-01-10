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

package me.azimmuradov.svc.engine.layers

import me.azimmuradov.svc.engine.entity.EntityType
import me.azimmuradov.svc.engine.layer.*
import me.azimmuradov.svc.engine.layout.Layout


interface Layers {

    val layout: Layout

    val all: List<Pair<LayerType<*>, Layer<*>>>

    fun <EType : EntityType> layerBy(type: LayerType<EType>): Layer<EType>
}


interface MutableLayers : Layers {

    override val all: List<Pair<LayerType<*>, MutableLayer<*>>>

    override fun <EType : EntityType> layerBy(type: LayerType<EType>): MutableLayer<EType>
}


val Layers.entities: LayeredEntitiesData get() = layeredEntitiesData { layerBy(it).objects }
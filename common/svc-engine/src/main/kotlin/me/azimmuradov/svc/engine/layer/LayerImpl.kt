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

package me.azimmuradov.svc.engine.layer

import me.azimmuradov.svc.engine.entity.*
import me.azimmuradov.svc.engine.layout.Layout
import me.azimmuradov.svc.engine.rectmap.MutableRectMap
import me.azimmuradov.svc.engine.rectmap.mutableRectMapOf


fun <EType : EntityType> layerOf(layout: Layout): Layer<EType> = LayerImpl(MutableLayerImpl(layout))

fun <EType : EntityType> mutableLayerOf(layout: Layout): MutableLayer<EType> = MutableLayerImpl(layout)


// Actual private implementations

private class LayerImpl<EType : EntityType>(layer: Layer<EType>) : Layer<EType> by layer

private class MutableLayerImpl<EType : EntityType> private constructor(
    layout: Layout,
    private val rectMap: MutableRectMap<Entity<EType>>,
) : MutableLayer<EType>, MutableRectMap<Entity<EType>> by rectMap {

    override val size = layout.size

    override val layoutRules = layout

    constructor(layout: Layout) : this(layout, mutableRectMapOf(layout.size))


    override fun put(obj: PlacedEntity<EType>): List<PlacedEntity<EType>> {
        val (entity, _, coordinates) = obj

        layoutRules.run {
            requireNotDisallowed(entity.type !in disallowedTypes)
            requireNotDisallowed(coordinates.mapNotNull(disallowedTypesMap::get).none { entity.type in it })
            requireNotDisallowed(coordinates.none(disallowedCoordinates::contains))
        }

        return rectMap.put(obj)
    }


    companion object {

        fun requireNotDisallowed(value: Boolean) = require(value) { TODO() }
    }
}
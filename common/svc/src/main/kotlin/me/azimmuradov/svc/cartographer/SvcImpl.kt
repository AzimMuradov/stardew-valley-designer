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

package me.azimmuradov.svc.cartographer

import me.azimmuradov.svc.cartographer.*
import me.azimmuradov.svc.cartographer.history.*
import me.azimmuradov.svc.cartographer.layers.LayersVisibility
import me.azimmuradov.svc.cartographer.palette.mutablePaletteOf
import me.azimmuradov.svc.cartographer.toolkit.Hand
import me.azimmuradov.svc.cartographer.toolkit.Toolkit
import me.azimmuradov.svc.engine.*
import me.azimmuradov.svc.engine.entity.Entity
import me.azimmuradov.svc.engine.entity.PlacedEntity
import me.azimmuradov.svc.engine.entity.ids.EntityId
import me.azimmuradov.svc.engine.layout.Layout
import me.azimmuradov.svc.engine.rectmap.Coordinate


fun svcOf(layout: Layout): Svc = SvcImpl(layout)


private class SvcImpl(override val layout: Layout) : Svc {

    override var behavior: SvcBehaviour = DefaultSvcBehaviour.rewriter


    val engine: SvcEngine = svcEngineOf(layout)

    override fun layers() = run {
        val visibleLayers = layersVisibility.visibleLayers()
        engine.layers().filter { (lType) -> lType in visibleLayers }
    }

    override fun ghostLayers() = ghostEntities

    var ghostEntities: List<PlacedEntity<*>> = listOf()


    override val history: ActsHistory = actsHistory()


    override var chosenEntities: List<PlacedEntity<*>> = listOf()


    override val toolkit: Toolkit = Toolkit(
        hand = Hand(
            actsRegisterer = history,
            startBlock = { c ->
                val resetEH: Boolean
                val es = if (c in chosenEntities.flatMapTo(mutableSetOf(), PlacedEntity<*>::coordinates)) {
                    chosenEntities = listOf()
                    resetEH = true
                    engine.removeAll(chosenEntities.mapTo(mutableSetOf(), PlacedEntity<*>::place)).values.flatten()
                } else {
                    resetEH = false
                    engine.remove(c).values.filterNotNull()
                }

                es.also { ghostEntities = it } to RevertibleAct(
                    act = {
                        engine.removeAll(es.mapTo(mutableSetOf(), PlacedEntity<*>::place))
                        if (resetEH) chosenEntities = listOf()
                    },
                    revertedAct = {
                        if (resetEH) chosenEntities = es
                        engine.putAll(es)
                    },
                )
            },
            keepBlock = { es -> ghostEntities = es },
            endBlock = { es ->
                ghostEntities = es

                RevertibleAct(
                    act = { engine.putAll(ghostEntities) },
                    revertedAct = { engine.removeAll(ghostEntities.mapTo(mutableSetOf(), PlacedEntity<*>::place)) },
                )
            },
        ),
    )

    override val palette = mutablePaletteOf(size = 10u)

    override val layersVisibility: LayersVisibility = LayersVisibility()


    override fun put(id: EntityId<*>, c: Coordinate) {
        engine.put(
            PlacedEntity(
                rectObj = Entity(obj = id, size = id.size),
                place = c,
            )
        )
    }
}
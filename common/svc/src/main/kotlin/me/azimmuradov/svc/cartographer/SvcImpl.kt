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

import androidx.compose.runtime.*
import me.azimmuradov.svc.cartographer.history.*
import me.azimmuradov.svc.cartographer.palette.mutablePaletteOf
import me.azimmuradov.svc.cartographer.toolkit.Toolkit
import me.azimmuradov.svc.cartographer.toolkit.tools.*
import me.azimmuradov.svc.engine.*
import me.azimmuradov.svc.engine.entity.PlacedEntity
import me.azimmuradov.svc.engine.layer.toLayerType
import me.azimmuradov.svc.engine.layout.Layout
import me.azimmuradov.svc.engine.rectmap.PlacedRectObject


fun svcOf(layout: Layout): Svc = SvcImpl(layout)


private class SvcImpl(override val layout: Layout) : Svc {

    // Backing svc engine

    val engine: SvcEngine = svcEngineOf(layout)


    // Views

    override val layers = engine.layers

    override var heldEntities by mutableStateOf(listOf<PlacedEntity<*>>())


    override val history: ActionHistory = actionHistory()


    override val palette = mutablePaletteOf(size = 10u)


    override val toolkit: Toolkit = Toolkit(
        hand = Hand(
            unitsRegisterer = history,
            onGrab = { c ->
                val esToMove = engine.remove(c).values.filterNotNull()
                heldEntities = esToMove
                heldEntities to HistoryUnit(
                    act = { engine.removeAll(esToMove) },
                    revert = { engine.putAll(esToMove) },
                )
            },
            onMove = { movedEs -> heldEntities = movedEs },
            onRelease = { movedEs ->
                heldEntities = listOf()
                val replacedEs = engine.putAll(movedEs)
                HistoryUnit(
                    act = { engine.putAll(movedEs) },
                    revert = {
                        engine.removeAll(movedEs)
                        engine.putAll(replacedEs.values.flatten())
                    },
                )
            },
        ),
        pen = Pen(
            unitsRegisterer = history,
            onDrawStart = { c ->
                val entityToPlace = palette.inUse?.let { entity -> PlacedRectObject(entity, place = c) }
                val isStartSuccessful = entityToPlace != null
                if (entityToPlace != null) {
                    engine.put(entityToPlace)
                }
                isStartSuccessful to entityToPlace
            },
            onDraw = { c, placedEsCs ->
                val entityToPlace = palette.inUse?.let { entity -> PlacedRectObject(entity, place = c) }
                if (entityToPlace != null && entityToPlace.coordinates.none { it in placedEsCs }) {
                    engine.put(entityToPlace)
                    entityToPlace
                } else {
                    null
                }
            },
            onDrawEnd = { placedEs ->
                HistoryUnit(
                    act = { engine.putAll(placedEs) },
                    revert = { engine.removeAll(placedEs) },
                )
            },
        ),
        eraser = Eraser(
            unitsRegisterer = history,
            onEraseStart = { c -> engine.remove(c).entities() },
            onErase = { c -> engine.remove(c).entities() },
            onEraseEnd = { removedEs ->
                HistoryUnit(
                    act = { engine.removeAll(removedEs) },
                    revert = { engine.putAll(removedEs) },
                )
            }
        ),
    )
}


private fun SvcEngine.removeAll(objs: Iterable<PlacedEntity<*>>) {
    for ((entity, place) in objs) {
        remove(entity.type.toLayerType(), place)
    }
}
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
import me.azimmuradov.svc.cartographer.engine.ObservableSvcEngine
import me.azimmuradov.svc.cartographer.history.*
import me.azimmuradov.svc.cartographer.palette.mutablePaletteOf
import me.azimmuradov.svc.cartographer.palette.putInUseOrClear
import me.azimmuradov.svc.cartographer.toolkit.*
import me.azimmuradov.svc.engine.*
import me.azimmuradov.svc.engine.entity.Entity
import me.azimmuradov.svc.engine.entity.PlacedEntity
import me.azimmuradov.svc.engine.layer.LayerType
import me.azimmuradov.svc.engine.layers.*
import me.azimmuradov.svc.engine.layout.Layout
import me.azimmuradov.svc.engine.layout.respects
import me.azimmuradov.svc.engine.rectmap.coordinates
import me.azimmuradov.svc.engine.rectmap.placeIt


fun svcOf(layout: Layout): Svc = SvcImpl(layout)


private class SvcImpl(override val layout: Layout) : Svc {

    val engine = ObservableSvcEngine(
        onLayersChanged = { layers -> entities = layers.entities },
        engine = svcEngineOf(layout),
    )


    // Views

    override var entities by mutableStateOf(LayeredEntities())

    override var heldEntities by mutableStateOf(LayeredEntities())

    override val palette = mutablePaletteOf(size = 10u)

    override var visibleLayers by mutableStateOf(LayerType.all.toSet())


    // Functions

    // History

    override val history = historyManager()

    // Toolkit

    val toolkit: Toolkit = Toolkit(
        hand = Hand(
            unitsRegisterer = history,
            onGrab = { c ->
                val esToMove = engine.remove(c).toLayeredEntities()
                heldEntities = esToMove
                heldEntities to HistoryUnit(
                    act = { engine.removeAll(esToMove.flatten()) },
                    revert = { engine.putAll(esToMove) },
                )
            },
            onMove = { movedEs -> heldEntities = movedEs },
            onRelease = { movedEs ->
                heldEntities = LayeredEntities()
                val replacedEs = engine.putAll(movedEs)
                HistoryUnit(
                    act = { engine.putAll(movedEs) },
                    revert = {
                        engine.removeAll(movedEs.flatten())
                        engine.putAll(replacedEs)
                    },
                )
            },
        ),
        pen = Pen(
            unitsRegisterer = history,
        ) {
            val placed = mutableListOf<PlacedEntity<*>>()
            val replaced = mutableListOf<PlacedEntity<*>>()

            Pen.Logic(
                onDrawStart = { c ->
                    val e = palette.inUse?.placeIt(there = c)
                    if (e != null && e respects layout) {
                        placed += e
                        replaced += engine.put(e).flatten()
                    }
                    e != null
                },
                onDraw = { c ->
                    val e = palette.inUse?.placeIt(there = c)
                    if (e != null && e respects layout && e.coordinates.none { it in placed.coordinates }) {
                        placed += e
                        replaced += engine.put(e).flatten()
                    }
                },
                onDrawEnd = {
                    if (placed.isNotEmpty()) {
                        HistoryUnit(
                            act = { engine.putAll(placed.layered()) },
                            revert = {
                                engine.removeAll(placed)
                                engine.putAll(replaced.layered())
                            },
                        )
                    } else {
                        null
                    }
                },
            )
        },
        eraser = Eraser(
            unitsRegisterer = history,
            onEraseStart = { c -> engine.remove(c) },
            onErase = { c -> engine.remove(c) },
            onEraseEnd = { removed ->
                HistoryUnit(
                    act = { engine.removeAll(removed.flatten()) },
                    revert = { engine.putAll(removed) },
                )
            }
        ),
        eyeDrawer = EyeDrawer(
            unitsRegisterer = history,
            onPickStart = { c ->
                val inUse = palette.inUse
                engine.get(c).flatten().lastOrNull()?.rectObject?.also(palette::putInUse) to inUse
            },
            onPick = { c ->
                engine.get(c).flatten().lastOrNull()?.rectObject?.also(palette::putInUse)
            },
            onPickEnd = { pickedEntity, previouslyInUse ->
                HistoryUnit(
                    act = { palette.putInUse(pickedEntity) },
                    revert = {
                        if (previouslyInUse != null) {
                            palette.putInUse(previouslyInUse)
                        } else {
                            palette.clearUsed()
                        }
                    },
                )
            }
        ),
    )

    override val tool get() = toolkit.tool

    override fun chooseToolOf(type: ToolType?) {
        val inUse = toolkit.tool?.type
        if (type != inUse) {
            toolkit.chooseToolOf(type)
            history += HistoryUnit(
                act = { toolkit.chooseToolOf(type) },
                revert = { toolkit.chooseToolOf(inUse) },
            )
        }
    }

    // Palette & Flavors

    override fun useInPalette(entity: Entity<*>) = putInUseOrClear(entity)

    override fun clearUsedInPalette() = putInUseOrClear(entity = null)

    private fun putInUseOrClear(entity: Entity<*>?) {
        val inUse = palette.inUse
        if (entity != inUse) {
            palette.putInUseOrClear(entity)
            history += HistoryUnit(
                act = { palette.putInUseOrClear(entity) },
                revert = { palette.putInUseOrClear(inUse) },
            )
        }
    }

    override fun changeVisibilityBy(layerType: LayerType<*>, value: Boolean) {
        visibleLayers = if (value) {
            visibleLayers + layerType
        } else {
            visibleLayers - layerType
        }
    }
}
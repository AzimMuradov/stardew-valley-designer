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

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import me.azimmuradov.svc.cartographer.modules.engine.*
import me.azimmuradov.svc.cartographer.modules.history.*
import me.azimmuradov.svc.cartographer.modules.palette.MutablePalette
import me.azimmuradov.svc.cartographer.modules.palette.putInUseOrClear
import me.azimmuradov.svc.cartographer.modules.toolkit.*
import me.azimmuradov.svc.cartographer.state.*
import me.azimmuradov.svc.cartographer.wishes.SvcWish
import me.azimmuradov.svc.engine.*
import me.azimmuradov.svc.engine.entity.Entity
import me.azimmuradov.svc.engine.entity.PlacedEntity
import me.azimmuradov.svc.engine.geometry.*
import me.azimmuradov.svc.engine.layer.LayerType
import me.azimmuradov.svc.engine.layers.*
import me.azimmuradov.svc.engine.layout.Layout
import me.azimmuradov.svc.engine.layout.respects
import me.azimmuradov.svc.engine.rectmap.coordinates
import me.azimmuradov.svc.engine.rectmap.placeIt
import mu.KotlinLogging


fun svcOf(layout: Layout): Svc = SvcImpl(layout)

val logger = KotlinLogging.logger(name = "SVC-LOGGER")


// TODO : chosen entities
// TODO : SVC behaviour

private class SvcImpl(layout: Layout) : Svc {

    // State Flow

    private val _state = MutableStateFlow(value = SvcState.default(layout.toState()))

    override val state: StateFlow<SvcState> get() = _state.asStateFlow()


    init {
        CoroutineScope(Dispatchers.Default).launch {
            state.collect {
                logger.debug("Svc State = $it")
            }
        }
    }


    // Consume Wishes

    override fun consume(wish: SvcWish) {
        logger.debug("Consume wish = $wish")

        when (wish) {
            // Top Menu
            SvcWish.History.GoBack -> history.goBackIfCan()
            SvcWish.History.GoForward -> history.goForwardIfCan()

            // Left-Side Menu
            is SvcWish.Tools.ChooseTool -> toolkit.chooseToolOf(wish.type)
            is SvcWish.Palette.AddToInUse -> {
                if (palette.putInUse(wish.entity) != wish.entity) {
                    history += state.value.toHistorySnapshot()
                }
            }

            // Right-Side Menu
            is SvcWish.VisibilityLayers.ChangeVisibility -> {
                val prev = state.value.editor.visibleLayers
                changeVisibilityBy(wish.layerType, wish.visible)
                val curr = state.value.editor.visibleLayers
                if (prev != curr) {
                    history += state.value.toHistorySnapshot()
                }
            }

            // EditorState
            is SvcWish.Act.Start -> toolkit.tool?.start(wish.coordinate)
            is SvcWish.Act.Continue -> toolkit.tool?.keep(wish.coordinate)
            SvcWish.Act.End -> toolkit.tool?.end()
        }
    }


    // Parts

    private val engine = ObservableSvcEngine(
        engine = svcEngineOf(layout),
        onLayersChanged = { layers ->
            _state.update { state ->
                state.copy(
                    editor = state.editor.copy(
                        entities = layers.toEntitiesState()
                    )
                )
            }
        }
    )

    private val history = ObservableHistoryManager(
        onHistoryChanged = { historyChange ->
            _state.update { state ->
                val history = HistoryState(historyChange.canGoBack, historyChange.canGoForward)
                when (historyChange) {
                    is HistoryChange.AfterRegistering -> state.copy(
                        history = history
                    )
                    is HistoryChange.AfterTraveling -> {
                        val snapshot = historyChange.currentSnapshot ?: HistorySnapshot.default(layout.toState())
                        updateFromHistorySnapshot(snapshot)
                        state.copy(
                            history = history,
                            toolkit = snapshot.toolkit,
                            palette = snapshot.palette,
                            editor = snapshot.editor
                        )
                    }
                }
            }
        }
    )

    private val palette = object : MutablePalette {

        override val inUse: Entity<*>? get() = state.value.palette.inUse

        override val hotbar: List<Entity<*>?> get() = state.value.palette.hotbar

        override val size: UInt = hotbar.size.toUInt()


        override fun putInUse(entity: Entity<*>): Entity<*>? = putInUseOrClear(entity)

        override fun clearUsed(): Entity<*>? = putInUseOrClear(entity = null)


        override fun putOnHotbar(index: Int, entity: Entity<*>): Entity<*>? =
            putOnHotbarOrClear(index, entity)

        override fun removeFromHotbar(index: Int): Entity<*>? =
            putOnHotbarOrClear(index, entity = null)

        override fun clearHotbar(): List<Entity<*>?> {
            val prev = state.value.palette.hotbar
            _state.update { state ->
                state.copy(
                    palette = state.palette.copy(
                        hotbar = state.palette.hotbar.map { null }
                    )
                )
            }
            return prev
        }


        private fun putInUseOrClear(entity: Entity<*>?): Entity<*>? {
            val prev = state.value.palette.inUse
            _state.update { state ->
                state.copy(
                    palette = state.palette.copy(
                        inUse = entity
                    )
                )
            }
            return prev
        }

        private fun putOnHotbarOrClear(index: Int, entity: Entity<*>?): Entity<*>? {
            if (index !in 0 until size.toInt()) return null

            val prev = state.value.palette.hotbar.getOrNull(index)
            _state.update { state ->
                state.copy(
                    palette = state.palette.copy(
                        hotbar = state.palette.hotbar.toMutableList().apply { set(index, entity) }
                    )
                )
            }
            return prev
        }
    }

    private fun changeVisibilityBy(layerType: LayerType<*>, value: Boolean) {
        _state.update { state ->
            state.copy(
                editor = state.editor.copy(
                    visibleLayers = if (value) {
                        state.editor.visibleLayers + layerType
                    } else {
                        state.editor.visibleLayers - layerType
                    }
                )
            )
        }
    }

    private val toolkit: Toolkit = Toolkit(
        hand = {
            var esToMoveCopy: LayeredEntities? = null

            Hand.Logic(
                onGrab = { c ->
                    val esToMove = engine.remove(c).toLayeredEntities()
                    if (esToMove.flatten().isNotEmpty()) {
                        esToMoveCopy = esToMove
                        _state.update { state ->
                            state.copy(
                                editor = state.editor.copy(
                                    heldEntities = esToMove.all
                                )
                            )
                        }
                    }
                    esToMove
                },
                onMove = { movingEs ->
                    _state.update { state ->
                        state.copy(
                            editor = state.editor.copy(
                                heldEntities = movingEs.all
                            )
                        )
                    }
                },
                onRelease = { movedEs ->
                    _state.update { state ->
                        state.copy(
                            editor = state.editor.copy(
                                heldEntities = emptyList()
                            )
                        )
                    }
                    if (movedEs.flatten().all { it respects layout }) {
                        if (movedEs.flatten().isNotEmpty()) {
                            engine.putAll(movedEs)
                            history.register(snapshot = state.value.toHistorySnapshot())
                        }
                    } else {
                        esToMoveCopy?.let { engine.putAll(it) }
                    }
                }
            )
        },
        pen = {
            val placed = mutableListOf<PlacedEntity<*>>()

            Pen.Logic(
                onDrawStart = { c ->
                    val e = palette.inUse?.placeIt(there = c)
                    if (e != null && e respects layout) {
                        placed += e
                        engine.put(e)
                    }
                    e != null
                },
                onDraw = { c ->
                    val e = palette.inUse?.placeIt(there = c)
                    if (e != null && e respects layout && e.coordinates notOverlapsWith placed.coordinates) {
                        placed += e
                        engine.put(e)
                    }
                },
                onDrawEnd = {
                    if (placed.isNotEmpty()) {
                        history.register(snapshot = state.value.toHistorySnapshot())
                    }
                },
            )
        },
        eraser = {
            val removed = mutableListOf<PlacedEntity<*>>()

            Eraser.Logic(
                onEraseStart = { c ->
                    removed += engine.remove(c).flatten()
                },
                onErase = { c ->
                    removed += engine.remove(c).flatten()
                },
                onEraseEnd = {
                    if (removed.isNotEmpty()) {
                        history.register(snapshot = state.value.toHistorySnapshot())
                    }
                },
            )
        },
        eyeDropper = {
            var prevInUse: Entity<*>? = null

            EyeDropper.Logic(
                onDropperStart = { c ->
                    prevInUse = palette.putInUseOrClear(entity = engine.get(c).topmost()?.rectObject)
                },
                onDropperKeep = { c ->
                    palette.putInUseOrClear(entity = engine.get(c).topmost()?.rectObject)
                },
                onDropperEnd = {
                    if (state.value.palette.inUse != prevInUse) {
                        history.register(snapshot = state.value.toHistorySnapshot())
                    }
                },
            )
        },
        rectSelect = {
            var start: Coordinate = Coordinate.ZERO
            var end: Coordinate = Coordinate.ZERO

            RectSelect.Logic(
                onSelectStart = { c ->
                    start = c
                    end = c
                    _state.update { state ->
                        val cs = PlacedRect.fromTwoCoordinates(start, end).coordinates
                        state.copy(
                            editor = state.editor.copy(
                                chosenEntities = engine.getAll(cs).all
                            )
                        )
                    }
                },
                onSelect = { c ->
                    end = c
                    _state.update { state ->
                        val cs = PlacedRect.fromTwoCoordinates(start, end).coordinates
                        state.copy(
                            editor = state.editor.copy(
                                chosenEntities = engine.getAll(cs).all
                            )
                        )
                    }
                },
                onSelectEnd = {
                    val cs = PlacedRect.fromTwoCoordinates(start, end).coordinates
                    if (engine.getAll(cs).flatten().isNotEmpty()) {
                        history.register(snapshot = state.value.toHistorySnapshot())
                    }
                },
            )
        },
        onToolChosen = { toolType ->
            _state.update { state ->
                if (state.toolkit.currentToolType != toolType) {
                    history.register(
                        history.currentSnapshotOrDefault(layout.toState()).copy(
                            toolkit = ToolkitState(
                                currentToolType = toolType
                            )
                        )
                    )
                    state.copy(
                        toolkit = ToolkitState(
                            currentToolType = toolType
                        )
                    )
                } else {
                    state
                }
            }
        }
    )


    private fun updateFromHistorySnapshot(snapshot: HistorySnapshot) {
        engine.update(snapshot.editor.entities)
        toolkit.update(snapshot.toolkit.currentToolType)
    }
}
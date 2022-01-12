/*
 * Copyright 2021-2022 Azim Muradov
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
import me.azimmuradov.svc.cartographer.modules.engine.ObservableSvcEngine
import me.azimmuradov.svc.cartographer.modules.engine.toState
import me.azimmuradov.svc.cartographer.modules.history.*
import me.azimmuradov.svc.cartographer.modules.palette.MutablePalette
import me.azimmuradov.svc.cartographer.modules.palette.putInUseOrClear
import me.azimmuradov.svc.cartographer.modules.toolkit.ShapeType
import me.azimmuradov.svc.cartographer.modules.toolkit.ToolType
import me.azimmuradov.svc.cartographer.state.*
import me.azimmuradov.svc.cartographer.wishes.SvcWish
import me.azimmuradov.svc.engine.*
import me.azimmuradov.svc.engine.entity.Entity
import me.azimmuradov.svc.engine.entity.PlacedEntity
import me.azimmuradov.svc.engine.geometry.*
import me.azimmuradov.svc.engine.layer.LayerType
import me.azimmuradov.svc.engine.layers.*
import me.azimmuradov.svc.engine.layout.*
import me.azimmuradov.svc.engine.rectmap.*
import mu.KotlinLogging


fun svcOf(layout: Layout): Svc = SvcImpl(layout)

val logger = KotlinLogging.logger(name = "SVC-LOGGER")


// TODO : SVC behaviour

private class SvcImpl(private val layout: Layout) : Svc {

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
            is SvcWish.Tools.ChooseTool -> {
                chooseTool(state.value.toolkit, wish.type)
                history += state.value.toHistorySnapshot()
            }
            is SvcWish.Tools.ChooseShape -> {
                chooseShape(state.value.toolkit, wish.type)
                history += state.value.toHistorySnapshot()
            }
            is SvcWish.Palette.AddToInUse -> {
                if (palette.putInUse(wish.entity) != wish.entity) {
                    // TODO : toolkit.chooseToolOf(ToolType.Pen)
                    history += state.value.toHistorySnapshot()
                }
            }

            // Right-Side Menu
            is SvcWish.VisibilityLayers.ChangeVisibility -> {
                changeVisibilityBy(wish.layerType, wish.visible)
                history += state.value.toHistorySnapshot()
            }

            // EditorState
            is SvcWish.Act.Start -> toolStart(state.value.toolkit, wish.coordinate)
            is SvcWish.Act.Continue -> toolKeep(state.value.toolkit, wish.coordinate)
            SvcWish.Act.End -> toolEnd(state.value.toolkit)
        }
    }


    // Parts

    private val engine = ObservableSvcEngine(
        engine = svcEngineOf(layout),
        onLayersChanged = { layers ->
            _state.update { state ->
                state.copy(
                    editor = state.editor.copy(
                        entities = layers.entities
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


    // Toolkit

    private fun chooseTool(toolkit: ToolkitState, tool: ToolType?) {
        fun idle(
            shape: ShapeType? = null,
            selectedEntities: LayeredEntitiesData = LayeredEntitiesData(),
        ): ToolkitState = ToolkitState.idle(tool, shape, selectedEntities)

        if (toolkit.isIdle) {
            _state.update { state ->
                when (toolkit) {
                    ToolkitState.None, is ToolkitState.Pen, is ToolkitState.Eraser, is ToolkitState.EyeDropper -> {
                        state.copy(toolkit = idle(toolkit.shape))
                    }
                    is ToolkitState.Hand -> {
                        state.copy(toolkit = idle(toolkit.shape, toolkit.selectedEntities))
                    }
                    is ToolkitState.Select -> {
                        state.copy(toolkit = idle(toolkit.shape, toolkit.selectedEntities))
                    }
                }
            }
        }
    }

    private fun chooseShape(toolkit: ToolkitState, shape: ShapeType?) {
        fun idle(
            tool: ToolType? = null,
            selectedEntities: LayeredEntitiesData = LayeredEntitiesData(),
        ): ToolkitState = ToolkitState.idle(tool, shape, selectedEntities)

        if (toolkit.isIdle) {
            _state.update { state ->
                when (toolkit) {
                    ToolkitState.None, is ToolkitState.Pen, is ToolkitState.Eraser, is ToolkitState.EyeDropper -> {
                        state.copy(toolkit = idle(toolkit.tool))
                    }
                    is ToolkitState.Hand -> {
                        state.copy(toolkit = idle(toolkit.tool, toolkit.selectedEntities))
                    }
                    is ToolkitState.Select -> {
                        state.copy(toolkit = idle(toolkit.tool, toolkit.selectedEntities))
                    }
                }
            }
        }
    }


    // TODO : Move to state
    private val placedCoordinates = mutableSetOf<Coordinate>()

    // TODO : Change(initMovedEs, start, isSelected)
    private var handData = Triple(LayeredEntitiesData(), Coordinate.ZERO, false)

    private fun toolStart(toolkit: ToolkitState, c: Coordinate) {
        if (toolkit.isIdle && toolkit != ToolkitState.None) {
            when (toolkit) {
                is ToolkitState.Hand.Point.Idle -> {
                    handData = Triple(LayeredEntitiesData(), Coordinate.ZERO, false)

                    val flattenSelectedEntities = toolkit.selectedEntities.flatten()
                    val esToMove = if (c in flattenSelectedEntities.coordinates) {
                        engine.removeAll(flattenSelectedEntities).also {
                            handData = Triple(it, c, true)
                        }
                    } else {
                        engine.remove(c).run {
                            layeredEntitiesData { type ->
                                setOfNotNull(entityOrNullBy(type))
                            }
                        }.also {
                            handData = Triple(it, c, false)
                        }
                    }

                    if (esToMove.flatten().isNotEmpty()) {
                        _state.update { state ->
                            state.copy(
                                toolkit = ToolkitState.Hand.Point.Acting(
                                    selectedEntities = toolkit.selectedEntities,
                                    heldEntities = esToMove
                                )
                            )
                        }
                    }
                }

                ToolkitState.Pen.Point.Idle -> {
                    placedCoordinates.clear()

                    val e = palette.inUse?.placeIt(there = c)
                    if (e != null && e respects layout) {
                        engine.put(e)
                        placedCoordinates += e.coordinates
                    }

                    _state.update { it.copy(toolkit = ToolkitState.Pen.Point.Acting) }
                }
                is ToolkitState.Pen.Shape.Idle -> {
                    placedCoordinates.clear()

                    val inUse = palette.inUse
                    if (inUse != null) {
                        val placedShape = toolkit.shape.projectTo(CanonicalCorners(c, c))
                        val disallowedCoordinates = layout.disallowedTypesMap
                            .mapNotNullTo(mutableSetOf()) { (c, types) ->
                                c.takeIf { inUse.type in types }
                            } + layout.disallowedCoordinates
                        val entitiesToDraw = placedShape.coordinates.filter {
                            it !in disallowedCoordinates
                        }.mapTo(mutableSetOf(), inUse::placeIt)
                        _state.update { state ->
                            state.copy(
                                toolkit = ToolkitState.Pen.Shape.Acting(
                                    start = c,
                                    placedShape = placedShape,
                                    entitiesToDraw = entitiesToDraw,
                                    entitiesToDelete = engine
                                        .getReplacedBy(entitiesToDraw.toList().asDisjoint())
                                        .flatten()
                                        .coordinates
                                )
                            )
                        }
                    }
                }

                ToolkitState.Eraser.Point.Idle -> {
                    engine.remove(c)

                    _state.update { it.copy(toolkit = ToolkitState.Eraser.Point.Acting) }
                }
                is ToolkitState.Eraser.Shape.Idle -> {
                    val placedShape = toolkit.shape.projectTo(CanonicalCorners(c, c))

                    _state.update {
                        it.copy(
                            toolkit = ToolkitState.Eraser.Shape.Acting(
                                start = c,
                                placedShape = placedShape,
                                entitiesToDelete = engine
                                    .getAll(placedShape.coordinates)
                                    .flatten()
                                    .coordinates
                            )
                        )
                    }
                }

                ToolkitState.EyeDropper.Point.Idle -> {
                    palette.putInUseOrClear(entity = engine.get(c).topmost()?.rectObject)

                    _state.update { it.copy(toolkit = ToolkitState.EyeDropper.Point.Acting) }
                }

                is ToolkitState.Select.Shape.Idle -> {
                    val placedShape = toolkit.shape.projectTo(CanonicalCorners(c, c))

                    _state.update {
                        it.copy(
                            toolkit = ToolkitState.Select.Shape.Acting(
                                start = c,
                                placedShape = placedShape,
                                selectedEntities = engine.getAll(placedShape.coordinates)
                            )
                        )
                    }
                }

                else -> Unit
            }
        }
    }

    private fun toolKeep(toolkit: ToolkitState, c: Coordinate) {
        if (toolkit.isActing && toolkit != ToolkitState.None) {
            when (toolkit) {
                is ToolkitState.Hand.Point.Acting -> {
                    val (esToMove, start) = handData
                    val flattenMovedEntities = esToMove.flatten().map { (entity, place) ->
                        PlacedEntity(entity, place = place + (c - start))
                    }
                    val moved = flattenMovedEntities.layeredData()

                    _state.update { state ->
                        state.copy(
                            toolkit = ToolkitState.Hand.Point.Acting(
                                selectedEntities = toolkit.selectedEntities,
                                heldEntities = moved
                            )
                        )
                    }
                }

                ToolkitState.Pen.Point.Acting -> {
                    val e = palette.inUse?.placeIt(there = c)
                    if (
                        e != null &&
                        e respects layout &&
                        e.coordinates notOverlapsWith placedCoordinates
                    ) {
                        engine.put(e)
                        placedCoordinates += e.coordinates
                    }
                }
                is ToolkitState.Pen.Shape.Acting -> {
                    val inUse = palette.inUse
                    if (inUse != null) {
                        val placedShape = toolkit.shape!!.projectTo(
                            CanonicalCorners.fromTwoCoordinates(toolkit.start, c)
                        )
                        val disallowedCoordinates = layout.disallowedTypesMap
                            .mapNotNullTo(mutableSetOf()) { (c, types) ->
                                c.takeIf { inUse.type in types }
                            } + layout.disallowedCoordinates
                        val entitiesToDraw = placedShape.coordinates.filter {
                            it !in disallowedCoordinates
                        }.mapTo(mutableSetOf(), inUse::placeIt)
                        _state.update { state ->
                            state.copy(
                                toolkit = toolkit.copy(
                                    placedShape = placedShape,
                                    entitiesToDraw = entitiesToDraw,
                                    entitiesToDelete = engine
                                        .getReplacedBy(entitiesToDraw.toList().asDisjoint())
                                        .flatten()
                                        .coordinates
                                )
                            )
                        }
                    }
                }

                ToolkitState.Eraser.Point.Acting -> {
                    engine.remove(c)
                }
                is ToolkitState.Eraser.Shape.Acting -> {
                    val placedShape = toolkit.shape!!.projectTo(
                        CanonicalCorners.fromTwoCoordinates(toolkit.start, c)
                    )
                    _state.update { state ->
                        state.copy(
                            toolkit = toolkit.copy(
                                placedShape = placedShape,
                                entitiesToDelete = engine
                                    .getAll(placedShape.coordinates)
                                    .flatten()
                                    .coordinates
                            )
                        )
                    }
                }

                ToolkitState.EyeDropper.Point.Acting -> {
                    palette.putInUseOrClear(entity = engine.get(c).topmost()?.rectObject)
                }

                is ToolkitState.Select.Shape.Acting -> {
                    val placedShape = toolkit.shape!!.projectTo(
                        CanonicalCorners.fromTwoCoordinates(toolkit.start, c)
                    )
                    _state.update { state ->
                        state.copy(
                            toolkit = toolkit.copy(
                                placedShape = placedShape,
                                selectedEntities = engine.getAll(placedShape.coordinates)
                            )
                        )
                    }
                }

                else -> Unit
            }
        }
    }

    private fun toolEnd(toolkit: ToolkitState) {
        if (toolkit.isActing && toolkit != ToolkitState.None) {
            when (toolkit) {
                is ToolkitState.Hand.Point.Acting -> {
                    val (esToMove, _, isSelected) = handData
                    val flattenMovedEntities = toolkit.heldEntities.flatten()
                    val moved = toolkit.heldEntities

                    if (flattenMovedEntities.all { it respectsLayout layout }) {
                        engine.putAll(moved.toLayeredEntities())
                        _state.update { state ->
                            state.copy(
                                toolkit = ToolkitState.Hand.Point.Idle(
                                    selectedEntities = if (isSelected) moved else toolkit.selectedEntities,
                                )
                            )
                        }
                    } else {
                        engine.putAll(esToMove.toLayeredEntities())
                        _state.update { state ->
                            state.copy(
                                toolkit = ToolkitState.Hand.Point.Idle(
                                    selectedEntities = toolkit.selectedEntities,
                                )
                            )
                        }
                    }

                    history += state.value.toHistorySnapshot()
                }

                ToolkitState.Pen.Point.Acting -> {
                    _state.update { it.copy(toolkit = ToolkitState.Pen.Point.Idle) }
                    history += state.value.toHistorySnapshot()
                }
                is ToolkitState.Pen.Shape.Acting -> {
                    engine.putAll(toolkit.entitiesToDraw.toList().asDisjoint())
                    _state.update { it.copy(toolkit = ToolkitState.Pen.Shape.Idle(toolkit.shape!!)) }
                    history += state.value.toHistorySnapshot()
                }

                ToolkitState.Eraser.Point.Acting -> {
                    _state.update { it.copy(toolkit = ToolkitState.Eraser.Point.Idle) }
                    history += state.value.toHistorySnapshot()
                }
                is ToolkitState.Eraser.Shape.Acting -> {
                    engine.removeAll(toolkit.entitiesToDelete)
                    _state.update { it.copy(toolkit = ToolkitState.Eraser.Shape.Idle(toolkit.shape!!)) }
                    history += state.value.toHistorySnapshot()
                }

                ToolkitState.EyeDropper.Point.Acting -> {
                    _state.update { it.copy(toolkit = ToolkitState.EyeDropper.Point.Idle) }
                    history += state.value.toHistorySnapshot()
                }

                is ToolkitState.Select.Shape.Acting -> {
                    _state.update {
                        it.copy(
                            toolkit = ToolkitState.Select.Shape.Idle(
                                shape = toolkit.shape!!,
                                selectedEntities = toolkit.selectedEntities
                            )
                        )
                    }
                    history += state.value.toHistorySnapshot()
                }

                else -> Unit
            }
        }
    }


    private fun updateFromHistorySnapshot(snapshot: HistorySnapshot) {
        engine.update(snapshot.editor.entities.toLayeredEntities())
    }
}
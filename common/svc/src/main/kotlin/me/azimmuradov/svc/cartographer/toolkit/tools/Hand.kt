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

package me.azimmuradov.svc.cartographer.toolkit

import me.azimmuradov.svc.cartographer.history.HistoryUnit
import me.azimmuradov.svc.cartographer.history.HistoryUnitsRegisterer
import me.azimmuradov.svc.engine.entity.PlacedEntity
import me.azimmuradov.svc.engine.geometry.*
import me.azimmuradov.svc.engine.layers.*


internal class Hand(
    unitsRegisterer: HistoryUnitsRegisterer,
    private val onGrab: (c: Coordinate) -> Pair<LayeredEntities, HistoryUnit>,
    private val onMove: (movedEs: LayeredEntities) -> Unit,
    private val onRelease: (movedEs: LayeredEntities) -> HistoryUnit,
) : RevertibleTool(
    type = ToolType.Hand,
    unitsRegisterer = unitsRegisterer,
) {

    override fun startBody(c: Coordinate): Pair<Boolean, HistoryUnit?> {
        val (esToMove, historyUnit) = onGrab(c)

        val isStartSuccessful = esToMove.flatten().isNotEmpty()

        return if (isStartSuccessful) {
            handManager = HandManager(start = c, esToMove)
            true to historyUnit
        } else {
            false to null
        }
    }

    override fun keepBody(c: Coordinate): HistoryUnit? {
        handManager.updateLast(c)
        onMove(handManager.movedEs)
        return null
    }

    override fun endBody(): HistoryUnit? {
        val movedEs = handManager.movedEs
        return onRelease(movedEs).takeIf { movedEs.flatten().isNotEmpty() }
    }


    private lateinit var handManager: HandManager

    private class HandManager(
        val start: Coordinate,
        private val esToMove: LayeredEntities,
    ) {

        fun updateLast(c: Coordinate) = run { last = c }

        val movedEs
            get() = esToMove.flatten().map { (entity, place) ->
                PlacedEntity(entity, place = place + (last - start))
            }.layered()


        private var last: Coordinate = start
    }
}
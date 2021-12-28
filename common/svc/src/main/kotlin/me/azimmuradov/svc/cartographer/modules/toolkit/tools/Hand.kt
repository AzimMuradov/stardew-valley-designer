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

package me.azimmuradov.svc.cartographer.modules.toolkit

import me.azimmuradov.svc.engine.entity.PlacedEntity
import me.azimmuradov.svc.engine.geometry.*
import me.azimmuradov.svc.engine.layers.*


internal class Hand(
    private val logicBuilder: () -> Logic,
) : HistoricalTool() {

    private lateinit var logic: Logic


    override fun startBody(c: Coordinate): Boolean {
        logic = logicBuilder()

        val esToMove = logic.onGrab(c)

        val isStartSuccessful = esToMove.flatten().isNotEmpty()

        if (isStartSuccessful) {
            handManager = HandManager(start = c, esToMove)
        }

        return isStartSuccessful
    }

    override fun keepBody(c: Coordinate) {
        handManager.updateLast(c)
        logic.onMove(handManager.movedEs)
    }

    override fun endBody() {
        val movedEs = handManager.movedEs
        logic.onRelease(movedEs)
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


    data class Logic(
        val onGrab: (c: Coordinate) -> LayeredEntities,
        val onMove: (movedEs: LayeredEntities) -> Unit,
        val onRelease: (movedEs: LayeredEntities) -> Unit,
    )
}
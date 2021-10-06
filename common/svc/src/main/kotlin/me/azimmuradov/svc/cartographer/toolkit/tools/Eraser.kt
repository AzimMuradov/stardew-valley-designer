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
import me.azimmuradov.svc.engine.geometry.Coordinate
import me.azimmuradov.svc.engine.layers.*


internal class Eraser(
    unitsRegisterer: HistoryUnitsRegisterer,
    private val onEraseStart: (c: Coordinate) -> LayeredSingleEntities,
    private val onErase: (c: Coordinate) -> LayeredSingleEntities,
    private val onEraseEnd: (removed: LayeredEntities) -> HistoryUnit,
) : RevertibleTool(
    type = ToolType.Eraser,
    unitsRegisterer = unitsRegisterer,
) {

    override fun startBody(c: Coordinate): Pair<Boolean, HistoryUnit?> {
        removedEs += onEraseStart(c).flatten()
        return true to null
    }

    override fun keepBody(c: Coordinate): HistoryUnit? {
        removedEs += onErase(c).flatten()
        return null
    }

    override fun endBody(): HistoryUnit? {
        val historyUnitOrNull = onEraseEnd(removedEs.layered()).takeIf { removedEs.isNotEmpty() }
        removedEs.clear()
        return historyUnitOrNull
    }


    private val removedEs = mutableListOf<PlacedEntity<*>>()
}
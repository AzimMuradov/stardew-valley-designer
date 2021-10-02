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

package me.azimmuradov.svc.cartographer.toolkit.tools

import me.azimmuradov.svc.cartographer.history.HistoryUnit
import me.azimmuradov.svc.cartographer.history.HistoryUnitsRegisterer
import me.azimmuradov.svc.cartographer.toolkit.RevertibleTool
import me.azimmuradov.svc.cartographer.toolkit.ToolType
import me.azimmuradov.svc.engine.coordinates
import me.azimmuradov.svc.engine.entity.PlacedEntity
import me.azimmuradov.svc.engine.rectmap.Coordinate


class Pen(
    unitsRegisterer: HistoryUnitsRegisterer,
    private val onDrawStart: (c: Coordinate) -> Pair<Boolean, PlacedEntity<*>?>,
    private val onDraw: (c: Coordinate, placedEsCs: Set<Coordinate>) -> PlacedEntity<*>?,
    private val onDrawEnd: (placedEs: List<PlacedEntity<*>>) -> HistoryUnit,
) : RevertibleTool(
    type = ToolType.Pen,
    unitsRegisterer = unitsRegisterer,
) {

    override fun startBody(c: Coordinate): Pair<Boolean, HistoryUnit?> {
        val (isStartSuccessful, placedEntity) = onDrawStart(c)
        if (isStartSuccessful && placedEntity != null) {
            placedEs += placedEntity
        }
        return isStartSuccessful to null
    }

    override fun keepBody(c: Coordinate): HistoryUnit? {
        onDraw(c, placedEs.coordinates())?.let { placedEs += it }
        return null
    }

    override fun endBody(): HistoryUnit? {
        val historyUnitOrNull = onDrawEnd(placedEs.toList()).takeIf { placedEs.isNotEmpty() }
        placedEs.clear()
        return historyUnitOrNull
    }


    private val placedEs = mutableListOf<PlacedEntity<*>>()
}
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
import me.azimmuradov.svc.engine.geometry.Coordinate


internal class Pen(
    unitsRegisterer: HistoryUnitsRegisterer,
    private val logicBuilder: () -> Logic,
) : RevertibleTool(
    type = ToolType.Pen,
    unitsRegisterer = unitsRegisterer,
) {

    private lateinit var logic: Logic


    override fun startBody(c: Coordinate): Pair<Boolean, HistoryUnit?> {
        logic = logicBuilder()
        return logic.onDrawStart(c) to null
    }

    override fun keepBody(c: Coordinate): HistoryUnit? {
        logic.onDraw(c)
        return null
    }

    override fun endBody(): HistoryUnit? {
        return logic.onDrawEnd()
    }


    data class Logic(
        val onDrawStart: (c: Coordinate) -> Boolean,
        val onDraw: (c: Coordinate) -> Unit,
        val onDrawEnd: () -> HistoryUnit?,
    )
}
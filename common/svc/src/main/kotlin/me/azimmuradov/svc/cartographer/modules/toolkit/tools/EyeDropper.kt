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

import me.azimmuradov.svc.engine.geometry.Coordinate

internal class EyeDropper(
    private val logicBuilder: () -> Logic,
) : HistoricalTool() {

    private lateinit var logic: Logic


    override fun startBody(c: Coordinate): Boolean {
        logic = logicBuilder()
        logic.onDropperStart(c)
        return true
    }

    override fun keepBody(c: Coordinate) {
        logic.onDropperKeep(c)
    }

    override fun endBody() {
        logic.onDropperEnd()
    }

    data class Logic(
        val onDropperStart: (c: Coordinate) -> Unit,
        val onDropperKeep: (c: Coordinate) -> Unit,
        val onDropperEnd: () -> Unit,
    )
}
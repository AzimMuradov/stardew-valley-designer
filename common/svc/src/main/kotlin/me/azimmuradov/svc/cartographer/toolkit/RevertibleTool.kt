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

import me.azimmuradov.svc.cartographer.history.*
import me.azimmuradov.svc.cartographer.toolkit.Tool.State
import me.azimmuradov.svc.engine.rectmap.Coordinate


abstract class RevertibleTool internal constructor(
    final override val type: ToolType,
    private val unitsRegisterer: HistoryUnitsRegisterer,
) : Tool {

    final override var state: State = State.Stale
        private set


    final override fun start(c: Coordinate) {
        check(state == State.Stale)

        val (startIsSuccessful, historyUnit) = startBody(c)

        if (startIsSuccessful) {
            state = State.Acting
            last = c
            unitsBuilder += historyUnit
        }
    }

    final override fun keep(c: Coordinate) {
        check(state == State.Acting)

        if (c != last) {
            unitsBuilder += keepBody(c)
            last = c
        }
    }

    final override fun end() {
        check(state == State.Acting)

        unitsBuilder += endBody()
        state = State.Stale

        unitsBuilder.buildOrNull()?.let { historyUnit ->
            unitsRegisterer += historyUnit
        }
    }


    protected abstract fun startBody(c: Coordinate): Pair<Boolean, HistoryUnit?>

    protected abstract fun keepBody(c: Coordinate): HistoryUnit?

    protected abstract fun endBody(): HistoryUnit?


    // For efficiency

    private var last = Coordinate.ZERO


    // For units registering

    private val unitsBuilder = HistoryUnitsBuilder()
}
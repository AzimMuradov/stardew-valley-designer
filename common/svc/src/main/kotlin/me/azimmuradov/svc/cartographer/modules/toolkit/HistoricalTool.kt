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

import me.azimmuradov.svc.cartographer.modules.history.HistorySnapshot
import me.azimmuradov.svc.engine.geometry.Coordinate


internal sealed class HistoricalTool : Tool {

    final override var state: ToolState = ToolState.Stale
        private set


    final override fun start(c: Coordinate) {
        if (state == ToolState.Stale) {
            if (startBody(c)) {
                state = ToolState.Acting
                last = c
            }
        }
    }

    final override fun keep(c: Coordinate) {
        if (state == ToolState.Acting && c != last) {
            keepBody(c)
            last = c
        }
    }

    final override fun end() {
        if (state == ToolState.Acting) {
            endBody()
            state = ToolState.Stale
        }
    }


    protected abstract fun startBody(c: Coordinate): Boolean

    protected abstract fun keepBody(c: Coordinate)

    protected abstract fun endBody()


    // For efficiency

    private var last = Coordinate.ZERO
}

typealias HistoricalAct = (HistorySnapshot) -> HistorySnapshot
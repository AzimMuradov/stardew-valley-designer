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

package me.azimmuradov.svc.cartographer.history

import androidx.compose.runtime.*


fun actionHistory(): ActionHistory = ActionHistoryImpl()


private class ActionHistoryImpl : ActionHistory {

    val historyUnits = mutableListOf<HistoryUnit>()

    var current: Int by mutableStateOf(HISTORY_ROOT)


    override val canGoBack: Boolean get() = current > HISTORY_ROOT

    override val canGoForward: Boolean get() = current < historyUnits.lastIndex


    override fun register(unit: HistoryUnit) {
        repeat(times = historyUnits.lastIndex - current) { historyUnits.removeLast() }
        historyUnits += unit
        current = historyUnits.lastIndex
    }

    override fun goBack() {
        if (canGoBack) {
            historyUnits[current].revert()
            current -= 1
        }
    }

    override fun goForward() {
        if (canGoForward) {
            historyUnits[current + 1].act()
            current += 1
        }
    }


    companion object {
        const val HISTORY_ROOT: Int = -1
    }
}
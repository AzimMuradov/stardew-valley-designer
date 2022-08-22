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

package me.azimmuradov.svc.cartographer.modules.history

import me.azimmuradov.svc.cartographer.modules.map.MapState


internal class HistoryManagerImpl(initialMapState: MapState) : HistoryManager {

    private val states = mutableListOf(initialMapState)

    private var current: Int = HISTORY_ROOT


    override val currentMapState: MapState get() = states[current]

    override val canGoBack: Boolean get() = current > HISTORY_ROOT

    override val canGoForward: Boolean get() = current < states.lastIndex


    override fun register(mapState: MapState) {
        if (currentMapState != mapState) {
            repeat(times = states.lastIndex - current) { states.removeLast() }
            states += mapState
            current = states.lastIndex
        }
    }

    override fun goBackIfCan() {
        if (canGoBack) {
            current -= 1
        }
    }

    override fun goForwardIfCan() {
        if (canGoForward) {
            current += 1
        }
    }


    private companion object {

        private const val HISTORY_ROOT: Int = 0
    }
}
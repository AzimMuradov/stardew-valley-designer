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

package me.azimmuradov.svc.cartographer.modules.history

import me.azimmuradov.svc.cartographer.logger

internal class ObservableHistoryManager(
    private val onHistoryChanged: (HistoryChange) -> Unit,
) : HistoryManager {

    private val snapshots = mutableListOf<HistorySnapshot>()

    private var current: Int = HISTORY_ROOT


    override val currentSnapshot: HistorySnapshot? get() = snapshots.getOrNull(current)

    override val canGoBack: Boolean get() = current > HISTORY_ROOT

    override val canGoForward: Boolean get() = current < snapshots.lastIndex


    override fun register(snapshot: HistorySnapshot) {
        if (currentSnapshot != snapshot) {
            repeat(times = snapshots.lastIndex - current) { snapshots.removeLast() }
            snapshots += snapshot
            current = snapshots.lastIndex

            logger.debug("History Manager Snapshots = $snapshots")
            logger.debug("History Manager Current Snapshot = $currentSnapshot")

            onHistoryChanged(HistoryChange.AfterRegistering(canGoBack, canGoForward))
        }
    }

    override fun goBackIfCan() {
        if (canGoBack) {
            current -= 1
            onHistoryChanged(HistoryChange.AfterTraveling(canGoBack, canGoForward, currentSnapshot))
        }
    }

    override fun goForwardIfCan() {
        if (canGoForward) {
            current += 1
            onHistoryChanged(HistoryChange.AfterTraveling(canGoBack, canGoForward, currentSnapshot))
        }
    }


    private companion object {

        private const val HISTORY_ROOT: Int = -1
    }
}


sealed interface HistoryChange {

    val canGoBack: Boolean

    val canGoForward: Boolean


    data class AfterRegistering(
        override val canGoBack: Boolean,
        override val canGoForward: Boolean,
    ) : HistoryChange

    data class AfterTraveling(
        override val canGoBack: Boolean,
        override val canGoForward: Boolean,
        val currentSnapshot: HistorySnapshot?,
    ) : HistoryChange
}
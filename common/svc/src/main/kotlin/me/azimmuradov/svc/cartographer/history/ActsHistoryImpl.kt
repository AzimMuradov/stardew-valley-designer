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


fun actsHistory(): ActsHistory = ActsHistoryImpl()


private class ActsHistoryImpl : ActsHistory {

    val acts = mutableListOf<RevertibleAct>()

    var current: Int = HISTORY_ROOT


    override val canGoBack: Boolean = current > -1

    override val canGoForward: Boolean = current < acts.lastIndex


    override fun register(act: RevertibleAct) {
        acts.dropLast(acts.lastIndex - current)
        acts += act
        ++current
    }

    override fun goBack() {
        if (canGoBack) acts[current--].act()
    }

    override fun goForward() {
        if (canGoForward) acts[++current].revertedAct()
    }


    companion object {
        const val HISTORY_ROOT: Int = -1
    }
}
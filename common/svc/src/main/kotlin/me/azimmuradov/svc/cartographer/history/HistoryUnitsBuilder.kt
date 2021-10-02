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


internal class HistoryUnitsBuilder {

    operator fun plusAssign(unit: HistoryUnit?) {
        if (unit != null) historyUnits += unit
    }

    fun buildOrNull(): HistoryUnit? {
        if (historyUnits.isEmpty()) return null

        val (actLambdas, revertLambdas) = historyUnits.map { (act, revert) -> act to revert }.unzip()
        historyUnits.clear()

        return HistoryUnit(
            act = { for (act in actLambdas) act() },
            revert = { for (revert in revertLambdas.asReversed()) revert() },
        )
    }


    private val historyUnits = mutableListOf<HistoryUnit>()
}
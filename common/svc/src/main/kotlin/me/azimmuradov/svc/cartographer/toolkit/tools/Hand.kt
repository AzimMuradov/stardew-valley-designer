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

import me.azimmuradov.svc.cartographer.history.RevertibleAct
import me.azimmuradov.svc.cartographer.history.actsHistory
import me.azimmuradov.svc.cartographer.toolkit.*
import me.azimmuradov.svc.engine.entity.PlacedEntity
import me.azimmuradov.svc.engine.rectmap.Coordinate


class Hand(
    private val startBlock: (Coordinate) -> List<PlacedEntity<*>>,
    private val keepBlock: (List<PlacedEntity<*>>) -> Unit,
    private val endBlock: (List<PlacedEntity<*>>) -> Unit,
) : ToolWithRevertibleAct(
    type = ToolType.Hand,
    actsRegisterer = actsHistory(),
) {

    private var start: Coordinate = Coordinate.ZERO
    private var last: Coordinate = Coordinate.ZERO
    private var esToMove: List<PlacedEntity<*>> = listOf()

    private fun reset() {
        start = Coordinate.ZERO
        last = Coordinate.ZERO
        esToMove = listOf()
    }


    override fun startActBody(c: Coordinate): RevertibleAct? {
        start = c
        last = c
        esToMove = startBlock(c)
        return null
    }

    override fun keepActBody(c: Coordinate): RevertibleAct? {
        last = c
        keepBlock(esToMove.moveAll(vec = last - start))
        return null
    }

    override fun endActBody(): RevertibleAct? {
        endBlock(esToMove.moveAll(vec = last - start)).also { reset() }
        return null
    }


    private companion object {

        fun List<PlacedEntity<*>>.moveAll(vec: Vector2) = map { it.move(vec) }

        fun PlacedEntity<*>.move(vec: Vector2) = copy(place = place + vec)
    }
}
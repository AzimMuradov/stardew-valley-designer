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

import me.azimmuradov.svc.cartographer.history.ActsRegisterer
import me.azimmuradov.svc.cartographer.history.RevertibleAct
import me.azimmuradov.svc.engine.entity.PlacedEntity
import me.azimmuradov.svc.engine.rectmap.Coordinate


class Hand(
    actsRegisterer: ActsRegisterer,
    private val startBlock: (Coordinate) -> Pair<List<PlacedEntity<*>>, RevertibleAct>,
    private val keepBlock: (List<PlacedEntity<*>>) -> Unit,
    private val endBlock: (List<PlacedEntity<*>>) -> RevertibleAct,
) : ToolWithRevertibleAct(
    type = ToolType.Hand,
    actsRegisterer = actsRegisterer,
) {

    private var start: Coordinate = Coordinate.ZERO
    private var esToMove: List<PlacedEntity<*>> = listOf()

    private fun reset() {
        start = Coordinate.ZERO
        esToMove = listOf()
    }


    override fun startActBody(c: Coordinate): RevertibleAct {
        start = c
        val (es, act) = startBlock(c)
        esToMove = es
        return act
    }

    override fun keepActBody(c: Coordinate): RevertibleAct? {
        keepBlock(esToMove.moveAll(vec = c - start))
        return null
    }

    override fun endActBody(c: Coordinate): RevertibleAct {
        start = c
        return endBlock(esToMove.moveAll(vec = c - start)).also { reset() }
    }


    private companion object {

        fun List<PlacedEntity<*>>.moveAll(vec: Vector2) = map { it.move(vec) }

        fun PlacedEntity<*>.move(vec: Vector2) = copy(place = place + vec)
    }
}
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
import me.azimmuradov.svc.engine.rectmap.Coordinate


abstract class ToolWithRevertibleAct(
    final override val type: ToolType,
    private val actsRegisterer: ActsRegisterer,
) : Tool {

    final override var state: State = State.Stale
        private set


    final override fun startAct(c: Coordinate) {
        check(state == State.Stale)

        state = State.Acting
        last = c

        rActBuilder.add(startActBody(c))
    }

    final override fun keepAct(c: Coordinate) {
        check(state == State.Acting)

        if (c != last) {
            last = c

            rActBuilder.add(keepActBody(c))
        }
    }

    final override fun endAct(c: Coordinate) {
        check(state == State.Acting)

        state = State.Stale
        last = Coordinate.ZERO

        rActBuilder.add(endActBody(c))

        actsRegisterer += rActBuilder.build()
    }


    protected abstract fun startActBody(c: Coordinate): RevertibleAct?

    protected abstract fun keepActBody(c: Coordinate): RevertibleAct?

    protected abstract fun endActBody(c: Coordinate): RevertibleAct?


    // For efficiency

    private var last: Coordinate = Coordinate.ZERO


    // For act registering

    private val rActBuilder = RevertibleActBuilder()
}
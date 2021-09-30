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
import me.azimmuradov.svc.cartographer.toolkit.ToolType
import me.azimmuradov.svc.cartographer.toolkit.ToolWithRevertibleAct
import me.azimmuradov.svc.engine.rectmap.Coordinate


class Eraser(
    private val startBlock: (Coordinate) -> Unit,
    private val keepBlock: (Coordinate) -> Unit,
    private val endBlock: () -> Unit,
) : ToolWithRevertibleAct(
    type = ToolType.Eraser,
    actsRegisterer = actsHistory(),
) {

    override fun startActBody(c: Coordinate): RevertibleAct? {
        startBlock(c)
        return null
    }

    override fun keepActBody(c: Coordinate): RevertibleAct? {
        keepBlock(c)
        return null
    }

    override fun endActBody(): RevertibleAct? {
        endBlock()
        return null
    }
}
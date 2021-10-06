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

import androidx.compose.runtime.*


internal class Toolkit(
    hand: Hand,
    pen: Pen,
    eraser: Eraser,
    eyeDrawer: EyeDrawer,
) {

    var tool: Tool? by mutableStateOf(null)
        private set

    fun chooseToolOf(type: ToolType?) {
        check(tool?.state != ToolState.Acting)

        tool = if (type != null) tools.first { it.type == type } else type
    }


    private val tools: List<Tool> = listOf(hand, pen, eraser, eyeDrawer)
}
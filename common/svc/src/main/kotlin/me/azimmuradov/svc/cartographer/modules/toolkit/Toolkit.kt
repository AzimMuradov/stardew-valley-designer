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

package me.azimmuradov.svc.cartographer.modules.toolkit

internal class Toolkit(
    hand: () -> Hand.Logic,
    pen: () -> Pen.Logic,
    eraser: () -> Eraser.Logic,
    eyeDropper: () -> EyeDropper.Logic,
    private val onToolChosen: (ToolType?) -> Unit,
) {

    var tool: Tool? = null
        private set

    fun chooseToolOf(type: ToolType?) {
        if (tool?.state != ToolState.Acting) {
            tool = type?.let(tools::get)
            onToolChosen(type)
        }
    }


    private val tools: Map<ToolType, Tool> = mapOf(
        ToolType.Hand to Hand(logicBuilder = hand),
        ToolType.Pen to Pen(logicBuilder = pen),
        ToolType.Eraser to Eraser(logicBuilder = eraser),
        ToolType.EyeDropper to EyeDropper(logicBuilder = eyeDropper),
    )


    // Observers & Updaters

    fun update(toolType: ToolType?) {
        tool = toolType?.let(tools::get)
    }
}
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

import me.azimmuradov.svc.engine.geometry.Coordinate


interface Tool : ToolInfo {

    fun start(c: Coordinate)

    fun keep(c: Coordinate)

    fun end()
}


interface ToolInfo {

    val type: ToolType

    val state: ToolState
}


/**
 * Available tool types.
 */
enum class ToolType {
    Hand,
    Pen,
    Eraser,
    EyeDropper,
    RectSelect,
    EllipseSelect,
}

/**
 * Tool state.
 */
enum class ToolState { Stale, Acting }
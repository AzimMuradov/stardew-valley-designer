/*
 * Copyright 2021-2022 Azim Muradov
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

package me.azimmuradov.svc.cartographer

import me.azimmuradov.svc.cartographer.modules.map.MapState
import me.azimmuradov.svc.cartographer.modules.history.HistoryState
import me.azimmuradov.svc.cartographer.modules.options.OptionsState
import me.azimmuradov.svc.cartographer.modules.palette.PaletteState
import me.azimmuradov.svc.cartographer.modules.toolkit.ToolkitState
import me.azimmuradov.svc.cartographer.modules.vislayers.VisLayersState
import me.azimmuradov.svc.engine.layout.Layout


data class CartographerState(
    val history: HistoryState,
    val map: MapState,
    val toolkit: ToolkitState,
    val palette: PaletteState,
    // val flavors: FlavorsState,
    val visLayers: VisLayersState,
    // val clipboard: ClipboardState,
    val options: OptionsState,
) {
    companion object {
        fun default(layout: Layout) = CartographerState(
            history = HistoryState.default(),
            map = MapState.default(layout),
            // flavors = FlavorsState.default(),
            toolkit = ToolkitState.default(),
            palette = PaletteState.default(),
            visLayers = VisLayersState.default(),
            // clipboard = ClipboardState.default(),
            options = OptionsState.default(),
        )
    }
}
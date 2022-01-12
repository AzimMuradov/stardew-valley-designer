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

package me.azimmuradov.svc.cartographer.state

data class SvcState(
    // Top Menu
    val history: HistoryState,

    // Left-Side Menu
    val toolkit: ToolkitState,
    val palette: PaletteState,

    // Right-Side Menu

    // EditorState
    val editor: EditorState,
) {

    companion object {

        fun default(layout: LayoutState) = SvcState(
            history = HistoryState.default(),
            toolkit = ToolkitState.None,
            palette = PaletteState.default(size = 10),
            editor = EditorState.default(layout),
        )
    }
}
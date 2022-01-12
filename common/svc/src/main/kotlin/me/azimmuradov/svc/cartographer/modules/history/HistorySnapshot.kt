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

package me.azimmuradov.svc.cartographer.modules.history

import me.azimmuradov.svc.cartographer.state.*

data class HistorySnapshot(
    val toolkit: ToolkitState,
    val palette: PaletteState,
    val editor: EditorState,
) {

    companion object {

        fun default(layout: LayoutState) = SvcState.default(layout).toHistorySnapshot()
    }
}


fun SvcState.toHistorySnapshot(): HistorySnapshot = HistorySnapshot(
    toolkit = toolkit,
    palette = palette,
    editor = editor
)
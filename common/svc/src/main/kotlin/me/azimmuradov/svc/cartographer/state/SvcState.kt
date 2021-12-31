package me.azimmuradov.svc.cartographer.state

import me.azimmuradov.svc.cartographer.state.editor.EditorState
import me.azimmuradov.svc.cartographer.state.editor.LayoutState

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
            toolkit = ToolkitState.default(),
            palette = PaletteState.default(size = 10),
            editor = EditorState.default(layout),
        )
    }
}
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
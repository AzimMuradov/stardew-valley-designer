package me.azimmuradov.svc.cartographer.state

data class HistoryState(
    val canGoBack: Boolean,
    val canGoForward: Boolean,
) {

    companion object {

        fun default() = HistoryState(
            canGoBack = false,
            canGoForward = false
        )
    }
}
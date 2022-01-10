package me.azimmuradov.svc.cartographer.modules.history

sealed interface HistoryChange {

    val canGoBack: Boolean

    val canGoForward: Boolean


    data class AfterRegistering(
        override val canGoBack: Boolean,
        override val canGoForward: Boolean,
    ) : HistoryChange

    data class AfterTraveling(
        override val canGoBack: Boolean,
        override val canGoForward: Boolean,
        val currentSnapshot: HistorySnapshot?,
    ) : HistoryChange
}
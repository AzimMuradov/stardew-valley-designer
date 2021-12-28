package me.azimmuradov.svc.cartographer.state

import me.azimmuradov.svc.engine.entity.Entity

data class PaletteState(
    val inUse: Entity<*>?,
) {

    companion object {

        fun default() = PaletteState(
            inUse = null
        )
    }
}
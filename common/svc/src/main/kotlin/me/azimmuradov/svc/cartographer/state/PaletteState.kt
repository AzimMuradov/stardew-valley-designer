package me.azimmuradov.svc.cartographer.state

import me.azimmuradov.svc.engine.entity.Entity

data class PaletteState(
    val inUse: Entity<*>?,
    val hotbar: List<Entity<*>?>,
) {

    companion object {

        fun default(size: Int) = PaletteState(
            inUse = null,
            hotbar = List(size) { null },
        )
    }
}
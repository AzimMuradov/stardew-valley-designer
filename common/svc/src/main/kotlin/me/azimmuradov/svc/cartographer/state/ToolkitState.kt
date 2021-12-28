package me.azimmuradov.svc.cartographer.state

import me.azimmuradov.svc.cartographer.modules.toolkit.ToolType

data class ToolkitState(
    val currentToolType: ToolType?,
) {

    companion object {

        fun default() = ToolkitState(
            currentToolType = null
        )
    }
}
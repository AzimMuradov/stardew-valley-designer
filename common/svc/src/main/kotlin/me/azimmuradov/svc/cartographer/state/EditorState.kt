package me.azimmuradov.svc.cartographer.state

import me.azimmuradov.svc.engine.layer.LayerType
import me.azimmuradov.svc.engine.layers.LayeredEntitiesData

data class EditorState(
    val entities: LayeredEntitiesData,
    val layout: LayoutState,
    val visibleLayers: Set<LayerType<*>>,
) {

    companion object {

        fun default(layout: LayoutState) = EditorState(
            entities = LayeredEntitiesData(),
            layout = layout,
            visibleLayers = LayerType.all.toSet()
        )
    }
}
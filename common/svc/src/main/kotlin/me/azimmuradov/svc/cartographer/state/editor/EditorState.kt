package me.azimmuradov.svc.cartographer.state.editor

import me.azimmuradov.svc.engine.entity.PlacedEntity
import me.azimmuradov.svc.engine.layer.LayerType

data class EditorState(
    val entities: List<Pair<LayerType<*>, List<PlacedEntity<*>>>>,
    val heldEntities: List<Pair<LayerType<*>, List<PlacedEntity<*>>>>,
    val chosenEntities: List<Pair<LayerType<*>, List<PlacedEntity<*>>>>,
    val layout: LayoutState,
    val visibleLayers: Set<LayerType<*>>,
) {

    companion object {

        fun default(layout: LayoutState) = EditorState(
            entities = emptyList(),
            heldEntities = emptyList(),
            chosenEntities = emptyList(),
            layout = layout,
            visibleLayers = LayerType.all.toSet()
        )
    }
}
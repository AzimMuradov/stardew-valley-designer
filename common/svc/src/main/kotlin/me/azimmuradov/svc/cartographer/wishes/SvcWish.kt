package me.azimmuradov.svc.cartographer.wishes

import me.azimmuradov.svc.cartographer.modules.toolkit.ToolType
import me.azimmuradov.svc.engine.entity.Entity
import me.azimmuradov.svc.engine.geometry.Coordinate
import me.azimmuradov.svc.engine.layer.LayerType

sealed interface SvcWish {

    // Top Menu

    sealed interface History : SvcWish {

        object GoBack : History

        object GoForward : History
    }


    // Left-Side Menu

    sealed interface Tools : SvcWish {

        data class ChooseTool(val type: ToolType) : Tools
    }

    sealed interface Palette : SvcWish {

        data class AddToInUse(val entity: Entity<*>) : Palette

        // data class AddToHotbar(val entity: Entity<*>, val i: UInt) : Palette
        //
        //
        // object ClearInUse : Palette
        //
        // data class ClearHotbarCell(val i: UInt) : Palette
        //
        // object Clear : Palette
    }

    // sealed interface Flavors : SvcWish

    // sealed interface Clipboard : SvcWish


    // Right-Side Menu

    sealed interface VisibilityLayers : SvcWish {

        data class ChangeVisibility(val layerType: LayerType<*>, val visible: Boolean) : VisibilityLayers
    }


    // Editor

    sealed interface Act : SvcWish {

        data class Start(val coordinate: Coordinate) : Act

        data class Continue(val coordinate: Coordinate) : Act

        object End : Act
    }
}
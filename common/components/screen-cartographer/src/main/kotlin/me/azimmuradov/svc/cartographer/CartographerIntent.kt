/*
 * Copyright 2021-2023 Azim Muradov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.azimmuradov.svc.cartographer

import me.azimmuradov.svc.cartographer.modules.toolkit.ShapeType
import me.azimmuradov.svc.cartographer.modules.toolkit.ToolType
import me.azimmuradov.svc.engine.Flooring
import me.azimmuradov.svc.engine.Wallpaper
import me.azimmuradov.svc.engine.entity.Entity
import me.azimmuradov.svc.engine.geometry.Coordinate
import me.azimmuradov.svc.engine.layer.LayerType


sealed interface CartographerIntent {

    sealed interface History : CartographerIntent {
        data object GoBack : History
        data object GoForward : History
    }

    sealed interface Engine : CartographerIntent {
        data class Start(val coordinate: Coordinate) : Engine
        data class Continue(val coordinate: Coordinate) : Engine
        data object End : Engine
    }

    sealed interface Toolkit : CartographerIntent {
        data class ChooseTool(val type: ToolType) : Toolkit
        data class ChooseShape(val type: ShapeType?) : Toolkit
    }

    sealed interface Palette : CartographerIntent {
        data class AddToInUse(val entity: Entity<*>) : Palette
        data class AddToHotbar(val entity: Entity<*>, val i: UInt) : Palette
        data object ClearInUse : Palette
        data class ClearHotbarCell(val i: UInt) : Palette
        data object Clear : Palette
    }

    sealed interface VisLayers : CartographerIntent {
        data class ChangeVisibility(
            val layerType: LayerType<*>,
            val visible: Boolean,
        ) : VisLayers
    }

    sealed interface WallpaperAndFlooring : CartographerIntent {
        data class ChooseWallpaper(val wallpaper: Wallpaper) : WallpaperAndFlooring
        data class ChooseFlooring(val flooring: Flooring) : WallpaperAndFlooring
    }

    sealed interface Options : CartographerIntent {
        data class ChangeAxisVisibility(val isVisible: Boolean) : Options
        data class ChangeGridVisibility(val isVisible: Boolean) : Options
        data class ChangeSpritesRender(val isRenderedFully: Boolean) : Options
    }
}

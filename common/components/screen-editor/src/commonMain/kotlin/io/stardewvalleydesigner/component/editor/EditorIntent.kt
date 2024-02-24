/*
 * Copyright 2021-2024 Azim Muradov
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

package io.stardewvalleydesigner.component.editor

import io.stardewvalleydesigner.component.editor.modules.toolkit.ShapeType
import io.stardewvalleydesigner.component.editor.modules.toolkit.ToolType
import io.stardewvalleydesigner.designformat.models.OptionsItemValue
import io.stardewvalleydesigner.engine.Flooring
import io.stardewvalleydesigner.engine.Wallpaper
import io.stardewvalleydesigner.engine.entity.Entity
import io.stardewvalleydesigner.engine.geometry.Coordinate
import io.stardewvalleydesigner.engine.layer.LayerType


sealed interface EditorIntent {

    sealed interface History : EditorIntent {
        data object GoBack : History
        data object GoForward : History
    }

    sealed interface Engine : EditorIntent {
        data class Start(val coordinate: Coordinate) : Engine
        data class Keep(val coordinate: Coordinate) : Engine
        data object End : Engine
    }

    sealed interface PlayerName : EditorIntent {
        data class Change(val name: String) : PlayerName
    }

    sealed interface FarmName : EditorIntent {
        data class Change(val name: String) : FarmName
    }

    sealed interface Toolkit : EditorIntent {
        data class ChooseTool(val type: ToolType) : Toolkit
        data class ChooseShape(val type: ShapeType?) : Toolkit
    }

    sealed interface Palette : EditorIntent {
        data class AddToInUse(val entity: Entity<*>) : Palette
        data class AddToHotbar(val entity: Entity<*>, val i: UInt) : Palette
        data object ClearInUse : Palette
        data class ClearHotbarCell(val i: UInt) : Palette
        data object Clear : Palette
    }

    sealed interface VisLayers : EditorIntent {
        data class ChangeVisibility(
            val layerType: LayerType<*>,
            val visible: Boolean,
        ) : VisLayers
    }

    sealed interface WallpaperAndFlooring : EditorIntent {
        data class ChooseWallpaper(val wallpaper: Wallpaper) : WallpaperAndFlooring
        data class ChooseFlooring(val flooring: Flooring) : WallpaperAndFlooring
    }

    sealed interface Options : EditorIntent {
        data class Toggle(
            val toggleable: OptionsItemValue.Toggleable,
            val value: Boolean,
        ) : Options
    }
}

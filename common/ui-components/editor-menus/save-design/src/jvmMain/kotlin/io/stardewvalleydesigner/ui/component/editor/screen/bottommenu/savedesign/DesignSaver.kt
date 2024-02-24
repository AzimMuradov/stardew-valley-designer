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

package io.stardewvalleydesigner.ui.component.editor.screen.bottommenu.savedesign

import io.stardewvalleydesigner.component.editor.modules.map.MapState
import io.stardewvalleydesigner.designformat.DesignFormatConverter
import io.stardewvalleydesigner.designformat.models.*


internal object DesignSaver {

    fun serializeDesignToString(
        map: MapState,
        playerName: String,
        farmName: String,
        palette: Palette,
        options: Options,
    ): String = DesignFormatConverter.stringify(
        design = convertToDesign(map, playerName, farmName, palette, options),
    )


    private fun convertToDesign(
        map: MapState,
        playerName: String,
        farmName: String,
        palette: Palette,
        options: Options,
    ): Design = Design(
        playerName = playerName,
        farmName = farmName,
        layout = map.layout.type,
        entities = map.entities,
        wallpaper = map.wallpaper,
        flooring = map.flooring,
        palette = palette,
        options = options,
    )
}

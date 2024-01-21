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

package io.stardewvalleydesigner.engine

import io.stardewvalleydesigner.engine.layers.*
import io.stardewvalleydesigner.engine.layout.LayoutType
import io.stardewvalleydesigner.engine.layout.LayoutsProvider.layoutOf


data class EditorEngineData(
    val layoutType: LayoutType,
    val layeredEntitiesData: LayeredEntitiesData,
    val wallpaper: Wallpaper?,
    val flooring: Flooring?,
)


fun EditorEngine.exportData(): EditorEngineData = EditorEngineData(
    layoutType = layout.type,
    layeredEntitiesData = layers.entities,
    wallpaper = wallpaper,
    flooring = flooring,
)

fun EditorEngineData.generateEngine(): EditorEngine = editorEngineOf(layoutOf(layoutType)).apply {
    putAll(layeredEntitiesData.toLayeredEntities())
    wallpaper = this@generateEngine.wallpaper
    flooring = this@generateEngine.flooring
}

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

package io.stardewvalleydesigner.ui.component.editor.res

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.ImageBitmap
import io.stardewvalleydesigner.component.editor.modules.toolkit.ShapeType
import io.stardewvalleydesigner.component.editor.modules.toolkit.ToolType
import io.stardewvalleydesigner.data.SpritePage


object ImageResources {

    val entities: Map<SpritePage, ImageBitmap> @Composable get() = imagesResourcesData.entities

    val wallsAndFloors: ImageBitmap @Composable get() = imagesResourcesData.wallsAndFloors

    val walls2: ImageBitmap @Composable get() = imagesResourcesData.walls2

    val floors2: ImageBitmap @Composable get() = imagesResourcesData.floors2

    val tools: Map<ToolType, ImageBitmap> @Composable get() = imagesResourcesData.tools

    val shapes: Map<ShapeType?, ImageBitmap> @Composable get() = imagesResourcesData.shapes


    private val imagesResourcesData: ImagesResourcesData @Composable get() = LocalImageResources.current
}

@Composable
fun WithImageResources(content: @Composable () -> Unit) {
    val data = ImagesResourcesData(
        entities = ImageResourcesProvider.entitySpriteMaps(),
        wallsAndFloors = ImageResourcesProvider.wallsAndFloorsSprite(),
        walls2 = ImageResourcesProvider.walls2Sprite(),
        floors2 = ImageResourcesProvider.floors2Sprite(),
        tools = ImageResourcesProvider.toolImages(),
        shapes = ImageResourcesProvider.shapeImages(),
    )
    CompositionLocalProvider(
        value = LocalImageResources provides data,
        content = content
    )
}

private data class ImagesResourcesData(
    val entities: Map<SpritePage, ImageBitmap>,
    val wallsAndFloors: ImageBitmap,
    val walls2: ImageBitmap,
    val floors2: ImageBitmap,
    val tools: Map<ToolType, ImageBitmap>,
    val shapes: Map<ShapeType?, ImageBitmap>,
)

private val LocalImageResources = compositionLocalOf<ImagesResourcesData> {
    error("No image resources provided")
}

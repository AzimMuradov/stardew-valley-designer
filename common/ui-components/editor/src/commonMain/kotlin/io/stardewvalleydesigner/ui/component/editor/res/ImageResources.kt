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
import io.stardewvalleydesigner.engine.layout.LayoutType
import io.stardewvalleydesigner.metadata.EntityPage
import io.stardewvalleydesigner.ui.component.themes.ThemeVariant


object ImageResources {

    val entities: Map<EntityPage, ImageBitmap> @Composable get() = imagesResourcesData.entities

    val wallsAndFloors: ImageBitmap @Composable get() = imagesResourcesData.wallsAndFloors

    val layouts: Map<LayoutType, LayoutSprites> @Composable get() = imagesResourcesData.layouts


    private val imagesResourcesData: ImagesResourcesData @Composable get() = LocalImageResources.current
}

@Composable
fun WithImageResources(themeVariant: ThemeVariant, content: @Composable () -> Unit) {
    val data = ImagesResourcesData(
        entities = ImageResourcesProvider.entitySpriteMaps(),
        wallsAndFloors = ImageResourcesProvider.wallsAndFloorsSprite(),
        layouts = ImageResourcesProvider.layoutSprites(themeVariant),
    )
    CompositionLocalProvider(
        LocalImageResources provides data,
        content = content
    )
}

private data class ImagesResourcesData(
    val entities: Map<EntityPage, ImageBitmap>,
    val wallsAndFloors: ImageBitmap,
    val layouts: Map<LayoutType, LayoutSprites>,
)

private val LocalImageResources = staticCompositionLocalOf<ImagesResourcesData> {
    error("No image resources provided")
}

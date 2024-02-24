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

package io.stardewvalleydesigner.designformat.models

import io.stardewvalleydesigner.engine.layout.LayoutType
import kotlinx.serialization.Serializable


@Serializable
data class Design(
    val version: DesignVersion = DesignVersion.VERSION_0_9_0,
    val playerName: String,
    val farmName: String,
    val layout: LayoutType,
    val entities: EntitiesPacked,
    val wallpaper: WallpaperPacked? = null,
    val flooring: FlooringPacked? = null,
)

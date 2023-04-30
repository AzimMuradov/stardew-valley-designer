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

package me.azimmuradov.svc.cartographer.res

import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import me.azimmuradov.svc.metadata.EntityPage


object ImageProvider {

    fun imageOf(file: EntityPage) = images.getValue(file)

    private val images = mapOf(
        EntityPage.CommonObjects to useResource("entities/common-objects.png", ::loadImageBitmap),
        EntityPage.Craftables to useResource("entities/craftables.png", ::loadImageBitmap),
        EntityPage.Furniture to useResource("entities/furniture.png", ::loadImageBitmap),
        EntityPage.Flooring to useResource("entities/flooring.png", ::loadImageBitmap),
    )
}

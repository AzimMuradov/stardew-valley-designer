/*
 * Copyright 2021-2021 Azim Muradov
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

package io.svapi.editor.impl.layout

import io.svapi.editor.Rect
import io.svapi.editor.RectMap
import io.svapi.editor.impl.entity.BigEntityType
import io.svapi.editor.impl.entity.CropType
import io.svapi.editor.impl.entity.FloorType
import io.svapi.editor.impl.entity.ObjectType
import io.svapi.editor.impl.layout.layouts.BigShed
import io.svapi.editor.impl.layout.layouts.Shed
import io.svapi.editor.impl.rectmap.rectMapOf


data class CartographerLayout(
    val type: CartographerLayoutType,
    val size: Rect,
    val rulesForFlooringLayer: RectMap<Set<FloorType>?> = rectMapOf(size),
    val rulesForObjectsLayer: RectMap<Set<ObjectType>?> = rectMapOf(size),
    val rulesForCropsLayer: RectMap<Set<CropType>?> = rectMapOf(size),
    val rulesForBigEntitiesLayer: RectMap<Set<BigEntityType>?> = rectMapOf(size),
) {

    init {
        val listOfRules = listOf(
            rulesForFlooringLayer,
            rulesForObjectsLayer,
            rulesForCropsLayer,
            rulesForBigEntitiesLayer,
        )

        require(listOfRules.all { it.rect == size }) { "Wrong `CartographerLayout` definition" }
    }
}


fun layout(type: CartographerLayoutType): CartographerLayout =
    when (type) {
        CartographerLayoutType.Shed -> Shed
        CartographerLayoutType.BigShed -> BigShed
    }
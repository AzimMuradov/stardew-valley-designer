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

package io.svapi.editor.impl.layout.layouts

import io.svapi.editor.Coordinate
import io.svapi.editor.Rect
import io.svapi.editor.impl.entity.CartographerEntityType
import io.svapi.editor.impl.entity.FloorType.GrassType
import io.svapi.editor.impl.entity.ObjectType.FurnitureType.BedType
import io.svapi.editor.impl.layout.CartographerLayout
import io.svapi.editor.impl.layout.CartographerLayoutType.BigShed
import io.svapi.editor.impl.layout.CartographerLayoutType.Shed
import io.svapi.editor.impl.rectmap.rectMapOf
import io.svapi.editor.xy


private val shedRect = Rect(w = 11, h = 10)

internal val Shed: CartographerLayout = CartographerLayout(
    type = Shed,
    shedRect,
    rulesForFlooringLayer = buildRules(shedRect, setOf(GrassType)),
    rulesForObjectsLayer = buildRules(shedRect, setOf(BedType)),
)


private val bigShedRect = Rect(w = 17, h = 13)

internal val BigShed: CartographerLayout = CartographerLayout(
    type = BigShed,
    bigShedRect,
    rulesForFlooringLayer = buildRules(bigShedRect, setOf(GrassType)),
    rulesForObjectsLayer = buildRules(bigShedRect, setOf(BedType)),
)


@OptIn(ExperimentalStdlibApi::class)
private fun <EType : CartographerEntityType> buildRules(rect: Rect, disallowed: Set<EType>) =
    rectMapOf(rect, map = buildMap<Coordinate, Set<EType>?> {
        repeat(times = rect.w) { x ->
            repeat(times = rect.h - 1) { y ->
                put(xy(x, y), disallowed)
            }
        }
        put(xy(x = rect.w / 2, y = rect.h - 1), disallowed)
    })
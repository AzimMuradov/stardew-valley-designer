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

package io.svapi.editor


/**
 * Editor layer.
 */
interface Layer<out EId, out E : Entity<EId>, out EH : EntityHolder<EId, E>> {

    val renderedMap: RectMap<EH>

    val map: RectMap<E>


    operator fun get(key: Coordinate): E?
}


val Layer<*, *, *>.layoutSize: Rect get() = renderedMap.rect


val Layer<*, *, *>.entitiesCount: Int get() = map.size

fun Layer<*, *, *>.isEmpty(): Boolean = map.isEmpty()

fun Layer<*, *, *>.isNotEmpty(): Boolean = map.isNotEmpty()
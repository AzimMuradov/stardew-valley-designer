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

package io.svapi.editor.impl.layer

import io.svapi.editor.Coordinate
import io.svapi.editor.EditorOperations
import io.svapi.editor.Rect
import io.svapi.editor.impl.entity.CartographerEntity
import io.svapi.editor.impl.entity.CartographerEntityType


interface CartographerLayer<out EType : CartographerEntityType> : EditorOperations<CartographerEntity<EType>> {

    val rect: Rect


    // val map: Map<Coordinate, CartographerEntity<EType>>

    // val renderedMap: Map<Coordinate, CartographerEntityHolder<EType>>


    // Restrictions for the Layer

    val disallowedEntityTypes: Map<Coordinate, Set<EType>?>

    val behaviour: CartographerLayerBehaviour
}
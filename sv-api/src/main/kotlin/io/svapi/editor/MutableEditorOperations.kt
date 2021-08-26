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
 * Available mutable editor operations.
 */
interface MutableEditorOperations<E : Entity<*>> : EditorOperations<E> {

    // Modification Operations

    fun put(key: Coordinate, value: E): E?

    fun remove(key: Coordinate): E?


    // Bulk Modification Operations

    fun putAll(from: Map<out Coordinate, E>)

    fun removeAll(keys: Iterable<Coordinate>)

    fun clear()
}


operator fun <E : Entity<*>> MutableEditorOperations<E>.set(key: Coordinate, value: E) {
    put(key, value)
}
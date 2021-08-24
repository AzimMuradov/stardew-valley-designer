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
 * Available immutable editor operations.
 */
interface EditorOperations<out E : Entity<*>> {

    // Query Operations

    // val size: Int

    // fun isEmpty(): Boolean

    // fun containsKey(key: Coordinate): Boolean

    // fun containsValue(value: @UnsafeVariance E): Boolean

    operator fun get(key: Coordinate): E?


    // Views

    val keys: Set<Coordinate>

    val values: Collection<E>

    val entries: Set<Map.Entry<Coordinate, E>>
}
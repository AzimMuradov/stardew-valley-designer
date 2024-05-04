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

package io.stardewvalleydesigner.engine.layer

import io.stardewvalleydesigner.engine.entity.EntityType
import io.stardewvalleydesigner.engine.geometry.Coordinate


/**
 * List of disjoint placed rectangular objects.
 */
class DisjointEntities<out T : EntityType> private constructor(
    private val objects: List<PlacedEntity<T>>,
) : List<PlacedEntity<T>> by objects {

    companion object {

        internal fun <T : EntityType> unsafe(objects: List<PlacedEntity<T>>): DisjointEntities<T> =
            DisjointEntities(objects)

        internal fun <T : EntityType> verified(objects: List<PlacedEntity<T>>): DisjointEntities<T> {
            val cs = mutableSetOf<Coordinate>()

            objects
                .asSequence()
                .flatMap(PlacedEntity<T>::coordinates)
                .forEach {
                    require(cs.add(it)) { "Wrong `DisjointRectObjects` definition." }
                }

            return DisjointEntities(objects)
        }
    }
}

fun <T : EntityType> emptyDisjointRectObjects(): DisjointEntities<T> =
    DisjointEntities.unsafe(emptyList())

fun <T : EntityType> List<PlacedEntity<T>>.asDisjointUnsafe(): DisjointEntities<T> =
    if (this is DisjointEntities<T>) this else DisjointEntities.unsafe(objects = this)

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

package io.stardewvalleydesigner.engine.layer

import io.stardewvalleydesigner.engine.geometry.Coordinate


/**
 * List of disjoint placed rectangular objects.
 */
class DisjointRectObjects<out RO : RectObject> private constructor(
    private val objects: List<PlacedRectObject<RO>>,
) : List<PlacedRectObject<RO>> by objects {

    companion object {

        internal fun <RO : RectObject> unsafe(objects: List<PlacedRectObject<RO>>): DisjointRectObjects<RO> =
            DisjointRectObjects(objects)

        internal fun <RO : RectObject> verified(objects: List<PlacedRectObject<RO>>): DisjointRectObjects<RO> {
            val cs = mutableSetOf<Coordinate>()

            objects
                .asSequence()
                .flatMap(PlacedRectObject<RO>::coordinates)
                .forEach {
                    require(cs.add(it)) { "Wrong `DisjointRectObjects` definition." }
                }

            return DisjointRectObjects(objects)
        }
    }
}

fun <RO : RectObject> emptyDisjointRectObjects(): DisjointRectObjects<RO> =
    DisjointRectObjects.unsafe(emptyList())

fun <RO : RectObject> List<PlacedRectObject<RO>>.asDisjointUnsafe(): DisjointRectObjects<RO> =
    if (this is DisjointRectObjects<RO>) this else DisjointRectObjects.unsafe(objects = this)

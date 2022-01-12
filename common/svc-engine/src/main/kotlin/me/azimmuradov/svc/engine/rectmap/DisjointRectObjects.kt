/*
 * Copyright 2021-2022 Azim Muradov
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

package me.azimmuradov.svc.engine.rectmap

import me.azimmuradov.svc.engine.geometry.Coordinate

/**
 * List of disjoint placed rectangular objects.
 */
class DisjointRectObjects<out RO : RectObject> internal constructor(
    private val objects: List<PlacedRectObject<RO>>,
) : List<PlacedRectObject<RO>> by objects {

    init {
        val cs = mutableSetOf<Coordinate>()

        objects
            .asSequence()
            .flatMap(PlacedRectObject<RO>::coordinates)
            .forEach {
                if (!cs.add(it)) {
                    throw IllegalArgumentException("Wrong `DisjointRectObjects` definition.")
                }
            }
    }
}

fun <RO : RectObject> emptyDisjointRectObjects(): DisjointRectObjects<RO> =
    listOf<PlacedRectObject<RO>>().asDisjoint()

fun <RO : RectObject> List<PlacedRectObject<RO>>.asDisjoint(): DisjointRectObjects<RO> =
    if (this is DisjointRectObjects<RO>) this else DisjointRectObjects(objects = this)
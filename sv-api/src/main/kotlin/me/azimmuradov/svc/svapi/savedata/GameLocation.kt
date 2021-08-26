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

package me.azimmuradov.svc.svapi.savedata

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty


data class GameLocation(
    @JacksonXmlProperty(isAttribute = true) val type: String? = null,
    val name: String? = null,
    private var _objects: List<Item<Vector2Wr, ObjectWr>>? = null,
    private var _furniture: List<Furniture>? = null,
) {

    @get:JacksonXmlElementWrapper(localName = "objects")
    @get:JacksonXmlProperty(localName = "Object")
    var objects: List<Item<Vector2Wr, ObjectWr>>? = null
        private set

    @get:JacksonXmlElementWrapper(localName = "furniture")
    @get:JacksonXmlProperty(localName = "Furniture")
    var furniture: List<Furniture>? = null
        private set


    init {
        _objects = objects
        _furniture = furniture
    }
}


data class Vector2Wr(
    @JacksonXmlProperty(localName = "Vector2") val pos: Position,
)

data class ObjectWr(
    @JacksonXmlProperty(localName = "Object") val obj: Obj,
)
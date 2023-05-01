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

package me.azimmuradov.svc.save.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlChildrenName


@Serializable
data class Building(
    val buildingType: String,
    val indoors: Indoors? = null,
)


@Serializable
data class Indoors(
    @XmlChildrenName("item", "", "") val objects: List<Item<Vector2Wrapper, ObjectWrapper>>,
    @XmlChildrenName("Furniture", "", "") val furniture: List<Furniture>,
    @XmlChildrenName("item", "", "") val terrainFeatures: List<Item<Vector2Wrapper, TerrainFeatureWrapper>>,
    val wallPaper: IntWrapper? = null,
    val floor: IntWrapper? = null,
)

@Serializable
data class Vector2Wrapper(@SerialName("Vector2") val pos: Position)

@Serializable
data class ObjectWrapper(@SerialName("Object") val obj: Object)

@Serializable
data class TerrainFeatureWrapper(@SerialName("TerrainFeature") val tf: TerrainFeature)

@Serializable
data class IntWrapper(val int: Int? = null)

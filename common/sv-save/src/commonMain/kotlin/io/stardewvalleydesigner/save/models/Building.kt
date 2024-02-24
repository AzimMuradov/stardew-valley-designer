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

package io.stardewvalleydesigner.save.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlChildrenName


@Serializable
internal data class Building(
    val buildingType: String,
    val indoors: Indoors? = null,
    val tileX: Int, val tileY: Int,
    val tilesWide: Int, val tilesHigh: Int,
)


@Serializable
internal data class Indoors(
    @XmlChildrenName("item", "", "") val objects: List<Item<Vector2Wrapper, ObjectWrapper>>,
    @XmlChildrenName("Furniture", "", "") val furniture: List<Furniture>,
    @XmlChildrenName("item", "", "") val terrainFeatures: List<Item<Vector2Wrapper, TerrainFeatureWrapper>>,
    val wallPaper: IntWrapper? = null,
    val floor: IntWrapper? = null,
    val appliedWallpaper: AppliedWallpaper? = null,
    val appliedFloor: AppliedFloor? = null,
) {

    val firstWallpaper: UByte?
        get() = wallPaper?.int?.toUByte()
            ?: appliedWallpaper?.dict?.firstOrNull()?.value?.string?.toUByte()

    val firstFloor: UByte?
        get() = floor?.int?.toUByte()
            ?: appliedFloor?.dict?.firstOrNull()?.value?.string?.toUByte()
}

@Serializable
internal data class AppliedWallpaper(
    @SerialName("SerializableDictionaryOfStringString")
    @XmlChildrenName("item", "", "")
    val dict: List<Item<StringWrapper, StringWrapper>>,
)

@Serializable
internal data class AppliedFloor(
    @SerialName("SerializableDictionaryOfStringString")
    @XmlChildrenName("item", "", "")
    val dict: List<Item<StringWrapper, StringWrapper>>,
)

@Serializable
internal data class Vector2Wrapper(@SerialName("Vector2") val pos: Position)

@Serializable
internal data class ObjectWrapper(@SerialName("Object") val obj: Object)

@Serializable
internal data class TerrainFeatureWrapper(@SerialName("TerrainFeature") val tf: TerrainFeature)

@Serializable
internal data class IntWrapper(val int: Int? = null)

@Serializable
internal data class StringWrapper(val string: String? = null)

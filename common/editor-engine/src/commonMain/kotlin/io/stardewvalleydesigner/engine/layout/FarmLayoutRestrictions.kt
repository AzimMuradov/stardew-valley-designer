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

package io.stardewvalleydesigner.engine.layout

import io.stardewvalleydesigner.engine.geometry.Coordinate
import io.stardewvalleydesigner.engine.geometry.xy
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder


@Serializable
internal data class FarmLayoutRestrictions(
    val canPlaceFloorOrObjectOrBuilding: List<SerializableCoordinate>,
    val canPlaceFloorOrObject: List<SerializableCoordinate>,
    val canPlaceFloor: List<SerializableCoordinate>,
    val canPlaceNothing: List<SerializableCoordinate>,
)


internal typealias SerializableCoordinate = @Serializable(with = CoordinateSerializer::class) Coordinate

internal object CoordinateSerializer : KSerializer<Coordinate> {

    private val delegateSerializer = CoordinateSurrogate.serializer()

    @OptIn(ExperimentalSerializationApi::class)
    override val descriptor: SerialDescriptor = SerialDescriptor(
        serialName = "io.stardewvalleydesigner.engine.entity.PlacedEntity",
        original = delegateSerializer.descriptor,
    )

    override fun serialize(encoder: Encoder, value: Coordinate) {
        delegateSerializer.serialize(
            encoder,
            value = CoordinateSurrogate(value.x, value.y)
        )
    }

    override fun deserialize(decoder: Decoder): Coordinate {
        val c = delegateSerializer.deserialize(decoder)
        return xy(c.x, c.y)
    }
}


@Serializable
private data class CoordinateSurrogate(val x: Int, val y: Int)

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

package io.stardewvalleydesigner.designformat.serializers

import io.stardewvalleydesigner.engine.entity.PlacedEntity
import io.stardewvalleydesigner.engine.geometry.Coordinate
import io.stardewvalleydesigner.engine.layer.placeIt
import io.stardewvalleydesigner.metadata.EntityDataProvider
import io.stardewvalleydesigner.metadata.EntityId
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder


internal object PlacedEntitySerializer : KSerializer<PlacedEntity<*>> {

    private val delegateSerializer = PlacedEntitySurrogate.serializer()

    @OptIn(ExperimentalSerializationApi::class)
    override val descriptor: SerialDescriptor = SerialDescriptor(
        serialName = "io.stardewvalleydesigner.engine.entity.PlacedEntity",
        original = delegateSerializer.descriptor,
    )

    override fun serialize(encoder: Encoder, value: PlacedEntity<*>) {
        delegateSerializer.serialize(
            encoder,
            value = PlacedEntitySurrogate(
                id = EntityDataProvider.entityToId.getValue(value.rectObject),
                place = value.place
            )
        )
    }

    override fun deserialize(decoder: Decoder): PlacedEntity<*> {
        val (id, place) = delegateSerializer.deserialize(decoder)
        return EntityDataProvider.entityById.getValue(id).placeIt(place)
    }
}


@Serializable
private data class PlacedEntitySurrogate(
    val id: @Serializable(with = EntityIdSerializer::class) EntityId,
    val place: @Serializable(with = CoordinateSerializer::class) Coordinate,
)

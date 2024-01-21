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

package io.stardewvalleydesigner.designformat.models

import io.stardewvalleydesigner.designformat.mappers.toEntityFlavor
import io.stardewvalleydesigner.designformat.mappers.toEntityFlavorModel
import io.stardewvalleydesigner.engine.entity.PlacedEntity
import io.stardewvalleydesigner.engine.layer.placeIt
import io.stardewvalleydesigner.metadata.*
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder


internal typealias PlacedEntityPacked = @Serializable(with = PlacedEntitySerializer::class) PlacedEntity<*>

private object PlacedEntitySerializer : KSerializer<PlacedEntity<*>> {

    private val delegateSerializer = PlacedEntityModel.serializer()

    @OptIn(ExperimentalSerializationApi::class)
    override val descriptor: SerialDescriptor = SerialDescriptor(
        serialName = "io.stardewvalleydesigner.engine.entity.PlacedEntity",
        original = delegateSerializer.descriptor
    )

    override fun serialize(encoder: Encoder, value: PlacedEntity<*>) {
        delegateSerializer.serialize(
            encoder,
            value = PlacedEntityModel(
                id = EntityDataProvider.entityToId.getValue(value.rectObject).let { (page, localId, flavor) ->
                    EntityIdModel(page, localId, flavor = flavor?.toEntityFlavorModel())
                },
                place = value.place
            )
        )
    }

    override fun deserialize(decoder: Decoder): PlacedEntity<*> {
        val (id, place) = delegateSerializer.deserialize(decoder)
        return EntityDataProvider.entityById.getValue(id.let { (page, localId, flavor) ->
            EntityId(page, localId, flavor = flavor?.toEntityFlavor())
        }).placeIt(place)
    }
}

@Serializable
private data class PlacedEntityModel(
    val id: EntityIdModel,
    val place: CoordinatePacked,
)

@Serializable
private data class EntityIdModel(
    val page: EntityPage,
    val localId: Int,
    val flavor: EntityFlavorPacked? = null,
)

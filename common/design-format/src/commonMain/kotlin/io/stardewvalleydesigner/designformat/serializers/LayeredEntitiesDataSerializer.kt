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

import io.stardewvalleydesigner.engine.layers.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder


internal object LayeredEntitiesDataSerializer : KSerializer<LayeredEntitiesData> {

    private val delegateSerializer = ListSerializer(PlacedEntitySerializer)

    @OptIn(ExperimentalSerializationApi::class)
    override val descriptor: SerialDescriptor = SerialDescriptor(
        serialName = "io.stardewvalleydesigner.engine.layers.LayeredEntitiesData",
        original = delegateSerializer.descriptor,
    )

    override fun serialize(encoder: Encoder, value: LayeredEntitiesData) {
        delegateSerializer.serialize(
            encoder,
            value = value.flatten(),
        )
    }

    override fun deserialize(decoder: Decoder): LayeredEntitiesData {
        return delegateSerializer.deserialize(decoder).layeredData()
    }
}

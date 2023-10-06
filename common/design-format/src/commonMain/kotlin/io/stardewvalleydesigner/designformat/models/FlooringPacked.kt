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

package io.stardewvalleydesigner.designformat.models

import io.stardewvalleydesigner.engine.Flooring
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder


internal typealias FlooringPacked = @Serializable(with = FlooringSerializer::class) Flooring

private object FlooringSerializer : KSerializer<Flooring> {

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "io.stardewvalleydesigner.engine.Flooring",
        kind = PrimitiveKind.INT
    )

    override fun serialize(encoder: Encoder, value: Flooring) {
        encoder.encodeInt(value.n.toInt())
    }

    override fun deserialize(decoder: Decoder): Flooring {
        return Flooring(decoder.decodeInt().toUByte())
    }
}

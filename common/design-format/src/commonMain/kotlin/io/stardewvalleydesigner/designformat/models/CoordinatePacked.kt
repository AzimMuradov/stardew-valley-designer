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

import io.stardewvalleydesigner.engine.*
import io.stardewvalleydesigner.engine.geometry.Coordinate
import io.stardewvalleydesigner.engine.geometry.xy
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder


internal typealias CoordinatePacked = @Serializable(with = CoordinateSerializer::class) Coordinate

private object CoordinateSerializer : KSerializer<Coordinate> {

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "io.stardewvalleydesigner.designformat.models.CoordinateSerializer",
        kind = PrimitiveKind.LONG
    )

    override fun serialize(encoder: Encoder, value: Coordinate) {
        encoder.encodeLong(packInts(value.x, value.y))
    }

    override fun deserialize(decoder: Decoder): Coordinate {
        val value = decoder.decodeLong()
        return xy(x = unpackInt1(value), y = unpackInt2(value))
    }
}

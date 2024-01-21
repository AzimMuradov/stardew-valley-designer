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

import io.stardewvalleydesigner.engine.entity.Color
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder


internal typealias ColorPacked = @Serializable(with = ColorSerializer::class) Color

private class ColorSerializer : KSerializer<Color> {

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "io.stardewvalleydesigner.engine.entity.Color",
        kind = PrimitiveKind.INT
    )

    override fun serialize(encoder: Encoder, value: Color) {
        encoder.encodeInt(value.pack())
    }

    override fun deserialize(decoder: Decoder): Color {
        return decoder.decodeInt().unpack()
    }
}

@Suppress("NOTHING_TO_INLINE")
private inline fun Color.pack(): Int = (r.toInt() shl 16) or (g.toInt() shl 8) or (b.toInt())

@Suppress("NOTHING_TO_INLINE")
private inline fun Int.unpack(): Color = Color(
    r = shr(16).toUByte(),
    g = shr(8).toUByte(),
    b = toUByte(),
)

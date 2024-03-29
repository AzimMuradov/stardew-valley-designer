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

import io.stardewvalleydesigner.engine.Wallpaper
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder


internal object WallpaperSerializer : KSerializer<Wallpaper> {

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "io.stardewvalleydesigner.engine.Wallpaper",
        kind = PrimitiveKind.INT,
    )

    override fun serialize(encoder: Encoder, value: Wallpaper) {
        encoder.encodeInt(value.n.toInt())
    }

    override fun deserialize(decoder: Decoder): Wallpaper {
        return Wallpaper(decoder.decodeInt().toUByte())
    }
}

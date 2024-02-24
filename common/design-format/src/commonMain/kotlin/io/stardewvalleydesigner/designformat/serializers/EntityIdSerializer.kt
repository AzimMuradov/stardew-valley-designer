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

import io.stardewvalleydesigner.engine.entity.*
import io.stardewvalleydesigner.metadata.EntityId
import io.stardewvalleydesigner.metadata.EntityPage
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*


@OptIn(ExperimentalSerializationApi::class)
internal object EntityIdSerializer : KSerializer<EntityId> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor(serialName = "EntityIdSurrogate") {
        element<EntityPage>(elementName = "page")
        element<Int>(elementName = "localId")
        element<EntityFlavor?>(elementName = "flavor", isOptional = true)
    }

    override fun serialize(encoder: Encoder, value: EntityId) = encoder.encodeStructure(descriptor) {
        encodeStringElement(
            descriptor,
            index = 0,
            value = value.page.name,
        )
        encodeIntElement(
            descriptor,
            index = 1,
            value = value.localId,
        )
        encodeNullableSerializableElement(
            descriptor,
            index = 2,
            serializer = EntityFlavorSurrogate.serializer(),
            value = value.flavor?.toEntityFlavorModel(),
        )
    }

    override fun deserialize(decoder: Decoder): EntityId = decoder.decodeStructure(descriptor) {
        var page = EntityPage.entries.first()
        var localId = -1
        var flavor: EntityFlavor? = null

        while (true) {
            when (val index = decodeElementIndex(descriptor)) {
                0 -> page = EntityPage.valueOf(decodeStringElement(descriptor, index = 0))

                1 -> localId = decodeIntElement(descriptor, index = 1)

                2 -> flavor = decodeNullableSerializableElement(
                    descriptor,
                    index = 2,
                    deserializer = EntityFlavorSurrogate.serializer(),
                )?.toEntityFlavor()

                CompositeDecoder.DECODE_DONE -> break

                else -> error("Unexpected index: $index")
            }
        }

        return@decodeStructure EntityId(page, localId, flavor)
    }


    private fun EntityFlavor.toEntityFlavorModel(): EntityFlavorSurrogate = when (this) {
        is Colors -> when (this) {
            is Colors.ChestColors -> ColorFlavorSurrogate.ChestColors(value)
            is Colors.FishPondColors -> ColorFlavorSurrogate.FishPondColors(value)
            is Colors.BlueJazzColors -> ColorFlavorSurrogate.BlueJazzColors(value)
            is Colors.FairyRoseColors -> ColorFlavorSurrogate.FairyRoseColors(value)
            is Colors.PoppyColors -> ColorFlavorSurrogate.PoppyColors(value)
            is Colors.SummerSpangleColors -> ColorFlavorSurrogate.SummerSpangleColors(value)
            is Colors.TulipColors -> ColorFlavorSurrogate.TulipColors(value)
        }

        is FarmBuildingColors -> FarmBuildingColorsFlavorSurrogate(building, roof, trim)

        is Rotations.Rotations2 -> RotationFlavorSurrogate(
            rotations = 2,
            currentRotation = ordinal,
        )

        is Rotations.Rotations4 -> RotationFlavorSurrogate(
            rotations = 4,
            currentRotation = ordinal,
        )
    }

    private fun EntityFlavorSurrogate.toEntityFlavor(): EntityFlavor = when (this) {
        is ColorFlavorSurrogate -> when (this) {
            is ColorFlavorSurrogate.ChestColors -> colorFlavorOf<Colors.ChestColors>(color)
            is ColorFlavorSurrogate.FishPondColors -> colorFlavorOf<Colors.FishPondColors>(color)
            is ColorFlavorSurrogate.BlueJazzColors -> colorFlavorOf<Colors.BlueJazzColors>(color)
            is ColorFlavorSurrogate.FairyRoseColors -> colorFlavorOf<Colors.FairyRoseColors>(color)
            is ColorFlavorSurrogate.PoppyColors -> colorFlavorOf<Colors.PoppyColors>(color)
            is ColorFlavorSurrogate.SummerSpangleColors -> colorFlavorOf<Colors.SummerSpangleColors>(color)
            is ColorFlavorSurrogate.TulipColors -> colorFlavorOf<Colors.TulipColors>(color)
        }

        is FarmBuildingColorsFlavorSurrogate -> FarmBuildingColors(building, roof, trim)

        is RotationFlavorSurrogate -> when (rotations) {
            2 -> Rotations.Rotations2.entries[currentRotation]
            4 -> Rotations.Rotations4.entries[currentRotation]
            else -> error("Unexpected rotations number: $rotations")
        }
    }

    private inline fun <reified T> colorFlavorOf(value: Color?): T where T : Enum<T>, T : Colors =
        enumValues<T>().first { it.value == value }
}


@Serializable
private sealed class EntityFlavorSurrogate

@Serializable
@SerialName("rotation")
private data class RotationFlavorSurrogate(
    val rotations: Int,
    val currentRotation: Int,
) : EntityFlavorSurrogate()

@Serializable
private sealed class ColorFlavorSurrogate : EntityFlavorSurrogate() {

    abstract val color: ColorSerializable?


    @Serializable
    @SerialName("fish-pond-color")
    data class FishPondColors(override val color: ColorSerializable) : ColorFlavorSurrogate()

    @Serializable
    @SerialName("chest-color")
    data class ChestColors(override val color: ColorSerializable? = null) : ColorFlavorSurrogate()

    @Serializable
    @SerialName("tulip-color")
    data class TulipColors(override val color: ColorSerializable) : ColorFlavorSurrogate()

    @Serializable
    @SerialName("summer-spangle-color")
    data class SummerSpangleColors(override val color: ColorSerializable) : ColorFlavorSurrogate()

    @Serializable
    @SerialName("fairy-rose-color")
    data class FairyRoseColors(override val color: ColorSerializable) : ColorFlavorSurrogate()

    @Serializable
    @SerialName("blue-jazz-color")
    data class BlueJazzColors(override val color: ColorSerializable) : ColorFlavorSurrogate()

    @Serializable
    @SerialName("poppy-color")
    data class PoppyColors(override val color: ColorSerializable) : ColorFlavorSurrogate()
}

@Serializable
@SerialName("farm-building-colors")
private data class FarmBuildingColorsFlavorSurrogate(
    val building: ColorSerializable? = null,
    val roof: ColorSerializable? = null,
    val trim: ColorSerializable? = null,
) : EntityFlavorSurrogate()

private typealias ColorSerializable = @Serializable(with = ColorSerializer::class) Color

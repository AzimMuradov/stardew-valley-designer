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
import nl.adaptivity.xmlutil.XMLConstants
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName


@Serializable
internal data class Furniture(
    @XmlSerialName(value = "type", XMLConstants.XSI_NS_URI, XMLConstants.XSI_PREFIX)
    @XmlElement(false)
    val typeAttr: String? = null,
    val name: String,
    // @SerialName("DisplayName") val displayName: String,
    // @SerialName("Name") val name2: String,
    val parentSheetIndex: Int,
    val tileLocation: Position,
    val sourceRect: SourceRect,
    @SerialName("furniture_type") val furnitureType: Int,
    val rotations: Int,
    val currentRotation: Int,
)

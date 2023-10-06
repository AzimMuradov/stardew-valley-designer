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

package io.stardewvalleydesigner.save

import nl.adaptivity.xmlutil.ExperimentalXmlUtilApi
import nl.adaptivity.xmlutil.QName
import nl.adaptivity.xmlutil.serialization.*
import nl.adaptivity.xmlutil.serialization.structure.SafeParentInfo


@OptIn(ExperimentalXmlUtilApi::class)
internal fun XmlConfig.Builder.customPolicy() {
    policy = CustomPolicy
}


@OptIn(ExperimentalXmlUtilApi::class)
private object CustomPolicy : DefaultXmlSerializationPolicy(
    pedantic = false,
    encodeDefault = XmlSerializationPolicy.XmlEncodeDefault.ALWAYS,
    unknownChildHandler = XmlConfig.IGNORING_UNKNOWN_CHILD_HANDLER
) {

    override fun effectiveOutputKind(
        serializerParent: SafeParentInfo,
        tagParent: SafeParentInfo,
        canBeAttribute: Boolean,
    ): OutputKind {
        val outputKind = super.effectiveOutputKind(serializerParent, tagParent, canBeAttribute)

        return if (
            outputKind == OutputKind.Attribute &&
            serializerParent.elementUseAnnotations.firstNotNullOfOrNull { it as? XmlElement }?.value != false
        ) {
            OutputKind.Element
        } else {
            outputKind
        }
    }

    override fun effectiveName(
        serializerParent: SafeParentInfo,
        tagParent: SafeParentInfo,
        outputKind: OutputKind,
        useName: XmlSerializationPolicy.DeclaredNameInfo,
    ): QName = useName.annotatedName
        ?: serializerParent.elementTypeDescriptor.typeQname
        ?: serialUseNameToQName(useName, tagParent.namespace)
}

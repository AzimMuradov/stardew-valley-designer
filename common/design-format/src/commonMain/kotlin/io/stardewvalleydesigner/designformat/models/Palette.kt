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

import io.stardewvalleydesigner.designformat.serializers.EntitySerializer
import io.stardewvalleydesigner.designformat.serializers.ListOfNullableEntitySerializer
import io.stardewvalleydesigner.engine.entity.Entity
import kotlinx.serialization.Serializable


@Serializable
data class Palette(
    val inUse: @Serializable(with = EntitySerializer::class) Entity<*>?,
    val hotbar: @Serializable(with = ListOfNullableEntitySerializer::class) List<Entity<*>?>,
) {

    companion object {

        fun default() = Palette(
            inUse = null,
            hotbar = List(size = 10) { null },
        )
    }
}

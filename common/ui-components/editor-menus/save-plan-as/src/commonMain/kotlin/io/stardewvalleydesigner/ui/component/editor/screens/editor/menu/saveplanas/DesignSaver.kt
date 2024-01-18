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

package io.stardewvalleydesigner.ui.component.editor.screens.editor.menu.saveplanas

import io.stardewvalleydesigner.designformat.PlanFormatConverter
import io.stardewvalleydesigner.designformat.models.Plan
import io.stardewvalleydesigner.editor.modules.map.MapState
import io.stardewvalleydesigner.engine.layers.flatten


internal object DesignSaver {

    fun serializePlanToBytes(map: MapState): ByteArray =
        PlanFormatConverter.stringify(map.toPlan()).encodeToByteArray()


    private fun MapState.toPlan(): Plan = Plan(
        entities = entities.flatten(),
        wallpaper = wallpaper,
        flooring = flooring,
        layout = layout.type,
    )
}

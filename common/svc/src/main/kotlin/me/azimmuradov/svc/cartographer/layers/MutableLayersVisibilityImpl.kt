/*
 * Copyright 2021-2021 Azim Muradov
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

package me.azimmuradov.svc.cartographer.layers

import androidx.compose.runtime.mutableStateMapOf
import me.azimmuradov.svc.engine.layer.LayerType


fun layersVisibility(): MutableLayersVisibility = MutableLayersVisibilityImpl()


private class MutableLayersVisibilityImpl : MutableLayersVisibility {

    val visibilityMap = mutableStateMapOf(
        LayerType.Floor to true,
        LayerType.FloorFurniture to true,
        LayerType.Object to true,
        LayerType.EntityWithoutFloor to true,
    )

    override val visible: Set<LayerType<*>>
        get() = visibilityMap.entries.mapNotNull { if (it.value) it.key else null }.toSet()

    override fun changeVisibility(layerType: LayerType<*>, value: Boolean) {
        visibilityMap[layerType] = value
    }
}
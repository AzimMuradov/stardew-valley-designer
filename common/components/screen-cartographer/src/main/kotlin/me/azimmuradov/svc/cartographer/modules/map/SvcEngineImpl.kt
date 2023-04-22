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

package me.azimmuradov.svc.cartographer.modules.map

import me.azimmuradov.svc.cartographer.utils.toState
import me.azimmuradov.svc.engine.*
import me.azimmuradov.svc.engine.layers.*


internal class SvcEngineImpl(engine: SvcEngine) : SvcEngine by engine {

    fun pushState(state: MapState) {
        clear()
        putAll(state.entities.toLayeredEntities())
    }

    fun pullState(selectedEntities: LayeredEntitiesData? = null) = MapState(
        entities = layers.entities,
        selectedEntities = selectedEntities ?: LayeredEntitiesData(),
        layout = layers.layout.toState()
    )
}

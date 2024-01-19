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

package io.stardewvalleydesigner.component.editor.modules.history

import io.stardewvalleydesigner.component.editor.modules.map.MapState


internal interface HistoryManager {

    val currentMapState: MapState

    val canGoBack: Boolean

    val canGoForward: Boolean


    fun register(mapState: MapState)

    fun goBackIfCan()

    fun goForwardIfCan()
}

internal val HistoryManager.state get() = HistoryState(canGoBack, canGoForward)
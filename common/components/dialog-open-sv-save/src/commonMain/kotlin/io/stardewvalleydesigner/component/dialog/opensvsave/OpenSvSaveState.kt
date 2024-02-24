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

package io.stardewvalleydesigner.component.dialog.opensvsave

import io.stardewvalleydesigner.designformat.Wrapper
import io.stardewvalleydesigner.designformat.models.Design


sealed interface OpenSvSaveState {

    data object NotOpened : OpenSvSaveState

    data object Empty : OpenSvSaveState

    data object Loading : OpenSvSaveState

    data class Loaded(
        val availableLayouts: List<Wrapper<Design>>,
        val chosenLayout: Wrapper<Design>,
    ) : OpenSvSaveState

    data object Error : OpenSvSaveState
}

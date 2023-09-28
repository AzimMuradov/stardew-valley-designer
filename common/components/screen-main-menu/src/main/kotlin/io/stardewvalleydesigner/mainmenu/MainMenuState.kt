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

package io.stardewvalleydesigner.mainmenu

import io.stardewvalleydesigner.engine.EditorEngineData


sealed interface MainMenuState {

    data object Idle : MainMenuState

    sealed interface NewPlanMenu : MainMenuState {

        data class Idle(
            val availableLayouts: List<Wrapper<EditorEngineData>>,
            val chosenLayout: Wrapper<EditorEngineData>,
        ) : NewPlanMenu
    }

    sealed interface OpenPlanMenu : MainMenuState {

        data object Empty : OpenPlanMenu

        data object Loading : OpenPlanMenu

        data class Loaded(val layout: Wrapper<EditorEngineData>) : OpenPlanMenu

        data object Error : OpenPlanMenu
    }

    sealed interface SaveLoaderMenu : MainMenuState {

        data object Empty : SaveLoaderMenu

        data object Loading : SaveLoaderMenu

        data class Loaded(
            val availableLayouts: List<Wrapper<EditorEngineData>>,
            val chosenLayout: Wrapper<EditorEngineData>,
        ) : SaveLoaderMenu

        data object Error : SaveLoaderMenu
    }


    companion object {

        fun default() = Idle
    }
}

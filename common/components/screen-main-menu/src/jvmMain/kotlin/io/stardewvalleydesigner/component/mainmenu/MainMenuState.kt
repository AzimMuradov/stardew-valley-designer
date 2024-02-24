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

package io.stardewvalleydesigner.component.mainmenu

import io.stardewvalleydesigner.designformat.models.Design


sealed interface MainMenuState {

    data object Idle : MainMenuState

    sealed interface NewDesignMenu : MainMenuState {

        data class Idle(
            val availableLayouts: List<Wrapper<Design>>,
            val chosenLayout: Wrapper<Design>,
        ) : NewDesignMenu
    }

    sealed interface OpenDesignMenu : MainMenuState {

        data object Empty : OpenDesignMenu

        data object Loading : OpenDesignMenu

        data class Loaded(
            val layout: Wrapper<Design>,
            val absolutePath: String?,
        ) : OpenDesignMenu

        data object Error : OpenDesignMenu
    }

    sealed interface SaveLoaderMenu : MainMenuState {

        data object Empty : SaveLoaderMenu

        data object Loading : SaveLoaderMenu

        data class Loaded(
            val availableLayouts: List<Wrapper<Design>>,
            val chosenLayout: Wrapper<Design>,
        ) : SaveLoaderMenu

        data object Error : SaveLoaderMenu
    }


    companion object {

        fun default() = Idle
    }
}

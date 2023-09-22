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


sealed interface MainMenuIntent {

    sealed interface NewPlanMenu : MainMenuIntent {

        data object OpenMenu : NewPlanMenu

        data class ChooseLayout(val layout: Wrapper<EditorEngineData>) : NewPlanMenu

        data object AcceptChosen : NewPlanMenu

        data object Cancel : NewPlanMenu
    }

    sealed interface SaveLoaderMenu : MainMenuIntent {

        data object OpenMenu : SaveLoaderMenu

        data class LoadSave(val path: String) : SaveLoaderMenu

        data class ChooseLayout(val layout: Wrapper<EditorEngineData>) : SaveLoaderMenu

        data object AcceptChosen : SaveLoaderMenu

        data object Cancel : SaveLoaderMenu
    }
}

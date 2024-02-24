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


sealed interface MainMenuIntent {

    sealed interface NewDesignMenu : MainMenuIntent {

        data object OpenMenu : NewDesignMenu

        data class ChooseLayout(val layout: Wrapper<Design>) : NewDesignMenu

        data object AcceptChosen : NewDesignMenu

        data object Cancel : NewDesignMenu
    }

    sealed interface OpenDesignMenu : MainMenuIntent {

        data object OpenMenu : OpenDesignMenu

        data class LoadDesign(val text: String, val absolutePath: String) : OpenDesignMenu {

            override fun toString(): String = "LoadDesign(text='${text.take(n = 100)}', absolutePath='$absolutePath')"
        }

        data object Accept : OpenDesignMenu

        data object Cancel : OpenDesignMenu
    }

    sealed interface SaveLoaderMenu : MainMenuIntent {

        data object OpenMenu : SaveLoaderMenu

        data class LoadSave(
            val text: String,
            val absolutePath: String,
        ) : SaveLoaderMenu {

            override fun toString(): String = "LoadSave(text='${text.take(n = 100)}', absolutePath='$absolutePath')"
        }

        data class ChooseLayout(val layout: Wrapper<Design>) : SaveLoaderMenu

        data object AcceptChosen : SaveLoaderMenu

        data object Cancel : SaveLoaderMenu
    }

    sealed interface UserDesignsMenu : MainMenuIntent {

        data class OpenDesign(
            val design: Design,
            val designPath: String,
        ) : UserDesignsMenu

        data class DeleteDesign(
            val designPath: String,
        ) : UserDesignsMenu
    }
}

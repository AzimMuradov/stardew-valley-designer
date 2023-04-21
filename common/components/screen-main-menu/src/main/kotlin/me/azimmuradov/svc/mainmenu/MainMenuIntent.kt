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

package me.azimmuradov.svc.mainmenu

import me.azimmuradov.svc.engine.layout.LayoutType


sealed interface MainMenuIntent {

    sealed interface NewPlan : MainMenuIntent {

        data object OpenMenu : NewPlan

        data class ChooseLayout(val layout: LayoutType) : NewPlan

        data object Cancel : NewPlan

        data object CreateNewPlan : NewPlan
    }

    sealed interface SaveData : MainMenuIntent {

        data class Load(val path: String) : SaveData
    }
}

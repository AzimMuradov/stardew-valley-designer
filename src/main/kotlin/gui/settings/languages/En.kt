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

package gui.settings.languages


object En : Language {
    override val appName: String = "Stardew Valley Cartographer"
    override val authorName: String = "Azim Muradov"

    override val currentLanguage: String = "English"

    override val menuScreen: String = "Menu"
    override val cartographerScreen: String = "Cartographer"


    object MenuButtons : Language.MenuButtons {
        override val newPlan: String = "New Plan"
        override val plans: String = "Plans"

        override val resources: String = "Resources"
        override val settings: String = "Settings"
        override val about: String = "About"
        override val donate: String = "Donate"
    }

    override val menuButtons: Language.MenuButtons = MenuButtons
}
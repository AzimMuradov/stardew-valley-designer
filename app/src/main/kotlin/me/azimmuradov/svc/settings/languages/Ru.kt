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

package me.azimmuradov.svc.settings.languages


object Ru : Language {
    override val appName: String = "Картограф Stardew Valley"
    override val authorName: String = "Азим Мурадов"

    override val currentLanguage: String = "Русский"

    override val menuScreen: String = "Меню"
    override val cartographerScreen: String = "Картограф"


    object MenuButtons : Language.MenuButtons {
        override val newPlan: String = "Новый План"
        override val plans: String = "Планы"

        override val resources: String = "Ресурсы"
        override val settings: String = "Настройки"
        override val about: String = "О приложении"
        override val donate: String = "Поддержать разработчика"
    }

    override val menuButtons: Language.MenuButtons = MenuButtons
}
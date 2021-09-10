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

import me.azimmuradov.svc.components.cartographer.menus.entityselection.EntitySelectionMenuTitle
import me.azimmuradov.svc.engine.llsvc.entity.ids.SvcEntityId


sealed interface Language {

    val appName: String
    val authorName: String

    val currentLanguage: String

    val menuScreen: String
    val cartographerScreen: String


    sealed interface MenuButtons {
        val newPlan: String
        val plans: String

        val resources: String
        val settings: String
        val about: String
        val donate: String
    }

    val menuButtons: MenuButtons


    // Cartographer Screen

    sealed interface Cartographer {

        fun menuTitle(x: EntitySelectionMenuTitle): String

        fun entity(x: SvcEntityId<*>): String
    }

    val cartographer: Cartographer
}
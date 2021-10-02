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

package me.azimmuradov.svc.settings.wordlists

import me.azimmuradov.svc.cartographer.toolkit.ToolType
import me.azimmuradov.svc.components.screens.cartographer.menus.EntitySelectionRoot
import me.azimmuradov.svc.engine.entity.Entity
import me.azimmuradov.svc.engine.layer.LayerType


/**
 * WordList profile.
 */
sealed interface WordList {

    /**
     * Application name.
     */
    val application: String

    /**
     * Author full name.
     */
    val author: String


    // Cartographer Screen

    /**
     * Menu title by its root.
     */
    fun menuTitle(root: EntitySelectionRoot): String

    /**
     * Get entity name.
     */
    fun entity(e: Entity<*>): String

    /**
     * Get tool name by its type.
     */
    fun tool(type: ToolType?): String

    /**
     * Layer by its type.
     */
    fun layer(type: LayerType<*>): String
}
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

package me.azimmuradov.svc.settings.wordlists

import me.azimmuradov.svc.cartographer.menus.EntitySelectionRoot
import me.azimmuradov.svc.cartographer.modules.toolkit.ShapeType
import me.azimmuradov.svc.cartographer.modules.toolkit.ToolType
import me.azimmuradov.svc.engine.entity.Entity
import me.azimmuradov.svc.engine.layer.LayerType
import me.azimmuradov.svc.engine.layout.LayoutType


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


    // General

    val ok: String

    val cancel: String

    val choose: String

    val load: String

    fun layout(type: LayoutType): String


    // Main menu screen

    val buttonStardewValleyText: String

    val buttonStardewValleyTooltip: String

    val buttonSwitchThemeText: String

    val buttonSwitchThemeTooltip: String

    val buttonSettingsText: String

    val buttonSettingsTooltip: String

    val buttonDonateText: String

    val buttonDonateTooltip: String

    val buttonHelpText: String

    val buttonHelpTooltip: String


    val buttonNewPlanText: String

    val newPlanWindowTitle: String

    val buttonOpenPlanText: String

    val buttonSaveImportText: String

    val saveImportWindowTitle: String

    val saveImportCurrentDirectoryLabel: String

    val saveImportTextFieldLabel: String

    val saveImportPlaceholder: String

    val saveImportPlaceholderError: String

    val buttonSearchForAPlanText: String


    // Cartographer Screen

    val optionShowAxis: String

    val optionShowGrid: String

    /**
     * Get menu title by its root.
     */
    fun menuTitle(root: EntitySelectionRoot): String

    /**
     * Get entity name.
     */
    fun entity(e: Entity<*>): String

    val buttonMakeScreenshotTooltip: String

    /**
     * Get tool name by its type.
     */
    fun tool(type: ToolType?): String

    /**
     * Get shape name by its type.
     */
    fun shape(type: ShapeType?): String

    val notAvailableForThisTool: String

    /**
     * Get layer name by its type.
     */
    fun layer(type: LayerType<*>): String

    val wallpapersTabTitle: String

    val flooringTabTitle: String
}

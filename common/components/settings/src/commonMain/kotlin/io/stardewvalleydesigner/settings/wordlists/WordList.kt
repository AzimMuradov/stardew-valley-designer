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

package io.stardewvalleydesigner.settings.wordlists

import io.stardewvalleydesigner.component.editor.menus.EntitySelectionRoot
import io.stardewvalleydesigner.component.editor.modules.toolkit.ShapeType
import io.stardewvalleydesigner.component.editor.modules.toolkit.ToolType
import io.stardewvalleydesigner.designformat.models.OptionsItemValue
import io.stardewvalleydesigner.engine.entity.Entity
import io.stardewvalleydesigner.engine.layer.LayerType
import io.stardewvalleydesigner.engine.layout.LayoutType


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


    fun screenTitle(subTitle: String) = "$application | $subTitle"

    val mainMenuTitle: String


    // General

    val ok: String

    val yes: String

    val no: String

    val delete: String

    val farm: String

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

    val buttonInfoText: String

    val buttonInfoTooltip: String

    val infoApplicationDescription: String

    val infoApplicationAuthor: String

    val infoApplicationSource: String

    val infoCurrentVersion: String

    val infoChangelog: String

    val infoConcernedApeTwitter: String

    val infoSVText1: String

    val infoSVText2: String

    val infoSVText3: String

    val infoBugs: String


    val buttonNewDesignText: String

    val newDesignWindowTitle: String


    val buttonOpenDesignText: String

    val openDesignWindowTitle: String

    val openDesignPlaceholder: String

    val openDesignPlaceholderError: String

    val openDesignSelectDesignButton: String

    val openDesignSelectDesignTitle: String

    val openDesignSelectDesignPlaceholder: String


    val buttonSaveImportText: String

    val saveImportWindowTitle: String

    val saveImportPlaceholder: String

    val saveImportPlaceholderError: String

    val saveImportSelectSaveFileButton: String

    val saveImportSelectSaveFileTitle: String

    val saveImportSelectSaveFilePlaceholder: String


    val chooseLayout: String


    val designCardFilename: String

    val designCardDate: String

    val designCardPlayerName: String

    val designCardFarmName: String

    val designCardLayout: String

    val designCardOpen: String

    val designCardDeleteDialogTitle: String

    val designCardDeleteDialogText: String


    // TODO : val buttonSearchForADesignText: String


    // Editor Screen

    /**
     * Get menu title by its root.
     */
    fun menuTitle(root: EntitySelectionRoot): String

    fun optionTitle(option: OptionsItemValue): String

    /**
     * Get entity name.
     */
    fun entity(e: Entity<*>): String


    val playerNamePlaceholder: String

    val farmNamePlaceholder: String


    val buttonSaveDesignAsImageTooltip: String

    val saveDesignAsImageTitle: String

    fun saveDesignAsImageNotificationMessage(path: String?): String


    val buttonSaveDesignTooltip: String

    val saveDesignNotificationMessage: String


    val buttonSaveDesignAsTooltip: String

    val saveDesignAsTitle: String

    fun saveDesignAsNotificationMessage(path: String?): String


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

    val objectCounterTitle: String

    val width: String

    val height: String

    val start: String

    val end: String
}

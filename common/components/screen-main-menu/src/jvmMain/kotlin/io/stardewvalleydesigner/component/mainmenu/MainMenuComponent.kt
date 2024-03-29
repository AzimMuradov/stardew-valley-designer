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

import io.stardewvalleydesigner.component.dialog.newdesign.NewDesignComponent
import io.stardewvalleydesigner.component.dialog.opendesign.OpenDesignComponent
import io.stardewvalleydesigner.component.dialog.opensvsave.OpenSvSaveComponent
import io.stardewvalleydesigner.designformat.models.Design


interface MainMenuComponent {

    val store: MainMenuStore

    val newDesignComponent: NewDesignComponent

    val openDesignComponent: OpenDesignComponent

    val openSvSaveComponent: OpenSvSaveComponent

    val onEditorScreenCall: (Design, designPath: String?) -> Unit
}

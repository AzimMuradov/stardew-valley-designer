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

package io.stardewvalleydesigner.component.root.children

import io.stardewvalleydesigner.LoggerUtils.createLoggerAwareStoreFactory
import io.stardewvalleydesigner.component.mainmenu.MainMenuComponent
import io.stardewvalleydesigner.component.mainmenu.MainMenuStoreFactory
import io.stardewvalleydesigner.engine.EditorEngineData


internal class MainMenuComponentImpl(
    override val onEditorScreenCall: (EditorEngineData, planPath: String?) -> Unit,
) : MainMenuComponent {

    override val store = MainMenuStoreFactory(createLoggerAwareStoreFactory()).create(onEditorScreenCall)
}

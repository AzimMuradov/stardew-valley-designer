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

package io.stardewvalleydesigner.components

import io.stardewvalleydesigner.editor.EditorComponent
import io.stardewvalleydesigner.engine.EditorEngineData
import io.stardewvalleydesigner.mainmenu.MainMenuComponent
import kotlinx.coroutines.flow.StateFlow


interface RootComponent {

    val children: StateFlow<Children>

    class Children(
        val mainMenuComponent: MainMenuComponent,
        val editorComponents: List<EditorComponent>,
    )


    fun createEditorComponent(data: EditorEngineData)

    fun destroyEditorComponent(component: EditorComponent)
}

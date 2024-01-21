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

package io.stardewvalleydesigner.component.root

import io.stardewvalleydesigner.component.editor.EditorComponent
import io.stardewvalleydesigner.component.editor.EditorState
import io.stardewvalleydesigner.component.root.RootComponent.Children
import io.stardewvalleydesigner.component.root.children.EditorComponentImpl
import io.stardewvalleydesigner.component.root.children.MainMenuComponentImpl
import io.stardewvalleydesigner.engine.EditorEngineData
import io.stardewvalleydesigner.engine.generateEngine
import kotlinx.coroutines.flow.*


fun rootComponent(): RootComponent = RootComponentImpl()


private class RootComponentImpl : RootComponent {

    private val _children: MutableStateFlow<Children> = MutableStateFlow(
        Children(
            mainMenuComponent = MainMenuComponentImpl(
                onEditorScreenCall = this::createEditorComponent,
            ),
            editorComponents = emptyList(),
        )
    )

    override val children: StateFlow<Children> = _children


    override fun createEditorComponent(data: EditorEngineData, planPath: String?) = _children.update { children ->
        Children(
            mainMenuComponent = children.mainMenuComponent,
            editorComponents = children.editorComponents + EditorComponentImpl(
                initialState = EditorState.from(data.generateEngine(), planPath),
            ),
        )
    }

    override fun destroyEditorComponent(component: EditorComponent) = _children.update { children ->
        Children(
            mainMenuComponent = children.mainMenuComponent,
            editorComponents = children.editorComponents - component,
        )
    }
}

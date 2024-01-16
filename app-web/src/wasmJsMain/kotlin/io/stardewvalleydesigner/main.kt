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

package io.stardewvalleydesigner

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import io.stardewvalleydesigner.editor.*
import io.stardewvalleydesigner.engine.layout.LayoutType
import io.stardewvalleydesigner.engine.layout.LayoutsProvider
import io.stardewvalleydesigner.settings.Lang
import io.stardewvalleydesigner.ui.component.editor.res.WithImageResources
import io.stardewvalleydesigner.ui.component.editor.screens.EditorScreen
import io.stardewvalleydesigner.ui.component.settings.WithSettings
import io.stardewvalleydesigner.ui.component.themes.AppTheme
import io.stardewvalleydesigner.ui.component.themes.ThemeVariant
import org.jetbrains.compose.resources.ExperimentalResourceApi


@OptIn(ExperimentalComposeUiApi::class, ExperimentalResourceApi::class)
fun main() {
    CanvasBasedWindow("Stardew Valley Designer") {
        AppTheme(themeVariant = ThemeVariant.LIGHT) {
            WithSettings(lang = Lang.EN) {
                WithImageResources(themeVariant = ThemeVariant.LIGHT) {
                    EditorScreen(
                        component = EditorComponentImpl(EditorState.default(LayoutsProvider.layoutOf(LayoutType.BigShed)))
                    )
                }
            }
        }
    }
}

internal class EditorComponentImpl(
    initialState: EditorState,
) : EditorComponent {

    override val store = EditorStoreFactory(LoggerUtils.createLoggerAwareStoreFactory()).create(initialState)
}

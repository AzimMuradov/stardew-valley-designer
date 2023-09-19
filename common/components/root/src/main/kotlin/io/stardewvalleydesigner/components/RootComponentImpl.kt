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

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.parcelable.Parcelable
import io.stardewvalleydesigner.components.RootComponent.Child
import io.stardewvalleydesigner.components.RootComponent.Child.EditorChild
import io.stardewvalleydesigner.components.RootComponent.Child.MainMenuChild
import io.stardewvalleydesigner.components.RootComponentImpl.Config.EditorConfig
import io.stardewvalleydesigner.components.RootComponentImpl.Config.MainMenuConfig
import io.stardewvalleydesigner.components.screens.editor.EditorComponentImpl
import io.stardewvalleydesigner.components.screens.menu.MainMenuComponentImpl
import io.stardewvalleydesigner.engine.EditorEngine


fun rootComponent(): RootComponent = RootComponentImpl(
    componentContext = DefaultComponentContext(
        lifecycle = LifecycleRegistry(),
    ),
)


private class RootComponentImpl(
    componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    private val stack = childStack(
        source = navigation,
        initialConfiguration = MainMenuConfig,
        childFactory = ::child,
    )

    override val childStack: Value<ChildStack<*, Child>> = stack


    override fun onEditorScreenCall(engine: EditorEngine) {
        navigation.push(configuration = EditorConfig(engine))
    }

    override fun onEditorScreenReturn() {
        navigation.pop()
    }


    private fun child(config: Config, componentContext: ComponentContext): Child = when (config) {
        MainMenuConfig -> MainMenuChild(
            MainMenuComponentImpl(
                this::onEditorScreenCall,
            )
        )

        is EditorConfig -> EditorChild(
            EditorComponentImpl(
                engine = config.engine,
                onEditorScreenReturn = this::onEditorScreenReturn,
            )
        )
    }

    private sealed class Config : Parcelable {
        data object MainMenuConfig : Config()
        data class EditorConfig(val engine: EditorEngine) : Config()
    }
}

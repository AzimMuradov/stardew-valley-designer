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

package io.stardewvalleydesigner.ui.component.settings

import androidx.compose.runtime.*
import io.stardewvalleydesigner.settings.Lang
import io.stardewvalleydesigner.settings.SettingsInterpreter
import io.stardewvalleydesigner.settings.wordlists.WordList


object GlobalSettings {

    val strings: WordList @Composable get() = SettingsInterpreter.wordList(lang)


    private val lang: Lang @Composable get() = LocalLang.current
}

@Composable
fun WithSettings(
    lang: Lang = Lang.EN,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalLang provides lang,
        content = content
    )
}


val LocalLang = staticCompositionLocalOf<Lang> {
    error("No language provided")
}

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

package io.stardewvalleydesigner.kmplib.clipboard

import kotlinx.browser.window


// TODO : Handle promises as coroutines

actual object Clipboard {

    actual fun copyToClipboard(text: String) {
        window.navigator.clipboard.writeText(text)
    }

    actual fun pasteFromClipboard(): String? = TODO()
}

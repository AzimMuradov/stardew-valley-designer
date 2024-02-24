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

package io.stardewvalleydesigner.component.editor.modules.options

import io.stardewvalleydesigner.component.editor.utils.Reduce
import io.stardewvalleydesigner.designformat.models.Options
import io.stardewvalleydesigner.component.editor.EditorIntent.Options as OptionsIntent


val reduce: Reduce<Options, OptionsIntent> = { intent ->
    when (intent) {
        is OptionsIntent.Toggle -> copy(
            toggleables = toggleables.toMutableMap().apply {
                put(intent.toggleable, intent.value)
            }
        )
    }
}

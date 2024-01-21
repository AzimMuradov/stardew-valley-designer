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

package io.stardewvalleydesigner.component.editor.modules.palette

import io.stardewvalleydesigner.component.editor.utils.Reduce
import io.stardewvalleydesigner.component.editor.utils.replace
import io.stardewvalleydesigner.component.editor.EditorIntent.Palette as PaletteIntent


val reduce: Reduce<PaletteState, PaletteIntent> = { intent ->
    when (intent) {
        is PaletteIntent.AddToInUse -> copy(inUse = intent.entity)
        is PaletteIntent.AddToHotbar -> copy(hotbar = hotbar.replace(intent.i.toInt(), intent.entity))
        PaletteIntent.ClearInUse -> copy(inUse = null)
        is PaletteIntent.ClearHotbarCell -> copy(hotbar = hotbar.replace(intent.i.toInt(), null))
        PaletteIntent.Clear -> copy(inUse = null, hotbar = hotbar.map { null })
    }
}

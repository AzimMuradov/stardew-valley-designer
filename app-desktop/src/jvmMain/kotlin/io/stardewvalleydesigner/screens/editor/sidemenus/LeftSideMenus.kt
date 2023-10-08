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

package io.stardewvalleydesigner.screens.editor.sidemenus

import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.stardewvalleydesigner.cmplib.sidemenus.FixedSideMenus
import io.stardewvalleydesigner.editor.menus.OptionsItemValue.Toggleable
import io.stardewvalleydesigner.editor.modules.options.OptionsState
import io.stardewvalleydesigner.editor.modules.palette.PaletteState
import io.stardewvalleydesigner.editor.modules.toolkit.ToolkitState
import io.stardewvalleydesigner.engine.layers.LayeredEntitiesData
import io.stardewvalleydesigner.engine.layers.isEmpty
import io.stardewvalleydesigner.editor.EditorIntent as Intent


@Composable
fun LeftSideMenus(
    toolkit: ToolkitState,
    palette: PaletteState,
    entities: LayeredEntitiesData,
    options: OptionsState,
    width: Dp,
    intentConsumer: (Intent) -> Unit,
) {
    FixedSideMenus(width) {
        menu { Toolbar(toolkit, intentConsumer) }
        menu { Palette(palette, intentConsumer) }
        if (options.toggleables.getValue(Toggleable.ShowObjectCounter) && !entities.isEmpty()) {
            menu(modifier = Modifier.heightIn(max = 300.dp)) {
                ObjectCounter(entities)
            }
        }
    }
}

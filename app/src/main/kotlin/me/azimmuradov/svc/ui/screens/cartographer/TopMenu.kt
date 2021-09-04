/*
 * Copyright 2021-2021 Azim Muradov
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

package me.azimmuradov.svc.ui.screens.cartographer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import me.azimmuradov.svc.components.cartographer.Cartographer
import me.azimmuradov.svc.components.cartographer.menus.BuildingsMenu
import me.azimmuradov.svc.components.cartographer.menus.MenuRoot
import me.azimmuradov.svc.components.cartographer.menus.filterElements
import me.azimmuradov.svc.engine.impl.entity.CartographerEntityType
import me.azimmuradov.svc.settings.languages.Language
import me.azimmuradov.svc.ui.utils.CascadingDropdownMenu


@Composable
fun TopMenu(component: Cartographer, modifier: Modifier = Modifier) {
    val models by component.models.subscribeAsState()
    val language = models.settings.language

    Row(modifier) {
        TopMenuCell(languageCartographer = language.cartographer, root = BuildingsMenu)
        // TopMenuCell(root = BuildingsMenu)
        // TopMenuCell(root = BuildingsMenu)
        // TopMenuCell(root = BuildingsMenu)
        // TopMenuCell(root = BuildingsMenu)
    }
}


@Composable
private fun RowScope.TopMenuCell(
    languageCartographer: Language.Cartographer,
    root: MenuRoot,
    disallowedTypes: List<CartographerEntityType>? = null,
) {
    val filteredRoot = disallowedTypes?.let { root.filterElements(it) } ?: root

    CascadingDropdownMenu(
        root = filteredRoot,
        modifier = Modifier
            .fillMaxHeight().weight(1f)
            .padding(5.dp)
            .background(color = Color.Cyan),
        cellModifier = Modifier
            .padding(horizontal = 10.dp, vertical = 5.dp),
        titleCellContent = { title ->
            Text(text = languageCartographer.menuTitle(title), modifier = Modifier.padding(5.dp))
        },
        valueCellContent = { value ->
            // TODO
            // Canvas(modifier = Modifier.aspectRatio(ratio = 1f).weight(2f)) {
            //     drawSpriteById(
            //         id = value.id,
            //         step = size.height,
            //     )
            // }
            Text(
                text = languageCartographer.entity(value.id),
                modifier = Modifier.weight(9f).padding(start = 5.dp),
                color = Color.Black,
            )
        },
    )
}
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

package me.azimmuradov.svc.screens.cartographer.sidemenus

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun SideMenus(
    modifier: Modifier = Modifier,
    content: SideMenusBuilder.() -> Unit,
) {
    Column(
        modifier = modifier.padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        val menus = SideMenusBuilder(columnScope = this).apply(content).build()

        for (menu in menus) {
            menu()
        }
    }
}


class SideMenusBuilder internal constructor(columnScope: ColumnScope) : ColumnScope by columnScope {

    fun menu(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
        menus += { SideMenuCard(modifier, content) }
    }

    internal fun build(): List<@Composable () -> Unit> = menus

    private val menus: MutableList<@Composable () -> Unit> = mutableListOf()
}

@Composable
private fun SideMenuCard(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = 4.dp,
        content = content,
    )
}
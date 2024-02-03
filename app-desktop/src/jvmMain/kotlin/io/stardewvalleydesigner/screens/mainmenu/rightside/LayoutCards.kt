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

package io.stardewvalleydesigner.screens.mainmenu.rightside

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.dirs.UserDirectories
import io.stardewvalleydesigner.designformat.DesignFormatConverter
import java.io.File
import kotlin.io.path.Path
import java.io.File.separator as sep


@Composable
fun LayoutCards() {
    val files = File("${UserDirectories.get().documentDir}${sep}Stardew Valley Designer${sep}")
        .listFiles() ?: emptyArray()

    val paths = files.map { it.path }

    val huh by remember(paths) {
        mutableStateOf(
            paths.mapNotNull { path ->
                try {
                    DesignFormatConverter.parse(File(path).readText())
                } catch (e: Exception) {
                    null
                }?.let {
                    it to Path(path).fileName
                }
            }
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.surface),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(huh) { (plan, name) ->
            // TODO : LayoutCard
        }
    }
}

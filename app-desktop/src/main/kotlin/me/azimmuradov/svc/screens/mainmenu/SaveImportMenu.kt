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

package me.azimmuradov.svc.screens.mainmenu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.rememberDialogState
import cafe.adriel.bonsai.core.Bonsai
import cafe.adriel.bonsai.filesystem.FileSystemBonsaiStyle
import cafe.adriel.bonsai.filesystem.FileSystemTree
import me.azimmuradov.svc.utils.GlobalSettings
import kotlin.io.path.*


@Composable
fun RowScope.SaveImportMenu() {
    val wordList = GlobalSettings.strings

    var isDialogVisible by remember { mutableStateOf(false) }

    PlanMenuButton(
        text = "Import from save",
        icon = painterResource(resourcePath = "main-menu/save_FILL1_wght400_GRAD0_opsz48.svg"),
        onClick = { isDialogVisible = true }
    )

    Dialog(
        onCloseRequest = { isDialogVisible = false },
        state = rememberDialogState(
            size = DpSize(width = 800.dp, height = 600.dp)
        ),
        visible = isDialogVisible,
        title = "Choose from available layouts",
        resizable = false
    ) {
        Box(Modifier.fillMaxSize().padding(16.dp), Alignment.Center) {
            Row(Modifier.fillMaxSize(), Arrangement.spacedBy(16.dp)) {
                Column(
                    modifier = Modifier.fillMaxHeight().weight(1f)
                ) {
                    val tree = FileSystemTree(rootPath = Path(System.getProperty("user.home")))

                    var path by remember { mutableStateOf("") }

                    Bonsai(
                        tree = tree,
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        onClick = {
                            val p = it.content.toNioPath()
                            if (p.isRegularFile()) path = p.absolutePathString()
                        },
                        onDoubleClick = null,
                        onLongClick = null,
                        style = FileSystemBonsaiStyle()
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextField(
                            value = path,
                            onValueChange = { path = it },
                            modifier = Modifier.weight(1f).height(48.dp),
                            singleLine = true
                        )
                        Button(
                            onClick = {},
                            modifier = Modifier.width(100.dp).height(36.dp),
                            enabled = path.isNotBlank()
                        ) {
                            Text(text = wordList.ok)
                        }
                    }
                }

                Box(modifier = Modifier.fillMaxHeight().width(1.dp).background(Color.Black))

                LayoutChoosingMenu(
                    modifier = Modifier.fillMaxHeight().weight(1f),
                    layouts = null,
                    placeholder = "",
                    chosenLayout = null,
                    okText = wordList.ok,
                    cancelText = wordList.cancel,
                    onLayoutChosen = {},
                    onOk = {},
                    onCancel = {}
                )
            }
        }
    }
}

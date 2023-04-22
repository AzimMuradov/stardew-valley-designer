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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.rememberDialogState
import cafe.adriel.bonsai.core.Bonsai
import cafe.adriel.bonsai.core.tree.Tree
import cafe.adriel.bonsai.filesystem.FileSystemBonsaiStyle
import cafe.adriel.bonsai.filesystem.FileSystemTree
import me.azimmuradov.svc.mainmenu.MainMenuIntent
import me.azimmuradov.svc.mainmenu.MainMenuState
import me.azimmuradov.svc.utils.GlobalSettings
import okio.Path
import kotlin.io.path.*


@Composable
fun RowScope.SaveImportMenu(
    state: MainMenuState,
    intentConsumer: (MainMenuIntent) -> Unit,
) {
    val wordList = GlobalSettings.strings

    PlanMenuButton(
        text = wordList.buttonSaveImportText,
        icon = painterResource(resourcePath = "main-menu/save_FILL1_wght400_GRAD0_opsz48.svg"),
        onClick = { intentConsumer(MainMenuIntent.SaveLoaderMenu.OpenMenu) }
    )

    Dialog(
        onCloseRequest = { intentConsumer(MainMenuIntent.SaveLoaderMenu.Cancel) },
        state = rememberDialogState(
            size = DpSize(width = 900.dp, height = 600.dp)
        ),
        visible = state is MainMenuState.SaveLoaderMenu,
        title = wordList.saveImportWindowTitle,
        resizable = false
    ) {
        Box(Modifier.fillMaxSize().padding(16.dp), Alignment.Center) {
            Row(Modifier.fillMaxSize(), Arrangement.spacedBy(16.dp)) {
                SaveFileLoader(intentConsumer)

                Box(modifier = Modifier.fillMaxHeight().width(1.dp).background(Color.Black))

                LayoutChoosingMenu(
                    modifier = Modifier.fillMaxHeight().weight(1f),
                    layouts = (state as? MainMenuState.SaveLoaderMenu.Idle)?.availableLayouts,
                    placeholder = if (state !is MainMenuState.SaveLoaderMenu.Error) {
                        wordList.saveImportPlaceholder
                    } else {
                        wordList.saveImportPlaceholderError
                    },
                    chosenLayout = (state as? MainMenuState.SaveLoaderMenu.Idle)?.chosenLayout,
                    okText = wordList.choose,
                    cancelText = wordList.cancel,
                    onLayoutChosen = { intentConsumer(MainMenuIntent.SaveLoaderMenu.ChooseLayout(it)) },
                    onOk = { intentConsumer(MainMenuIntent.SaveLoaderMenu.AcceptChosen) },
                    onCancel = { intentConsumer(MainMenuIntent.SaveLoaderMenu.Cancel) }
                )
            }
        }
    }
}


@Composable
private fun RowScope.SaveFileLoader(intentConsumer: (MainMenuIntent.SaveLoaderMenu) -> Unit) {
    val wordList = GlobalSettings.strings

    Column(
        modifier = Modifier.fillMaxHeight().weight(1f),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val tree = FileSystemTree(
            rootPath = Path("${System.getProperty("user.home")}/.config/StardewValley/Saves"),
            selfInclude = true
        ).apply(Tree<Path>::expandAll)

        var path by remember { mutableStateOf("") }

        Box(Modifier.fillMaxWidth().weight(1f)) {
            Bonsai(
                tree = tree,
                modifier = Modifier.fillMaxSize(),
                onClick = {
                    val p = it.content.toNioPath()
                    if (p.isRegularFile()) path = p.absolutePathString()
                },
                onDoubleClick = null,
                onLongClick = null,
                style = FileSystemBonsaiStyle()
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().height(56.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = path,
                onValueChange = { path = it },
                modifier = Modifier.weight(1f).height(56.dp),
                textStyle = MaterialTheme.typography.body2,
                label = { Text(wordList.saveImportTextFieldLabel) },
                // leadingIcon = {
                //     Icon(
                //         imageVector = Icons.Filled.Save,
                //         contentDescription = null,
                //         modifier = Modifier
                //             .pointerHoverIcon(PointerIcon.Hand)
                //             .size(24.dp)
                //             .clickable(
                //                 interactionSource = MutableInteractionSource(),
                //                 indication = rememberRipple(
                //                     bounded = false,
                //                     radius = 18.dp,
                //                     color = Color.Black
                //                 )
                //             ) {
                //                 path = "${System.getProperty("user.home")}/.config/StardewValley/Saves"
                //             }
                //     )
                // },
                trailingIcon = {
                    val interactionSource = remember { MutableInteractionSource() }

                    Icon(
                        imageVector = Icons.Filled.Cancel,
                        contentDescription = null,
                        modifier = Modifier
                            .pointerHoverIcon(PointerIcon.Hand)
                            .size(24.dp)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = rememberRipple(
                                    bounded = false,
                                    radius = 18.dp,
                                    color = Color.Black
                                )
                            ) {
                                path = ""
                            }
                    )
                },
                singleLine = true
            )
            Button(
                onClick = { intentConsumer(MainMenuIntent.SaveLoaderMenu.LoadSave(path)) },
                modifier = Modifier.height(36.dp),
                enabled = path.isNotBlank()
            ) {
                Text(wordList.load)
            }
        }
    }
}

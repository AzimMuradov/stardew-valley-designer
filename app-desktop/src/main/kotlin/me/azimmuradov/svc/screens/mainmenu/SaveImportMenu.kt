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
import androidx.compose.material.TextFieldDefaults.IconOpacity
import androidx.compose.material.TextFieldDefaults.UnfocusedIndicatorLineOpacity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.rememberDialogState
import cafe.adriel.bonsai.core.Bonsai
import cafe.adriel.bonsai.core.tree.Tree
import cafe.adriel.bonsai.filesystem.FileSystemBonsaiStyle
import cafe.adriel.bonsai.filesystem.FileSystemTree
import kotlinx.coroutines.delay
import me.azimmuradov.svc.mainmenu.MainMenuIntent
import me.azimmuradov.svc.mainmenu.MainMenuState
import me.azimmuradov.svc.utils.GlobalSettings
import mu.KotlinLogging
import okio.Path
import okio.Path.Companion.toPath
import java.io.File
import java.util.*
import kotlin.io.path.*
import kotlin.time.Duration.Companion.milliseconds
import java.io.File.separator as sep


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
        Row(
            modifier = Modifier.fillMaxSize()
                .background(MaterialTheme.colors.background)
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            SaveFileLoader(intentConsumer)

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
                onCancel = { intentConsumer(MainMenuIntent.SaveLoaderMenu.Cancel) },
                isLoading = state is MainMenuState.SaveLoaderMenu.Loading
            )
        }
    }
}


@Composable
private fun RowScope.SaveFileLoader(intentConsumer: (MainMenuIntent.SaveLoaderMenu) -> Unit) {
    val wordList = GlobalSettings.strings

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .weight(1f)
            .shadow(
                elevation = 4.dp,
                shape = MaterialTheme.shapes.large
            )
            .background(MaterialTheme.colors.secondary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val roots by remember { mutableStateOf(File.listRoots()) }
        val root by remember { mutableStateOf(roots.first().absolutePath) }

        LaunchedEffect(Unit) {
            KotlinLogging.logger(name = "log").info { roots.asList() }
        }

        var currentDir by remember { mutableStateOf(savePath()) }

        val tree = PathTree(currentDir).apply {
            LaunchedEffect(key1 = this) {
                collapseRoot()
                while (nodes.size != 1) delay(10.milliseconds)
                expandRoot()
            }
        }

        var path by remember { mutableStateOf("") }


        Row(
            modifier = Modifier.fillMaxWidth().height(56.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = currentDir,
                onValueChange = {},
                modifier = Modifier.weight(1f).height(56.dp),
                readOnly = true,
                label = { Text(wordList.saveImportCurrentDirectoryLabel) },
                trailingIcon = {
                    val interactionSource = remember(::MutableInteractionSource)

                    Icon(
                        imageVector = Icons.Filled.ArrowUpward,
                        contentDescription = null,
                        modifier = Modifier
                            .pointerHoverIcon(PointerIcon.Hand)
                            .size(24.dp)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = rememberRipple(
                                    bounded = false,
                                    radius = 18.dp,
                                    color = Color.White
                                )
                            ) {
                                currentDir = currentDir.toPath().parent?.toNioPath()?.absolutePathString() ?: currentDir
                            }
                    )
                },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = MaterialTheme.colors.onPrimary,
                    backgroundColor = MaterialTheme.colors.secondaryVariant,
                    cursorColor = MaterialTheme.colors.onPrimary,
                    focusedIndicatorColor = MaterialTheme.colors.onPrimary.copy(ContentAlpha.high),
                    unfocusedIndicatorColor = MaterialTheme.colors.onPrimary.copy(UnfocusedIndicatorLineOpacity),
                    trailingIconColor = MaterialTheme.colors.onPrimary.copy(IconOpacity),
                    focusedLabelColor = MaterialTheme.colors.onPrimary.copy(ContentAlpha.high),
                    unfocusedLabelColor = MaterialTheme.colors.onPrimary.copy(ContentAlpha.medium),
                )
            )
            IconButton(onClick = { currentDir = System.getProperty("user.home") }) {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = null,
                    modifier = Modifier.pointerHoverIcon(PointerIcon.Hand),
                    tint = Color.White
                )
            }
            IconButton(onClick = { currentDir = savePath() }) {
                Icon(
                    imageVector = Icons.Filled.Save,
                    contentDescription = null,
                    modifier = Modifier.pointerHoverIcon(PointerIcon.Hand),
                    tint = Color.White
                )
            }
        }
        Box(
            Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(MaterialTheme.colors.surface)
                .padding(8.dp)
        ) {
            Bonsai(
                tree = tree,
                modifier = Modifier.fillMaxSize(),
                onClick = {
                    val p = it.content.toNioPath()
                    when {
                        p.isRegularFile() -> path = p.absolutePathString()
                        p.isDirectory() -> {
                            currentDir = if (p.absolutePathString() != root) {
                                p.absolutePathString()
                            } else {
                                currentDir.toPath().parent?.toString() ?: currentDir
                            }
                        }
                    }
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
                label = { Text(wordList.saveImportTextFieldLabel) },
                trailingIcon = {
                    val interactionSource = remember(::MutableInteractionSource)

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
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = MaterialTheme.colors.onSecondary,
                    backgroundColor = MaterialTheme.colors.onPrimary,
                    cursorColor = MaterialTheme.colors.onSecondary,
                    focusedIndicatorColor = MaterialTheme.colors.onSecondary.copy(ContentAlpha.high),
                    unfocusedIndicatorColor = MaterialTheme.colors.onSecondary.copy(UnfocusedIndicatorLineOpacity),
                    trailingIconColor = MaterialTheme.colors.secondaryVariant.copy(IconOpacity),
                    focusedLabelColor = MaterialTheme.colors.secondaryVariant.copy(ContentAlpha.high),
                    unfocusedLabelColor = MaterialTheme.colors.secondaryVariant.copy(ContentAlpha.medium),
                )
            )
            Button(
                onClick = { intentConsumer(MainMenuIntent.SaveLoaderMenu.LoadSave(path)) },
                modifier = Modifier.height(36.dp),
                enabled = path.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.surface,
                    contentColor = MaterialTheme.colors.primary
                )
            ) {
                Text(
                    wordList.load,
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                )
            }
        }
    }
}


// Shit code to force bonsai to recompose.
// Bonsai should fix that someday...

@Composable
private fun PathTree(root: String): Tree<Path> {
    var rooty by remember { mutableStateOf("") }
    return if (root != rooty) {
        rooty = root
        FileSystemTree(
            rootPath = Path(root),
            selfInclude = true
        )
    } else {
        FileSystemTree(
            rootPath = Path(rooty),
            selfInclude = true
        )
    }
}

private fun savePath(): String {
    val os = System.getProperty("os.name").uppercase(Locale.getDefault())
    val dataRoot = if ("WIN" in os) {
        System.getenv("APPDATA")
    } else {
        "${System.getProperty("user.home")}${sep}.config"
    }
    return "$dataRoot${sep}StardewValley${sep}Saves"
}

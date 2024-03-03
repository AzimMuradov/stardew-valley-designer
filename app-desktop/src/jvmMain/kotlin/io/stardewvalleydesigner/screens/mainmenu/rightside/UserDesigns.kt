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

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.DialogProperties
import io.github.irgaly.kfswatch.KfsDirectoryWatcher
import io.github.irgaly.kfswatch.KfsEvent
import io.stardewvalleydesigner.LoggerUtils.logger
import io.stardewvalleydesigner.cmplib.tooltip.TooltipArea
import io.stardewvalleydesigner.designformat.DesignFormatSerializer
import io.stardewvalleydesigner.designformat.models.Design
import io.stardewvalleydesigner.engine.layout.LayoutsProvider
import io.stardewvalleydesigner.kmplib.fs.JvmFileSystem
import io.stardewvalleydesigner.kmplib.fs.getSvdSavesDir
import io.stardewvalleydesigner.ui.component.editor.screen.LayoutPreview
import io.stardewvalleydesigner.ui.component.settings.GlobalSettings
import io.stardewvalleydesigner.ui.component.windowsize.LocalWindowSize
import io.stardewvalleydesigner.ui.component.windowsize.WindowSize
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import java.io.File
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.io.path.*


@Composable
fun UserDesigns(
    onDesignOpenClicked: (Design, String) -> Unit,
    onDesignDeleteClicked: (String) -> Unit,
) {
    val wordList = GlobalSettings.strings

    val scope = rememberCoroutineScope()

    val docsDir by remember { mutableStateOf(JvmFileSystem.getDocsDir()) }
    val designSavesDir by remember { mutableStateOf(JvmFileSystem.getSvdSavesDir()) }

    val watcher by remember(scope) {
        mutableStateOf(KfsDirectoryWatcher(scope, dispatcher = Dispatchers.IO))
    }

    var designsData: List<DesignData> by remember { mutableStateOf(emptyList()) }

    LaunchedEffect(watcher) {
        watcher.add(docsDir)

        watcher
            .onEventFlow
            .onStart {
                designsData = if (
                    withContext(Dispatchers.IO) {
                        Path(designSavesDir).exists()
                    }
                ) {
                    watcher.add(designSavesDir)
                    withContext(Dispatchers.IO) {
                        getDesignsData(designSavesDir)
                    }
                } else {
                    emptyList()
                }
            }
            .collect {
                when (it.targetDirectory) {
                    docsDir -> if (it.path == "Stardew Valley Designer") {
                        when (it.event) {
                            KfsEvent.Create, KfsEvent.Modify -> {
                                watcher.add(designSavesDir)
                                designsData = withContext(Dispatchers.IO) {
                                    getDesignsData(designSavesDir)
                                }
                            }

                            KfsEvent.Delete -> {
                                watcher.remove(designSavesDir)
                                designsData = emptyList()
                            }
                        }
                    }

                    designSavesDir -> {
                        designsData = withContext(Dispatchers.IO) {
                            getDesignsData(designSavesDir)
                        }
                    }

                    else -> logger.error { it }
                }
            }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface)
            .padding(8.dp)
    ) {
        val lazyGridState = rememberLazyGridState()

        if (designsData.isNotEmpty()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(
                    count = when (LocalWindowSize.current) {
                        WindowSize.EXPANDED -> 1
                        WindowSize.LARGE -> 2
                        WindowSize.EXTRA_LARGE -> 3
                    }
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.surface),
                state = lazyGridState,
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(designsData) { designData ->
                    val designPath = JvmFileSystem.relative(
                        dir = designSavesDir,
                        filename = "${designData.metadata.filename}.json",
                    )

                    DesignCard(
                        designData = designData,
                        openDesign = {
                            onDesignOpenClicked(designData.design, designPath)
                        },
                        deleteDesign = {
                            onDesignDeleteClicked(designPath)
                        },
                    )
                }
            }
        } else {
            Text(
                text = wordList.designNoDesignsAtPath(designSavesDir),
                modifier = Modifier.align(Alignment.Center).padding(20.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h6
            )
        }

        VerticalScrollbar(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .fillMaxHeight()
                .padding(vertical = 16.dp),
            style = defaultScrollbarStyle().let {
                it.copy(unhoverColor = it.hoverColor)
            },
            adapter = rememberScrollbarAdapter(scrollState = lazyGridState),
        )
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun DesignCard(
    designData: DesignData,
    openDesign: () -> Unit,
    deleteDesign: () -> Unit,
) {
    val wordList = GlobalSettings.strings

    val (design, metadata) = designData
    val (filename, date) = metadata

    val dateFormatted = DateTimeFormatter
        .ofPattern("dd.MM.yyyy HH:mm:ss")
        .withZone(ZoneId.systemDefault())
        .format(date)

    Card(
        modifier = Modifier.fillMaxWidth().height(200.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(contentAlignment = Alignment.TopEnd) {
                Box(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.medium)
                        .clickable(onClick = openDesign)
                        .padding(8.dp)
                        .pointerHoverIcon(PointerIcon.Hand),
                ) {
                    LayoutPreview(
                        layout = LayoutsProvider.layoutOf(design.layout),
                        season = design.season,
                        entities = design.entities,
                        wallpaper = design.wallpaper,
                        flooring = design.flooring
                    )
                }


                var showDeleteDialog by remember { mutableStateOf(false) }
                var hovered by remember { mutableStateOf(false) }

                val rotation by animateFloatAsState(if (hovered) 180f else 0f)

                Icon(
                    imageVector = Icons.Filled.Cancel,
                    contentDescription = null,
                    modifier = Modifier
                        .absoluteOffset(x = 6.dp, y = (-6).dp)
                        .padding(6.dp)
                        .border(
                            width = Dp.Hairline,
                            color = Color(red = 0.4f, green = 0.1f, blue = 0.1f),
                            shape = CircleShape,
                        )
                        .background(
                            color = Color.White,
                            shape = CircleShape,
                        )
                        .clickable(
                            interactionSource = remember(::MutableInteractionSource),
                            indication = rememberRipple(bounded = false, radius = 24.dp)
                        ) {
                            showDeleteDialog = true
                        }
                        .pointerHoverIcon(PointerIcon.Hand)
                        .onPointerEvent(PointerEventType.Enter) {
                            hovered = true
                        }
                        .onPointerEvent(PointerEventType.Exit) {
                            hovered = false
                        }
                        .clip(CircleShape)
                        .size(28.dp)
                        .rotate(rotation),
                    tint = Color(red = 0.4f, green = 0.1f, blue = 0.1f)
                )

                if (showDeleteDialog) {
                    AlertDialog(
                        onDismissRequest = { showDeleteDialog = false },
                        confirmButton = {
                            Button(
                                onClick = {
                                    deleteDesign()
                                    showDeleteDialog = false
                                },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(red = 0.5f, green = 0.1f, blue = 0.1f),
                                    contentColor = Color.White,
                                ),
                            ) {
                                Text(text = wordList.delete, fontSize = 12.sp)
                            }
                        },
                        modifier = Modifier,
                        dismissButton = {
                            Button(onClick = { showDeleteDialog = false }) {
                                Text(wordList.no, fontSize = 12.sp)
                            }
                        },
                        title = {
                            Text(wordList.designCardDeleteDialogTitle)
                        },
                        text = {
                            Text(wordList.designCardDeleteDialogText)
                        },
                        shape = MaterialTheme.shapes.medium,
                        backgroundColor = MaterialTheme.colors.surface,
                        contentColor = MaterialTheme.colors.onSurface,
                        properties = DialogProperties(),
                    )
                }
            }
            SelectionContainer {
                Column(
                    modifier = Modifier.fillMaxHeight().weight(1f),
                    verticalArrangement = Arrangement.SpaceEvenly,
                ) {
                    InfoLine(wordList.designCardFilename, filename, hasTooltip = true)
                    InfoLine(wordList.designCardDate, dateFormatted, hasTooltip = true)
                    InfoLine(
                        wordList.designCardPlayerName,
                        value = design.playerName.takeIf { it.isNotBlank() } ?: "??",
                    )
                    InfoLine(
                        wordList.designCardFarmName,
                        value = design.farmName.takeIf { it.isNotBlank() } ?: "??",
                    )
                    InfoLine(wordList.designCardLayout, wordList.layout(design.layout))
                }
            }
        }
    }
}

@Composable
private fun InfoLine(
    header: String,
    value: String,
    hasTooltip: Boolean = false,
) {
    Row(
        modifier = Modifier.height(IntrinsicSize.Max),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier.width(100.dp).alignByBaseline(),
            contentAlignment = Alignment.TopStart,
        ) {
            Text(
                text = header,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.h6,
            )
        }
        TooltipArea(tooltip = value, enabled = hasTooltip) {
            Box(
                modifier = Modifier.weight(6f).alignByBaseline(),
                contentAlignment = Alignment.TopEnd,
            ) {
                Text(
                    text = value,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
            }
        }
    }
}


private data class DesignData(
    val design: Design,
    val metadata: DesignMetadata,
)

private data class DesignMetadata(
    val filename: String,
    val date: Instant,
)

private fun getDesignsData(dir: String): List<DesignData> {
    val files = File(dir)
        .listFiles()
        ?.asList()
        ?.filter { it.extension == "json" }
        ?: emptyList()

    return files.mapNotNull { file ->
        try {
            DesignData(
                DesignFormatSerializer.deserialize(file.readText()),
                DesignMetadata(
                    filename = file.toPath().nameWithoutExtension,
                    date = Instant.ofEpochMilli(file.lastModified())
                )
            )
        } catch (e: Exception) {
            null
        }
    }.sortedByDescending { designData ->
        designData.metadata.date
    }
}

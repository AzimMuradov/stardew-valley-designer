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

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogWindow
import androidx.compose.ui.window.rememberDialogState
import io.github.irgaly.kfswatch.KfsDirectoryWatcher
import io.github.irgaly.kfswatch.KfsEvent
import io.stardewvalleydesigner.LoggerUtils.logger
import io.stardewvalleydesigner.designformat.DesignFormatConverter
import io.stardewvalleydesigner.designformat.models.Design
import io.stardewvalleydesigner.engine.layout.LayoutsProvider
import io.stardewvalleydesigner.kmplib.fs.JvmFileSystem
import io.stardewvalleydesigner.kmplib.fs.getSvdSavesDir
import io.stardewvalleydesigner.ui.component.settings.GlobalSettings
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
        val lazyListState = rememberLazyListState()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.surface),
            state = lazyListState,
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
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
        VerticalScrollbar(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .fillMaxHeight()
                .padding(vertical = 16.dp),
            style = defaultScrollbarStyle().let {
                it.copy(unhoverColor = it.hoverColor)
            },
            adapter = rememberScrollbarAdapter(scrollState = lazyListState),
        )
    }
}


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
            modifier = Modifier.fillMaxSize().padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(Modifier.fillMaxHeight().width(250.dp)) {
                LayoutPreview(
                    layout = LayoutsProvider.layoutOf(design.layout),
                    entities = design.entities,
                    wallpaper = design.wallpaper,
                    flooring = design.flooring
                )
            }
            Column(
                modifier = Modifier.fillMaxHeight().weight(1f),
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                InfoLine(wordList.designCardFilename, filename)
                InfoLine(wordList.designCardDate, dateFormatted)
                InfoLine(wordList.designCardPlayerName, design.playerName)
                InfoLine(wordList.designCardFarmName, design.farmName)
                InfoLine(wordList.designCardLayout, wordList.layout(design.layout))
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .widthIn(min = 100.dp, max = 300.dp)
                    .width(IntrinsicSize.Min)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = openDesign,
                    modifier = Modifier.height(50.dp).fillMaxWidth()
                ) {
                    Text(wordList.designCardOpen)
                }

                var showDeleteDialog by remember { mutableStateOf(false) }

                Button(
                    onClick = { showDeleteDialog = true },
                    modifier = Modifier.height(50.dp).fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(red = 0.5f, green = 0.1f, blue = 0.1f),
                        contentColor = Color.White,
                    ),
                ) {
                    Text(wordList.delete)
                }

                if (showDeleteDialog) {
                    DialogWindow(
                        onCloseRequest = { showDeleteDialog = false },
                        state = rememberDialogState(
                            size = DpSize(500.dp, 200.dp),
                        ),
                        title = wordList.designCardDeleteDialogTitle,
                        resizable = false,
                    ) {
                        Column(Modifier.fillMaxSize().padding(20.dp)) {
                            Text(
                                text = wordList.designCardDeleteDialogText,
                                style = MaterialTheme.typography.h5,
                            )
                            Spacer(Modifier.weight(1f))
                            Row(Modifier.fillMaxWidth(), Arrangement.SpaceEvenly) {
                                Button(
                                    onClick = deleteDesign,
                                    modifier = Modifier.width(100.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color(red = 0.5f, green = 0.1f, blue = 0.1f),
                                        contentColor = Color.White,
                                    ),
                                ) {
                                    Text(wordList.delete)
                                }
                                Button(
                                    onClick = { showDeleteDialog = false },
                                    modifier = Modifier.width(100.dp),
                                ) {
                                    Text(wordList.no)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoLine(header: String, value: String) {
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
                DesignFormatConverter.parse(file.readText()),
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

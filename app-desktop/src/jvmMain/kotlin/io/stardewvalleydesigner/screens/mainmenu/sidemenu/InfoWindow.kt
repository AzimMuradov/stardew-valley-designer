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

package io.stardewvalleydesigner.screens.mainmenu.sidemenu

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogWindow
import androidx.compose.ui.window.rememberDialogState
import io.stardewvalleydesigner.AppInfo
import io.stardewvalleydesigner.kmplib.browser.Browser
import io.stardewvalleydesigner.ui.component.settings.GlobalSettings
import io.stardewvalleydesigner.ui.component.themes.ternaryColor


@Composable
fun InfoWindow() {
    val wordList = GlobalSettings.strings

    var isOpened: Boolean by remember { mutableStateOf(false) }

    SideMenuButton(
        text = wordList.buttonInfoText,
        tooltip = wordList.buttonInfoTooltip,
        icon = Icons.Filled.Info,
        onClick = { isOpened = true }
    )

    DialogWindow(
        onCloseRequest = { isOpened = false },
        state = rememberDialogState(size = DpSize(width = 400.dp, height = 650.dp)),
        visible = isOpened,
        title = "",
        resizable = false
    ) {
        Info()
    }
}


@Composable
private fun Info() {
    val wordList = GlobalSettings.strings

    val heading = MaterialTheme.typography.subtitle1.toSpanStyle().copy(
        color = MaterialTheme.colors.primary,
        fontWeight = FontWeight.Bold
    )

    Column(
        Modifier
            .fillMaxSize()
            .background(ternaryColor)
            .padding(vertical = 24.dp)
    ) {
        val state = rememberLazyListState()

        Text(
            text = wordList.application,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colors.primary,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h4
        )

        Spacer(Modifier.size(36.dp))

        Box(Modifier.fillMaxWidth().weight(1f)) {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp), state = state) {
                item {
                    SelectionContainer {
                        Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                            Text(wordList.infoApplicationDescription)
                            Divider(modifier = Modifier.fillMaxWidth())
                            ApplicationAuthorsSection(heading)
                            Divider(modifier = Modifier.fillMaxWidth())
                            StardewValleySection(heading)
                            Divider(modifier = Modifier.fillMaxWidth())
                            BugTrackerSection()
                        }
                    }
                }
            }
            VerticalScrollbar(
                modifier = Modifier.align(Alignment.TopEnd).fillMaxHeight().padding(end = 6.dp),
                adapter = rememberScrollbarAdapter(state)
            )
        }
    }
}


@Composable
private fun ApplicationAuthorsSection(heading: SpanStyle) {
    val wordList = GlobalSettings.strings

    val link = MaterialTheme.typography.subtitle1.copy(
        color = Color.Blue,
    )

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = heading) { append(wordList.infoApplicationAuthor) }
                },
                modifier = Modifier.weight(1f),
            )
            Box(
                Modifier
                    .wrapContentSize()
                    .pointerHoverIcon(PointerIcon.Hand)
                    .clickable { Browser.openLink(AppInfo.AUTHOR_URL) }
                    .wrapContentSize()
            ) {
                DisableSelection { Text(wordList.author, style = link) }
            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(heading) { append(wordList.infoApplicationSource) }
                },
                modifier = Modifier.weight(1f),
            )
            Spacer(Modifier.size(16.dp))
            Icon(
                painter = painterResource("social/github-mark.svg"),
                contentDescription = null,
                modifier = Modifier
                    .background(
                        color = Color.Black,
                        shape = MaterialTheme.shapes.small
                    )
                    .pointerHoverIcon(PointerIcon.Hand)
                    .clickable(
                        interactionSource = remember(::MutableInteractionSource),
                        indication = rememberRipple(color = Color.White)
                    ) {
                        Browser.openLink(AppInfo.REPOSITORY_URL)
                    }
                    .padding(8.dp)
                    .size(24.dp),
                tint = MaterialTheme.colors.onPrimary
            )
        }

        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = heading) { append(wordList.infoCurrentVersion) }
                },
                modifier = Modifier.weight(1f),
            )
            Row {
                Text(AppInfo.VERSION)
                Text(" | ")
                Box(
                    Modifier
                        .wrapContentSize()
                        .pointerHoverIcon(PointerIcon.Hand)
                        .clickable { Browser.openLink(AppInfo.CHANGELOG_URL) }
                        .wrapContentSize()
                ) {
                    DisableSelection { Text(wordList.infoChangelog, style = link) }
                }
            }
        }
    }
}

@Composable
private fun StardewValleySection(heading: SpanStyle) {
    val wordList = GlobalSettings.strings

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(
            text = buildAnnotatedString {
                append(wordList.infoSVText1)
                withStyle(style = heading) { append("ConcernedApe") }
                append(wordList.infoSVText2)
                appendLine()
                appendLine()
                append(wordList.infoSVText3)
            },
            modifier = Modifier.fillMaxWidth(),
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = heading) { append("ConcernedApe twitter") }
                },
                modifier = Modifier.weight(1f),
            )
            Spacer(Modifier.size(16.dp))
            Image(
                painter = painterResource("social/concerned-ape.jpg"),
                contentDescription = null,
                modifier = Modifier
                    .pointerHoverIcon(PointerIcon.Hand)
                    .clickable(
                        interactionSource = remember(::MutableInteractionSource),
                        indication = rememberRipple(color = Color.White)
                    ) {
                        Browser.openLink(url = "https://twitter.com/ConcernedApe")
                    }
                    .size(40.dp)
                    .clip(MaterialTheme.shapes.small)
            )
        }
    }
}

@Composable
private fun BugTrackerSection() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = GlobalSettings.strings.infoBugs,
            modifier = Modifier.weight(1f),
            softWrap = true
        )
        Spacer(Modifier.size(16.dp))
        Icon(
            imageVector = Icons.Filled.BugReport,
            contentDescription = null,
            modifier = Modifier
                .background(
                    color = MaterialTheme.colors.error,
                    shape = MaterialTheme.shapes.small
                )
                .pointerHoverIcon(PointerIcon.Hand)
                .clickable(
                    interactionSource = remember(::MutableInteractionSource),
                    indication = rememberRipple(color = Color.White)
                ) {
                    Browser.openLink(AppInfo.BUG_TRACKER_URL)
                }
                .padding(8.dp)
                .size(24.dp),
            tint = MaterialTheme.colors.onPrimary
        )
    }
}

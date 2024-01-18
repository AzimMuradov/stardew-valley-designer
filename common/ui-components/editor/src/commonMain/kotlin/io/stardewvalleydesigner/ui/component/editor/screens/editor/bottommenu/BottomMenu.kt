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

package io.stardewvalleydesigner.ui.component.editor.screens.editor.bottommenu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import io.stardewvalleydesigner.editor.EditorState
import io.stardewvalleydesigner.editor.menus.OptionsItemValue
import io.stardewvalleydesigner.engine.geometry.*
import io.stardewvalleydesigner.ui.component.editor.utils.UNDEFINED
import io.stardewvalleydesigner.ui.component.settings.GlobalSettings


@Composable
internal fun BottomMenu(
    editorState: EditorState,
    snackbarHostState: SnackbarHostState,
    currCoordinate: Coordinate,
    leftBottomMenus: @Composable RowScope.(EditorState, SnackbarHostState) -> Unit,
    rightBottomMenus: @Composable RowScope.(EditorState, SnackbarHostState) -> Unit,
) {
    val wordList = GlobalSettings.strings

    Row(
        modifier = Modifier
            .shadow(elevation = 4.dp)
            .fillMaxWidth().height(56.dp)
            .background(color = MaterialTheme.colors.primary)
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.weight(1f).fillMaxHeight(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            leftBottomMenus(editorState, snackbarHostState)

            Spacer(Modifier.weight(Float.MAX_VALUE))
        }

        Row(
            modifier = Modifier.weight(2f).fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (editorState.options.toggleables.getValue(OptionsItemValue.Toggleable.ShowCurrentCoordinatesAnsShapeSize)) {
                Divider(
                    modifier = Modifier.fillMaxHeight(fraction = 0.6f).width(1.dp),
                    color = MaterialTheme.colors.onPrimary.copy(alpha = 0.5f),
                )

                Row(Modifier.weight(2f), Arrangement.Center) {
                    val text = currCoordinate.takeUnless { it == UNDEFINED }?.let { (x, y) ->
                        "X: $x, Y: $y"
                    } ?: wordList.noCursor

                    Text(
                        text = text,
                        color = MaterialTheme.colors.onPrimary,
                        style = MaterialTheme.typography.subtitle1
                    )
                }

                Divider(
                    modifier = Modifier.fillMaxHeight(fraction = 0.6f).width(1.dp),
                    color = MaterialTheme.colors.onPrimary.copy(alpha = 0.5f),
                )

                val actionVector = editorState.toolkit.actionVector

                if (actionVector != null) {
                    Row(Modifier.weight(3f), Arrangement.Center) {
                        val (start, end) = actionVector

                        Text(
                            text = "${wordList.start}: $start, ${wordList.end}: $end",
                            color = MaterialTheme.colors.onPrimary,
                            style = MaterialTheme.typography.subtitle1
                        )
                    }

                    Divider(
                        modifier = Modifier.fillMaxHeight(fraction = 0.6f).width(1.dp),
                        color = MaterialTheme.colors.onPrimary.copy(alpha = 0.5f),
                    )

                    Row(Modifier.weight(2f), Arrangement.Center) {
                        val (w, h) = actionVector.let { (start, end) ->
                            CanonicalCorners.fromTwoCoordinates(start, end).rect
                        }

                        Text(
                            text = "${wordList.width}: $w, ${wordList.height}: $h",
                            color = MaterialTheme.colors.onPrimary,
                            style = MaterialTheme.typography.subtitle1
                        )
                    }
                } else {
                    Row(Modifier.weight(2f), Arrangement.Center) {
                        Text(
                            text = wordList.noSelection,
                            color = MaterialTheme.colors.onPrimary,
                            style = MaterialTheme.typography.subtitle1
                        )
                    }
                }

                Divider(
                    modifier = Modifier.fillMaxHeight(fraction = 0.6f).width(1.dp),
                    color = MaterialTheme.colors.onPrimary.copy(alpha = 0.5f),
                )
            }
        }

        Row(
            modifier = Modifier.weight(1f).fillMaxHeight(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(Modifier.weight(Float.MAX_VALUE))

            rightBottomMenus(editorState, snackbarHostState)
        }
    }
}

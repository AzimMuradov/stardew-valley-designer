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

package io.stardewvalleydesigner.ui.component.editor.screen.bottommenu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.stardewvalleydesigner.component.editor.EditorState
import io.stardewvalleydesigner.designformat.models.OptionsItemValue
import io.stardewvalleydesigner.engine.geometry.*
import io.stardewvalleydesigner.settings.wordlists.WordList
import io.stardewvalleydesigner.ui.component.editor.utils.UNDEFINED
import io.stardewvalleydesigner.ui.component.settings.GlobalSettings


@Composable
internal fun BottomMenu(
    editorState: EditorState,
    snackbarHostState: SnackbarHostState,
    currCoordinate: Coordinate,
    onPlayerNameChanged: (String) -> Unit,
    onFarmNameChanged: (String) -> Unit,
    rightBottomMenus: @Composable RowScope.(EditorState, SnackbarHostState) -> Unit,
) {
    val wordList = GlobalSettings.strings

    Row(
        modifier = Modifier
            .shadow(elevation = 4.dp)
            .fillMaxWidth().height(56.dp)
            .background(color = MaterialTheme.colors.primary)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxHeight().weight(1f).padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            NameField(
                initName = editorState.playerName,
                placeholder = wordList.playerNamePlaceholder,
                onNameChanged = onPlayerNameChanged
            )
            NameField(
                initName = editorState.farmName,
                placeholder = wordList.farmNamePlaceholder,
                onNameChanged = onFarmNameChanged,
            )
        }

        Row(
            modifier = Modifier.fillMaxHeight().weight(2f),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CursorAndSelectionInfo(editorState, currCoordinate, wordList)
        }

        Row(
            modifier = Modifier.fillMaxHeight().weight(1f),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(Modifier.weight(Float.MAX_VALUE))

            rightBottomMenus(editorState, snackbarHostState)
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun RowScope.NameField(
    initName: String,
    placeholder: String,
    onNameChanged: (String) -> Unit,
) {
    val textFieldColors = TextFieldDefaults.textFieldColors(
        textColor = MaterialTheme.colors.onPrimary,
        backgroundColor = MaterialTheme.colors.primaryVariant.copy(ContentAlpha.high),
        cursorColor = MaterialTheme.colors.onPrimary,
        focusedIndicatorColor = MaterialTheme.colors.onPrimary.copy(ContentAlpha.high),
        unfocusedIndicatorColor = MaterialTheme.colors.onPrimary.copy(TextFieldDefaults.UnfocusedIndicatorLineOpacity),
        trailingIconColor = MaterialTheme.colors.onPrimary.copy(TextFieldDefaults.IconOpacity),
        placeholderColor = MaterialTheme.colors.onPrimary.copy(ContentAlpha.medium),
    )
    val textFieldInteractionSource = remember { MutableInteractionSource() }

    var name by remember { mutableStateOf(initName) }

    BasicTextField(
        value = name,
        onValueChange = {
            if (it.length <= name.length || it.length <= 35) {
                name = it
                onNameChanged(it)
            }
        },
        modifier = Modifier
            .fillMaxHeight()
            .weight(1f)
            .background(
                color = textFieldColors.backgroundColor(enabled = true).value,
                shape = MaterialTheme.shapes.medium
            ),
        readOnly = false,
        textStyle = MaterialTheme.typography.body1.merge(
            TextStyle(color = textFieldColors.textColor(enabled = true).value)
        ),
        singleLine = true,
        cursorBrush = SolidColor(textFieldColors.cursorColor(isError = false).value),
        interactionSource = textFieldInteractionSource,
        decorationBox = @Composable { innerTextField ->
            TextFieldDefaults.OutlinedTextFieldDecorationBox(
                value = initName,
                visualTransformation = VisualTransformation.None,
                innerTextField = innerTextField,
                placeholder = {
                    Text(
                        text = placeholder,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = MaterialTheme.typography.body1,
                    )
                },
                leadingIcon = null,
                label = null,
                trailingIcon = {
                    val trailingIconInteractionSource = remember(::MutableInteractionSource)

                    Icon(
                        imageVector = Icons.Filled.Cancel,
                        contentDescription = null,
                        modifier = Modifier
                            .pointerHoverIcon(PointerIcon.Hand)
                            .size(20.dp)
                            .clickable(
                                interactionSource = trailingIconInteractionSource,
                                indication = rememberRipple(
                                    bounded = false,
                                    radius = 14.dp,
                                    color = textFieldColors.trailingIconColor(
                                        enabled = true,
                                        isError = false,
                                        trailingIconInteractionSource,
                                    ).value
                                )
                            ) {
                                name = ""
                                onNameChanged("")
                            }
                    )
                },
                singleLine = true,
                enabled = true,
                isError = false,
                interactionSource = textFieldInteractionSource,
                colors = textFieldColors,
                contentPadding = PaddingValues(start = 12.dp),
                border = {
                    TextFieldDefaults.BorderBox(
                        enabled = true,
                        isError = false,
                        interactionSource = textFieldInteractionSource,
                        colors = textFieldColors,
                        shape = MaterialTheme.shapes.medium,
                    )
                }
            )
        }
    )
}

@Composable
private fun RowScope.CursorAndSelectionInfo(
    editorState: EditorState,
    currCoordinate: Coordinate,
    wordList: WordList,
) {
    if (editorState.options.toggleables.getValue(OptionsItemValue.Toggleable.ShowCurrentCoordinatesAnsShapeSize)) {
        val actionVector = editorState.toolkit.actionVector

        Row(Modifier.weight(2f), Arrangement.Center) {
            val text = currCoordinate.takeUnless { it == UNDEFINED }?.let { (x, y) ->
                "X: $x, Y: $y"
            } ?: ""

            Text(
                text = text,
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.subtitle1
            )
        }

        if (actionVector != null) {
            Divider(
                modifier = Modifier.fillMaxHeight(fraction = 0.6f).width(1.dp),
                color = MaterialTheme.colors.onPrimary.copy(alpha = 0.5f),
            )

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
        }
    }
}

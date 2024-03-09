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

package io.stardewvalleydesigner.ui.component.editor.screen.sidemenus

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import io.stardewvalleydesigner.cmplib.group.GroupOption
import io.stardewvalleydesigner.cmplib.group.ToggleButtonsGroup
import io.stardewvalleydesigner.cmplib.tooltip.TooltipArea
import io.stardewvalleydesigner.cmplib.tooltip.TooltipPlacement
import io.stardewvalleydesigner.component.editor.EditorIntent
import io.stardewvalleydesigner.component.editor.modules.toolkit.*
import io.stardewvalleydesigner.ui.component.editor.res.ImageResourcesProvider
import io.stardewvalleydesigner.ui.component.settings.GlobalSettings


@Composable
internal fun Toolbar(
    toolkit: ToolkitState,
    intentConsumer: (EditorIntent.Toolkit) -> Unit,
) {
    val tools = listOf(
        ToolType.Pen,
        ToolType.Eraser,
        ToolType.Drag,
        ToolType.Select,
        ToolType.EyeDropper,
    ).map { GroupOption.Some(it) }
    val shapes = (listOf(null) + ShapeType.entries).map {
        if (it in toolkit.allowedShapes) {
            GroupOption.Some(it)
        } else {
            GroupOption.Disabled(it)
        }
    }

    val size = 5u

    val wordList = GlobalSettings.strings

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Divider(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = wordList.tool(toolkit.tool),
            style = MaterialTheme.typography.subtitle1
        )
        Spacer(modifier = Modifier.width(8.dp))
        Divider(modifier = Modifier.weight(1f))
    }

    Spacer(modifier = Modifier.height(16.dp))

    ToggleButtonsGroup(
        buttonLabels = tools,
        rowSize = size,
        modifier = Modifier.fillMaxWidth(),
        chosenLabel = toolkit.tool,
        onButtonClick = { intentConsumer(EditorIntent.Toolkit.ChooseTool(it)) },
        spaceContent = {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = null,
                modifier = Modifier.fillMaxSize().padding(8.dp),
            )
        },
        buttonContent = { toolType ->
            ButtonContent(
                tooltip = wordList.tool(toolType),
                image = ImageResourcesProvider.toolImageBy(toolType),
            )
        }
    )

    if (toolkit.allowedShapes != listOf(null)) {
        Spacer(modifier = Modifier.height(8.dp))

        ToggleButtonsGroup(
            buttonLabels = shapes,
            rowSize = size,
            modifier = Modifier.fillMaxWidth(),
            chosenLabel = toolkit.shape,
            onButtonClick = { intentConsumer(EditorIntent.Toolkit.ChooseShape(it)) },
            spaceContent = {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize().padding(8.dp),
                )
            },
            buttonContent = { shapeType ->
                val shapeName = wordList.shape(shapeType)
                val postfix = if (shapeType !in toolkit.allowedShapes) {
                    " (${wordList.notAvailableForThisTool})"
                } else {
                    ""
                }
                ButtonContent(
                    tooltip = "$shapeName$postfix",
                    image = ImageResourcesProvider.shapeImageBy(shapeType),
                )
            }
        )
    }
}


@Composable
private fun ButtonContent(tooltip: String, image: ImageBitmap) {
    TooltipArea(
        tooltip,
        delayMillis = 1000,
        tooltipPlacement = TooltipPlacement.ComponentRect(
            anchor = Alignment.TopEnd,
            alignment = Alignment.TopEnd,
            offset = DpOffset((-8).dp, 8.dp)
        )
    ) {
        Icon(
            bitmap = image,
            contentDescription = null,
            modifier = Modifier.fillMaxSize().padding(8.dp),
        )
    }
}

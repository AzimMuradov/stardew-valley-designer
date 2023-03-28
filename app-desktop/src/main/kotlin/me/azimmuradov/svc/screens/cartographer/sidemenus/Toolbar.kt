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

package me.azimmuradov.svc.screens.cartographer.sidemenus

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.azimmuradov.svc.cartographer.CartographerIntent
import me.azimmuradov.svc.cartographer.modules.toolkit.*
import me.azimmuradov.svc.utils.GlobalSettings
import me.azimmuradov.svc.utils.group.GroupOption
import me.azimmuradov.svc.utils.group.ToggleButtonsGroup


@Composable
fun Toolbar(
    toolkit: ToolkitState,
    intentConsumer: (CartographerIntent.Toolkit) -> Unit,
) {
    val tools = ToolType.values().map { GroupOption.Some(it) }
    val shapes = (listOf(null) + ShapeType.values()).map {
        if (it in toolkit.allowedShapes) {
            GroupOption.Some(it)
        } else {
            GroupOption.Disabled(it)
        }
    }

    val size = 5u

    val wordList = GlobalSettings.strings

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Divider(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = wordList.tool(toolkit.tool),
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Divider(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(16.dp))

        ToggleButtonsGroup(
            buttonLabels = tools,
            rowSize = size,
            chosenLabel = GroupOption.Some(toolkit.tool),
            onButtonClick = { intentConsumer(CartographerIntent.Toolkit.ChooseTool(it)) },
            spaceContent = {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize().padding(8.dp),
                )
            },
            buttonContent = { toolType ->
                // TooltipArea(
                //     tooltip = {
                //         Surface(
                //             modifier = Modifier.height(24.dp),
                //             color = Color.DarkGray,
                //             contentColor = Color.White,
                //             shape = MaterialTheme.shapes.medium,
                //         ) {
                //             Row(
                //                 modifier = Modifier.fillMaxHeight().padding(horizontal = 8.dp),
                //                 verticalAlignment = Alignment.CenterVertically,
                //             ) {
                //                 Text(wordList.tool(toolType), fontSize = 12.sp)
                //             }
                //         }
                //     },
                //     tooltipPlacement = TooltipPlacement.ComponentRect(),
                // ) {
                // Sprite(
                //     sprite = toolSpriteBy(toolType),
                //     modifier = Modifier.fillMaxSize().padding(4.dp),
                // )
                // }
                Text(
                    text = toolType.name,
                    fontSize = 10.sp,
                    modifier = Modifier.fillMaxSize().padding(4.dp),
                )
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        ToggleButtonsGroup(
            buttonLabels = shapes,
            rowSize = size,
            chosenLabel = GroupOption.Some(toolkit.shape),
            onButtonClick = { intentConsumer(CartographerIntent.Toolkit.ChooseShape(it)) },
            spaceContent = {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize().padding(8.dp),
                )
            },
            buttonContent = { selectionType ->
                // Sprite(
                //     sprite = toolSpriteBy(ToolType.Hand),
                //     modifier = Modifier.fillMaxSize().padding(4.dp),
                // )
                Text(
                    text = selectionType?.name.toString(),
                    fontSize = 10.sp,
                    modifier = Modifier.fillMaxSize().padding(4.dp),
                )
            }
        )
    }
}

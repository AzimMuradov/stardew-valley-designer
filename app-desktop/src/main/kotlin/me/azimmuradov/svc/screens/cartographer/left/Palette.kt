/*
 * Copyright 2021-2021 Azim Muradov
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

package me.azimmuradov.svc.screens.cartographer.left

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import me.azimmuradov.svc.cartographer.palette.MutablePalette
import me.azimmuradov.svc.engine.rectmap.Rect
import me.azimmuradov.svc.settings.languages.Language
import me.azimmuradov.svc.utils.drawSpriteBy
import me.azimmuradov.svc.utils.group.ButtonsGroup


@Composable
fun Palette(
    palette: MutablePalette,
    language: Language,
    modifier: Modifier = Modifier,
) {
    val inUse = palette.inUse
    val hotbar = palette.hotbar

    Box(modifier) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Card(elevation = 0.dp) {
                Row(
                    Modifier.fillMaxWidth().padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Button(
                        onClick = { /* TODO */ },
                        modifier = Modifier
                            .size(56.dp)
                            .border(
                                width = Dp.Hairline,
                                color = MaterialTheme.colors.secondaryVariant,
                                shape = CircleShape,
                            ),
                        elevation = ButtonDefaults.elevation(pressedElevation = 4.dp),
                        shape = CircleShape,
                        colors = ButtonDefaults.outlinedButtonColors(),
                        contentPadding = PaddingValues(12.dp),
                        enabled = inUse != null,
                    ) {
                        if (inUse != null) {
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                drawSpriteBy(
                                    id = inUse,
                                    layoutSize = size,
                                )
                            }
                        } else {
                            Icon(Icons.Default.Clear, null, Modifier.fillMaxSize())
                        }
                    }
                    Column {
                        Text(
                            text = if (inUse != null) language.cartographer.entity(inUse) else "",
                            color = MaterialTheme.colors.secondaryVariant,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.subtitle1,
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = inUse?.size?.toShortString() ?: "",
                            style = MaterialTheme.typography.body2,
                        )
                    }
                }
            }

            Spacer(Modifier.height(8.dp))


            ButtonsGroup(
                buttonLabels = hotbar,
                rowSize = 5u,
                onButtonClick = { },
                spaceContent = { Icon(Icons.Default.Clear, null, Modifier.fillMaxSize()) }
            ) { entityId ->
                Canvas(Modifier.fillMaxSize()) {
                    drawSpriteBy(
                        id = entityId,
                        layoutSize = size,
                    )
                }
            }
        }
    }
}


private fun Rect.toShortString(): String = "$w x $h"
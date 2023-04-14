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

package me.azimmuradov.svc.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.azimmuradov.svc.ICON_RES_PATH
import me.azimmuradov.svc.components.screens.MainMenuComponent


@Composable
fun MainMenuUi(component: MainMenuComponent) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth().weight(1f).background(MaterialTheme.colors.background).padding(10.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(300.dp)
                    .shadow(
                        elevation = 4.dp,
                        shape = MaterialTheme.shapes.large
                    )
                    .background(MaterialTheme.colors.surface)
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .aspectRatio(ratio = 9f / 5f)
                        .shadow(
                            elevation = 4.dp,
                            shape = MaterialTheme.shapes.medium
                        )
                        .background(Color.Yellow.copy(alpha = 0.3f).compositeOver(Color.White))
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(ICON_RES_PATH),
                        contentDescription = null,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(Modifier.size(12.dp))
                    Text(
                        text = "Stardew Valley Cartographer",
                        modifier = Modifier.weight(3f),
                        textAlign = TextAlign.Center,
                        lineHeight = 32.sp,
                        style = MaterialTheme.typography.h5
                    )
                }
                Column(
                    Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = {},
                        Modifier.height(50.dp).fillMaxWidth(),
                        colors = ButtonDefaults.outlinedButtonColors(
                            backgroundColor = Color(0xFFE6FFBF)
                        )
                    ) {
                        Text(text = "Stardew Valley")
                    }
                    Button(
                        onClick = {},
                        Modifier.height(50.dp).fillMaxWidth(),
                        enabled = false,
                        colors = ButtonDefaults.outlinedButtonColors(
                            backgroundColor = Color(0xFFE6FFBF)
                        )
                    ) {
                        Text(text = "Switch theme")
                    }
                    Button(
                        onClick = {},
                        Modifier.height(50.dp).fillMaxWidth(),
                        enabled = false,
                        colors = ButtonDefaults.outlinedButtonColors(
                            backgroundColor = Color(0xFFE6FFBF)
                        )
                    ) {
                        Text(text = "Settings")
                    }
                    Button(
                        onClick = {},
                        Modifier.height(50.dp).fillMaxWidth(),
                        enabled = false,
                        colors = ButtonDefaults.outlinedButtonColors(
                            backgroundColor = Color(0xFFE6FFBF)
                        )
                    ) {
                        Text(text = "Donate")
                    }
                    Button(
                        onClick = {},
                        Modifier.height(50.dp).fillMaxWidth(),
                        colors = ButtonDefaults.outlinedButtonColors(
                            backgroundColor = Color(0xFFE6FFBF)
                        )
                    ) {
                        Text(text = "Help")
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .shadow(
                        elevation = 4.dp,
                        shape = MaterialTheme.shapes.large
                    )
                    .background(Color.LightGray)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(Modifier.fillMaxWidth().height(56.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Button(onClick = {}, Modifier.fillMaxHeight().width(200.dp)) {
                        Text(text = "New Plan")
                    }

                    Button(onClick = {}, Modifier.fillMaxHeight().width(200.dp)) {
                        Text(text = "Import plan from save")
                    }

                    Button(onClick = {}, Modifier.fillMaxHeight().width(200.dp), enabled = false) {
                        Text(text = "Search for a plan")
                    }
                }


                // TODO : Persistent Plans

                LazyColumn(
                    modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.surface),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // items(count = 5) {
                    //     Card(modifier = Modifier.fillMaxWidth().height(200.dp)) {
                    //         Row(
                    //             modifier = Modifier.fillMaxSize().padding(8.dp),
                    //             horizontalArrangement = Arrangement.spacedBy(8.dp)
                    //         ) {
                    //             Card(Modifier.fillMaxHeight().width(120.dp)) {
                    //                 Text(text = "Plan screenshot")
                    //             }
                    //             Card(Modifier.fillMaxHeight().weight(1f)) {
                    //                 Column {
                    //                     Text(text = "Plan #$it")
                    //                     Text(text = "Plan info")
                    //                 }
                    //             }
                    //         }
                    //     }
                    // }
                }
            }
        }
    }
}

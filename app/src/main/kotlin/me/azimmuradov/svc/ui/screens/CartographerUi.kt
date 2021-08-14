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

package me.azimmuradov.svc.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultBlendMode
import androidx.compose.ui.graphics.drawscope.Stroke
import me.azimmuradov.svc.components.cartographer.Cartographer


@Composable
fun CartographerUi(component: Cartographer) {
    Row(Modifier.fillMaxSize()) {
        Column(Modifier.weight(1f).fillMaxHeight().background(color = Color.Blue)) {
            // Layout
        }
        Column(
            modifier = Modifier.weight(5f).fillMaxHeight().background(color = Color.Cyan),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Canvas(modifier = Modifier.aspectRatio(1f).fillMaxSize().background(color = Color.White)) {
                val canvasSize = size.width
                val stepSize = canvasSize / 30

                val floatIterable = object : Iterable<Float> {
                    override fun iterator(): Iterator<Float> = object : Iterator<Float> {
                        val start = 0f
                        val end = size.width

                        var curr = start

                        override fun hasNext(): Boolean = curr < end

                        override fun next(): Float = curr.also { curr += stepSize }
                    }
                }

                for (x in floatIterable) {
                    for (y in floatIterable) {
                        drawRect(
                            color = Color.Black,
                            topLeft = Offset(x, y),
                            size = Size(width = stepSize, height = stepSize),
                            alpha = 0.7f,
                            style = Stroke(),
                            colorFilter = null,
                            blendMode = DefaultBlendMode,
                        )
                    }
                }
            }
        }
        Column(Modifier.weight(1f).fillMaxHeight().background(color = Color.Blue)) {
            //
        }
    }
}
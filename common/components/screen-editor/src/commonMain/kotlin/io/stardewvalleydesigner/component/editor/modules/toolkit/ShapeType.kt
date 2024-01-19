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

package io.stardewvalleydesigner.component.editor.modules.toolkit

import io.stardewvalleydesigner.engine.geometry.Coordinate
import io.stardewvalleydesigner.engine.geometry.shapes.*


enum class ShapeType {
    Rect,
    RectOutline,
    Ellipse,
    EllipseOutline,
    Diamond,
    DiamondOutline,
    Line;

    fun projectTo(a: Coordinate, b: Coordinate): PlacedShape = PlacedShape(
        a, b,
        strategy = when (this) {
            Rect -> PlacedRectStrategy
            RectOutline -> PlacedRectOutlineStrategy
            Ellipse -> PlacedEllipseStrategy
            EllipseOutline -> PlacedEllipseOutlineStrategy
            Diamond -> PlacedDiamondStrategy
            DiamondOutline -> PlacedDiamondOutlineStrategy
            Line -> PlacedLineStrategy
        }
    )
}

fun PlacedShape.type(): ShapeType = when (strategy) {
    PlacedRectStrategy -> ShapeType.Rect
    PlacedRectOutlineStrategy -> ShapeType.RectOutline
    PlacedEllipseStrategy -> ShapeType.Ellipse
    PlacedEllipseOutlineStrategy -> ShapeType.EllipseOutline
    PlacedDiamondStrategy -> ShapeType.Diamond
    PlacedDiamondOutlineStrategy -> ShapeType.DiamondOutline
    PlacedLineStrategy -> ShapeType.Line
}

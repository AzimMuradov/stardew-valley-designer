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

package io.stardewvalleydesigner.engine.layout.layouts

import io.stardewvalleydesigner.engine.entity.*
import io.stardewvalleydesigner.engine.geometry.*
import io.stardewvalleydesigner.engine.layout.Layout
import io.stardewvalleydesigner.engine.layout.LayoutType


internal val StandardFarmLayout = Layout(
    type = LayoutType.StandardFarm,
    size = rectOf(w = 80, h = 65),
    disallowedTypes = setOf(
        FloorFurnitureType,
        ObjectType.FurnitureType.HouseFurnitureType,
        ObjectType.FurnitureType.IndoorFurnitureType,
    ),
    disallowedTypesMap = emptyMap(),
    disallowedCoordinates = buildSet {
        this += rect(59..67, 11..16) // TODO : TEMP
        this -= row(60..62, 15).toSet()

        // Borders
        this += buildList {
            addAll(rect(0..39, 0..7) - xy(34, 7))
            addAll(row(0..3, 8))
            addAll(rect(0..2, 9..61))
            addAll(rect(3..6, 23..33))
            addAll(listOf(xy(3, 34), xy(4, 34), xy(3, 35)))
            addAll(rect(0..39, 62..64))
            addAll(rect(42..79, 62..64))
            addAll(rect(69..79, 59..61))
            addAll(rect(73..79, 56..58))
            addAll(rect(77..79, 19..55))
            addAll(rect(42..79, 0..6))
            addAll(row(42..45, 7))
            addAll(row(55..79, 7))
            addAll(rect(55..68, 8..9))
            addAll(rect(73..79, 8..9))
            addAll(rect(78..79, 10..14))
        }

        // Pet bowl
        this += xy(54, 7)

        // Mailbox
        this += xy(68, 16)

        // Small lake
        this += buildList {
            addAll(row(70..74, 28))
            addAll(rect(70..75, 29..32))
            addAll(row(71..74, 33))
        }

        // Big lake
        this += buildList {
            addAll(row(36..42, 49))
            addAll(row(35..43, 50))
            addAll(row(34..45, 51))
            addAll(rect(33..46, 52..54))
            addAll(row(34..46, 55))
            addAll(row(34..45, 56))
            addAll(row(36..44, 57))
            addAll(row(37..41, 58))
        }
    }
)

private fun rect(xs: IntRange, ys: IntRange) = buildList {
    for (x in xs) {
        for (y in ys) {
            add(xy(x, y))
        }
    }
}

private fun row(xs: IntRange, y: Int) = buildList {
    for (x in xs) {
        add(xy(x, y))
    }
}

private fun col(x: Int, ys: IntRange) = buildList {
    for (y in ys) {
        add(xy(x, y))
    }
}

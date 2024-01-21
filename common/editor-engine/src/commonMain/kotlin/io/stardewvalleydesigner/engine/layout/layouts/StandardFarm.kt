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

package io.stardewvalleydesigner.engine.layout.layouts

import io.stardewvalleydesigner.engine.entity.*
import io.stardewvalleydesigner.engine.geometry.*
import io.stardewvalleydesigner.engine.layout.Layout
import io.stardewvalleydesigner.engine.layout.LayoutType


internal val StandardFarmLayout: Layout = run {
    val disallowedCoordinates: Set<Coordinate> = buildSet {
        // Farmhouse
        this += buildList {
            addAll(rect(59..67, 11..16))
            removeAll(row(60..62, 15))
        }

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


    return@run Layout(
        type = LayoutType.StandardFarm,
        size = rectOf(w = 80, h = 65),
        disallowedTypes = setOf(
            FloorFurnitureType,
            ObjectType.FurnitureType.HouseFurnitureType,
            ObjectType.FurnitureType.IndoorFurnitureType,
        ),
        disallowedTypesMap = buildMap {
            val standard = setOf(EntityWithoutFloorType.BuildingType, EntityWithoutFloorType.CropType)
            val buildable = standard - setOf(EntityWithoutFloorType.BuildingType)
            val noPlaceable = standard + ObjectType.all

            // Top-left area
            this += row(4..39, 8) withData buildable
            this += row(7..9, 8) withData standard
            this += xy(34, 7) to standard
            this += listOf(xy(3, 9), xy(4, 9), xy(3, 10)) withData buildable

            // Rock shade on the left
            this += listOf(xy(5, 34), xy(4, 35), xy(3, 36)) withData buildable

            // Top area
            this += xy(40, 0) to buildable
            this += xy(40, 1) to standard
            this += col(41, 0..8) withData buildable
            this += row(42..45, 8) withData buildable
            this += xy(46, 7) to buildable
            this += xy(48, 7) to standard

            // Pet area
            this += listOf(xy(53, 7), xy(53, 8), xy(54, 8)) withData noPlaceable

            // Weird shade tile
            this += xy(54, 9) to standard

            // Farmhouse area
            this += buildList {
                addAll(row(54..77, 10))
                addAll(row(55..77, 11))
                addAll(row(56..77, 12))
                addAll(row(57..77, 13))
                addAll(row(58..77, 14))
                addAll(rect(58..79, 15..17))
                removeAll(listOf(xy(71, 16), xy(70, 17), xy(71, 17)))
            } withData buildable
            this += row(60..62, 15) withData standard // Farmhouse
            this += xy(64, 17) to noPlaceable // Farmhouse
            this += rect(69..72, 8..9) withData noPlaceable // Spouse patio
            this += rect(69..72, 10..13) withData standard // Under the spouse patio
            this += xy(68, 14) to noPlaceable // Above the mailbox
            // Path to shipment chest
            this += buildList {
                addAll(listOf(xy(71, 15), xy(72, 15), xy(72, 16)))
                addAll(row(72..79, 17))
            } withData standard
            this += xy(78, 18) to buildable // Weird grass tile

            // Shade line on the right
            this += col(76, 19..55) withData buildable

            // Remove disallowed coordinates
            this -= disallowedCoordinates
        },
        disallowedCoordinates = disallowedCoordinates
    )
}


private infix fun Collection<Coordinate>.withData(noPlaceable: Set<EntityType>) = associateWith {
    noPlaceable
}


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

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

package me.azimmuradov.svc.save

import kotlinx.serialization.decodeFromString
import me.azimmuradov.svc.engine.*
import me.azimmuradov.svc.engine.layers.layered
import me.azimmuradov.svc.engine.layout.LayoutType
import me.azimmuradov.svc.engine.layout.LayoutsProvider
import me.azimmuradov.svc.save.mappers.toPlacedEntityOrNull
import me.azimmuradov.svc.save.models.SaveGame
import nl.adaptivity.xmlutil.serialization.XML
import java.io.File


object SaveDataSerializers {

    fun parse(path: String): List<SvcEngine> {
        val xml = XML {
            indent = 2
            customPolicy()
        }

        // Fix BOM bug
        val text = File(path).readText().run {
            if (first() == '\uFEFF') drop(n = 1) else this
        }

        val save = xml.decodeFromString<SaveGame>(text)

        val farm = save.locations.first { it.type == "Farm" }
        val buildings = farm.buildings.filter { it.buildingType in buildingsByType }

        return buildList {
            this += svcEngineOf(LayoutsProvider.layoutOf(LayoutType.StandardFarm)).apply {
                putAll(
                    run {
                        val furniture = farm.furniture.mapNotNull { it.toPlacedEntityOrNull() }
                        val objects = farm.objects.mapNotNull { it.value.obj.toPlacedEntityOrNull() }
                        val flooring = farm.terrainFeatures.mapNotNull { it.toPlacedEntityOrNull() }

                        (furniture + objects + flooring).filter { it.place.y >= 0 }.layered()
                    }
                )
                putAll(
                    farm.buildings.mapNotNull { it.toPlacedEntityOrNull() }.layered()
                )
            }

            println(
                farm.buildings.joinToString(separator = "\n") {
                    "${it.buildingType} ${it.tileX} ${it.tileY} ${it.tilesWide} ${it.tilesHigh}"
                }
            )

            this += buildings.map { building ->
                val layout = LayoutsProvider.layoutOf(buildingsByType.getValue(building.buildingType))

                svcEngineOf(layout).apply {
                    putAll(
                        if (building.indoors != null) {
                            val furniture = building.indoors.furniture.mapNotNull { it.toPlacedEntityOrNull() }
                            val objects = building.indoors.objects.mapNotNull { it.value.obj.toPlacedEntityOrNull() }
                            val flooring = building.indoors.terrainFeatures.mapNotNull { it.toPlacedEntityOrNull() }

                            (furniture + objects + flooring).filter { it.place.y >= 0 }
                        } else {
                            emptyList()
                        }.layered()
                    )
                    wallpaper = building.indoors?.wallPaper?.int?.toUByte()?.run(::Wallpaper)
                    flooring = building.indoors?.floor?.int?.toUByte()?.run(::Flooring)
                }
            }
        }
    }

    private val buildingsByType = mapOf(
        "Shed" to LayoutType.Shed,
        "Big Shed" to LayoutType.BigShed
    )
}

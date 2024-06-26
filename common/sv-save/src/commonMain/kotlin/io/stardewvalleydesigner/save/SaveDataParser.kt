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

package io.stardewvalleydesigner.save

import io.stardewvalleydesigner.LoggerUtils.logger
import io.stardewvalleydesigner.designformat.models.*
import io.stardewvalleydesigner.engine.*
import io.stardewvalleydesigner.engine.layout.*
import io.stardewvalleydesigner.save.mappers.toPlacedEntityOrNull
import io.stardewvalleydesigner.save.models.SaveGame
import io.stardewvalleydesigner.save.models.Season
import kotlinx.serialization.decodeFromString
import nl.adaptivity.xmlutil.serialization.XML
import io.stardewvalleydesigner.data.Season as SeasonData


object SaveDataParser {

    fun parse(text: String): List<Design> {
        val xml = XML {
            indent = 2
            customPolicy()
        }

        // Fix BOM bug
        fun String.dropBOM() = if (first() == '\uFEFF') drop(n = 1) else this

        val save = xml.decodeFromString<SaveGame>(text.dropBOM())

        val farm = save.locations.first { it.type == "Farm" }
        val buildings = farm.buildings.filter { it.buildingType in buildingsByType }

        val farmEngine = run {
            val layout = LayoutsProvider.layoutOf(
                when (save.whichFarm) {
                    0 -> LayoutType.StandardFarm
                    1 -> LayoutType.RiverlandFarm
                    2 -> LayoutType.ForestFarm
                    3 -> LayoutType.HillTopFarm
                    4 -> LayoutType.WildernessFarm
                    5 -> LayoutType.FourCornersFarm
                    6 -> TODO("Beach Farm")
                    else -> error("unknown farm ${save.whichFarm}")
                }
            )

            editorEngineOf(layout).apply {
                val (es, failedEs) = buildList {
                    addAll(farm.furniture.mapNotNull { it.toPlacedEntityOrNull() })
                    addAll(farm.objects.mapNotNull { it.value.obj.toPlacedEntityOrNull() })
                    addAll(farm.terrainFeatures.mapNotNull { it.toPlacedEntityOrNull() })
                    addAll(farm.buildings.mapNotNull { it.toPlacedEntityOrNull() })
                }.partition { it respects layout }

                if (failedEs.isNotEmpty()) {
                    logger.warn { failedEs }
                }

                for (e in es) put(e)
            }
        }

        val buildingsEngines = buildings.map { building ->
            val layout = LayoutsProvider.layoutOf(buildingsByType.getValue(building.buildingType))

            editorEngineOf(layout).apply {
                val (es, failedEs) = buildList {
                    if (building.indoors != null) {
                        addAll(building.indoors.furniture.mapNotNull { it.toPlacedEntityOrNull() })
                        addAll(building.indoors.objects.mapNotNull { it.value.obj.toPlacedEntityOrNull() })
                        addAll(building.indoors.terrainFeatures.mapNotNull { it.toPlacedEntityOrNull() })
                    }
                }.partition { it respects layout }

                if (failedEs.isNotEmpty()) {
                    logger.warn { failedEs }
                }

                for (e in es) put(e)

                wallpaper = building.indoors?.firstWallpaper?.run(::Wallpaper)
                flooring = building.indoors?.firstFloor?.run(::Flooring)
            }
        }

        val designs = (listOf(farmEngine) + buildingsEngines).map { engine ->
            Design(
                playerName = save.player.name,
                farmName = save.player.farmName,
                season = when (save.currentSeason) {
                    Season.spring -> SeasonData.Spring
                    Season.summer -> SeasonData.Summer
                    Season.fall -> SeasonData.Fall
                    Season.winter -> SeasonData.Winter
                },
                layout = engine.layout.type,
                entities = engine.getEntities(),
                wallpaper = engine.wallpaper,
                flooring = engine.flooring,
                palette = Palette.default(),
                options = Options.default(),
            )
        }

        return designs
    }

    private val buildingsByType = mapOf(
        "Shed" to LayoutType.Shed,
        "Big Shed" to LayoutType.BigShed
    )
}

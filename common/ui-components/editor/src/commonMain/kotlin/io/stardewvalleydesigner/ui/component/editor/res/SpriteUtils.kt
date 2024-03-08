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

package io.stardewvalleydesigner.ui.component.editor.res

import androidx.compose.ui.graphics.ImageBitmap
import io.stardewvalleydesigner.data.*
import io.stardewvalleydesigner.data.Season.*
import io.stardewvalleydesigner.engine.entity.*
import io.stardewvalleydesigner.engine.entity.Building.SimpleBuilding.JunimoHut
import io.stardewvalleydesigner.engine.entity.Equipment.SimpleEquipment.*
import io.stardewvalleydesigner.engine.geometry.*
import io.stardewvalleydesigner.engine.layer.LayerType
import io.stardewvalleydesigner.engine.layers.LayeredEntitiesData
import io.stardewvalleydesigner.ui.component.editor.res.ImageResourcesProvider.spriteBy
import io.stardewvalleydesigner.ui.component.editor.utils.DrawerUtils.placedEntityComparator


object SpriteUtils {

    fun calculateSprite(
        spriteMaps: Map<SpritePage, ImageBitmap>,
        entities: LayeredEntitiesData,
        visibleLayers: Set<LayerType<*>>,
        season: Season,
    ): List<Pair<PlacedEntity<*>, Sprite>> {
        val sorted = visibleLayers.flatMap(entities::entitiesBy).sortedWith(placedEntityComparator)
        val fenceEs = setOf(WoodFence, StoneFence, IronFence, HardwoodFence)
        val fences = entities
            .objectEntities
            .asSequence()
            .filter { it.rectObject in fenceEs }
            .associate { it.place to it.rectObject }
        val fe = entities
            .floorEntities
            .associate { it.place to it.rectObject }

        return buildList {
            for (placedEntity in sorted) {
                val (entity, place) = placedEntity
                val qualifier = when (entity) {
                    WoodFence, StoneFence, IronFence, HardwoodFence,
                    -> {
                        val hasBottomNeighbour = fences[place + vec(x = 0, y = 1)] == entity
                        val hasTopNeighbour = fences[place - vec(x = 0, y = 1)] == entity
                        val hasRightNeighbour = fences[place + vec(x = 1, y = 0)] == entity
                        val hasLeftNeighbour = fences[place - vec(x = 1, y = 0)] == entity

                        val ctId =
                            1 * (if (hasBottomNeighbour) 1 else 0) +
                                2 * (if (hasTopNeighbour) 1 else 0) +
                                4 * (if (hasRightNeighbour) 1 else 0) +
                                8 * (if (hasLeftNeighbour) 1 else 0)

                        SpriteQualifier.FenceQualifier(
                            variant = when (ctId) {
                                0 -> FenceVariant.Single
                                1 -> FenceVariant.Single
                                2 -> FenceVariant.HasTop
                                3 -> FenceVariant.HasTop
                                4 -> FenceVariant.HasRight
                                5 -> FenceVariant.HasRightAndBottom
                                6 -> FenceVariant.HasRightAndTop
                                7 -> FenceVariant.HasRightAndBottom
                                8 -> FenceVariant.HasLeft
                                9 -> FenceVariant.HasLeftAndBottom
                                10 -> FenceVariant.HasLeftAndTop
                                11 -> FenceVariant.HasLeftAndBottom
                                12 -> FenceVariant.HasLeftAndRight
                                13 -> FenceVariant.HasLeftRightAndBottom
                                14 -> FenceVariant.HasLeftAndRight
                                else -> FenceVariant.HasLeftRightAndBottom
                            },
                        )
                    }

                    SeasonalPlant1, SeasonalPlant2, SeasonalPlant3,
                    SeasonalPlant4, SeasonalPlant5, SeasonalPlant6,
                    SeasonalDecor,
                    JunimoHut,
                    -> SpriteQualifier.SeasonQualifier(season)

                    TubOFlowers -> SpriteQualifier.TubOFlowersQualifier(
                        when (season) {
                            Spring, Summer -> true
                            Fall, Winter -> false
                        }
                    )

                    Floor.Grass -> SpriteQualifier.None

                    Floor.SteppingStonePath -> SpriteQualifier.SteppingStonePathQualifier(season != Winter)

                    is Floor -> {
                        val hasBottomNeighbour = fe[place + vec(x = 0, y = 1)] == entity
                        val hasTopNeighbour = fe[place - vec(x = 0, y = 1)] == entity
                        val hasRightNeighbour = fe[place + vec(x = 1, y = 0)] == entity
                        val hasLeftNeighbour = fe[place - vec(x = 1, y = 0)] == entity

                        val ctId =
                            1 * (if (hasBottomNeighbour) 1 else 0) +
                                2 * (if (hasTopNeighbour) 1 else 0) +
                                4 * (if (hasRightNeighbour) 1 else 0) +
                                8 * (if (hasLeftNeighbour) 1 else 0)

                        SpriteQualifier.FloorQualifier(
                            variant = when (ctId) {
                                0 -> FloorVariant.Single
                                1 -> FloorVariant.ColTop
                                2 -> FloorVariant.ColBottom
                                3 -> FloorVariant.ColCenter
                                4 -> FloorVariant.RowLeft
                                5 -> FloorVariant.TopLeft
                                6 -> FloorVariant.BottomLeft
                                7 -> FloorVariant.CenterLeft
                                8 -> FloorVariant.RowRight
                                9 -> FloorVariant.TopRight
                                10 -> FloorVariant.BottomRight
                                11 -> FloorVariant.CenterRight
                                12 -> FloorVariant.RowCenter
                                13 -> FloorVariant.TopCenter
                                14 -> FloorVariant.BottomCenter
                                else -> FloorVariant.CenterCenter
                            },
                            isNotWinter = season != Winter,
                        )
                    }

                    else -> SpriteQualifier.None
                }

                add(Pair(placedEntity, spriteBy(spriteMaps, entity, qualifier)))
            }
        }
    }

    fun calculateSprite(
        spriteMaps: Map<SpritePage, ImageBitmap>,
        entity: Entity<*>,
        season: Season,
    ): Sprite {
        val qualifier = when (entity) {
            WoodFence, StoneFence, IronFence, HardwoodFence,
            -> {
                SpriteQualifier.FenceQualifier(
                    variant = FenceVariant.Single,
                )
            }

            SeasonalPlant1, SeasonalPlant2, SeasonalPlant3,
            SeasonalPlant4, SeasonalPlant5, SeasonalPlant6,
            SeasonalDecor,
            JunimoHut,
            -> SpriteQualifier.SeasonQualifier(season)

            TubOFlowers -> SpriteQualifier.TubOFlowersQualifier(
                when (season) {
                    Spring, Summer -> true
                    Fall, Winter -> false
                }
            )

            Floor.SteppingStonePath -> SpriteQualifier.SteppingStonePathQualifier(season != Winter)

            Floor.Grass -> SpriteQualifier.None

            is Floor -> SpriteQualifier.FloorQualifier(
                variant = FloorVariant.Single,
                isNotWinter = season != Winter,
            )

            else -> SpriteQualifier.None
        }
        return spriteBy(spriteMaps, entity, qualifier)
    }

    fun calculateSprite(
        spriteMaps: Map<SpritePage, ImageBitmap>,
        entities: Collection<PlacedEntity<*>>,
        season: Season,
    ): Sequence<Pair<PlacedEntity<*>, Sprite>> = entities.asSequence().map { placedEntity ->
        val sprite = calculateSprite(spriteMaps, placedEntity.rectObject, season)
        placedEntity to sprite
    }
}

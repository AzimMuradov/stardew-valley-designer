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

package io.stardewvalleydesigner.data

import io.stardewvalleydesigner.data.internal.*
import io.stardewvalleydesigner.engine.entity.*
import io.stardewvalleydesigner.engine.entity.Building.ColoredFarmBuilding
import io.stardewvalleydesigner.engine.entity.Building.SimpleBuilding.JunimoHut
import io.stardewvalleydesigner.engine.entity.Equipment.SimpleEquipment.*


object EntityDataProvider {

    fun entityToId(entity: Entity<*>): EntityId = when (entity) {
        is ColoredFarmBuilding -> building(QualifiedEntity(entity)).entityId
        else -> m2.getValue(entity)
    }

    fun entityById(id: EntityId): Entity<*>? = when (id.flavor) {
        is FarmBuildingColors -> when (val building = m3[id.default] as? ColoredFarmBuilding) {
            null -> null
            is Building.Barn3 -> building.copy(id.flavor)
            is Building.Coop3 -> building.copy(id.flavor)
            is Building.Shed2 -> building.copy(id.flavor)
            is Building.Stable -> building.copy(id.flavor)
            is Building.StoneCabin3 -> building.copy(id.flavor)
            is Building.PlankCabin3 -> building.copy(id.flavor)
            is Building.LogCabin3 -> building.copy(id.flavor)
        }

        else -> m3[id]
    }

    fun entityToData(qe: QualifiedEntity<*>): SpriteId = when (val e = qe.entity) {
        is ColoredFarmBuilding -> building(QualifiedEntity(e)).spriteId
        else -> m1.getValue(qe)
    }


    private val mapping by lazy {
        buildSet {
            addAll(
                (Building.all - JunimoHut).map {
                    building(QualifiedEntity(entity = it))
                }
            )
            addAll(
                Season.entries.map {
                    building(
                        QualifiedEntity(
                            entity = JunimoHut,
                            qualifier = SpriteQualifier.SeasonQualifier(it)
                        )
                    )
                }
            )
            addAll(
                Crop.all.map {
                    crop(QualifiedEntity(it))
                }
            )
            addAll(
                (Equipment.all - setOf(
                    SeasonalPlant1, SeasonalPlant2, SeasonalPlant3,
                    SeasonalPlant4, SeasonalPlant5, SeasonalPlant6,
                    SeasonalDecor, TubOFlowers,
                    WoodFence, StoneFence, IronFence, HardwoodFence,
                )).map {
                    equipment(QualifiedEntity(entity = it))
                }
            )
            addAll(
                listOf(
                    SeasonalPlant1, SeasonalPlant2, SeasonalPlant3,
                    SeasonalPlant4, SeasonalPlant5, SeasonalPlant6,
                    SeasonalDecor,
                ).flatMap {
                    Season.entries.map { season ->
                        equipment(QualifiedEntity(entity = it, SpriteQualifier.SeasonQualifier(season)))
                    }
                }
            )
            addAll(
                listOf(
                    WoodFence, StoneFence,
                    IronFence, HardwoodFence,
                ).flatMap {
                    FenceVariant.entries.map { variant ->
                        equipment(QualifiedEntity(entity = it, SpriteQualifier.FenceQualifier(variant)))
                    }
                }
            )
            addAll(
                listOf(true, false).map {
                    equipment(
                        QualifiedEntity(
                            entity = TubOFlowers,
                            qualifier = SpriteQualifier.TubOFlowersQualifier(it)
                        )
                    )
                }
            )
            addAll(
                FloorFurniture.all.map {
                    floorFurniture(QualifiedEntity(entity = it))
                }
            )
            addAll(
                FloorVariant.entries.flatMap { ct ->
                    listOf(true, false).flatMap { season ->
                        (Floor.all - listOf(Floor.Grass, Floor.SteppingStonePath)).map { floor ->
                            floor(
                                QualifiedEntity(
                                    entity = floor,
                                    qualifier = SpriteQualifier.FloorQualifier(ct, season),
                                )
                            )
                        }
                    }
                }
            )
            add(floor(QualifiedEntity(entity = Floor.Grass)))
            addAll(
                listOf(true, false).map {
                    floor(
                        QualifiedEntity(
                            entity = Floor.SteppingStonePath,
                            qualifier = SpriteQualifier.SteppingStonePathQualifier(it),
                        )
                    )
                }
            )
            // addAll(
            //     HouseFurniture.all.map {
            //         houseFurniture(QualifiedEntity(entity = it))
            //     }
            // )
            addAll(
                IndoorFurniture.all.map {
                    indoorFurniture(QualifiedEntity(entity = it))
                }
            )
            addAll(
                UniversalFurniture.all.map {
                    universalFurniture(QualifiedEntity(entity = it))
                }
            )
        }
    }


    private val m1 by lazy {
        mapping.associate {
            it.qualifiedEntity to it.spriteId
        }
    }

    private val m2 by lazy {
        mapping.associate {
            it.qualifiedEntity.entity to it.entityId
        }
    }

    private val m3 by lazy {
        mapping.associate {
            it.entityId to it.qualifiedEntity.entity
        }
    }
}

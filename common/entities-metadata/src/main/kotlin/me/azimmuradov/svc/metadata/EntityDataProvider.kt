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

package me.azimmuradov.svc.metadata

import me.azimmuradov.svc.engine.entity.*
import me.azimmuradov.svc.metadata.internal.*


object EntityDataProvider {

    val entityToId by lazy {
        entityToMetadata.mapValues { it.value.id }
    }

    val entityById by lazy {
        entityToMetadata.asSequence().map { (e, meta) -> meta.id to e }.toMap()
    }

    val entityMetadataById by lazy {
        Equipment.all.associateBy { equipment(it).id }
    }

    val entityToMetadata by lazy {
        Building.all.associateWith(::building) +
                Crop.all.associateWith(::crop) +
                Equipment.all.associateWith(::equipment) +
                Floor.all.associateWith(::floor) +
                FloorFurniture.all.associateWith(::floorFurniture) +
                // HouseFurniture.all.associateWith(::houseFurniture) +
                IndoorFurniture.all.associateWith(::indoorFurniture) +
                UniversalFurniture.all.associateWith(::universalFurniture)
    }


    fun fullDataOf(entity: Entity<*>) = EntityFullData(
        entity = entity,
        metadata = entityToMetadata.getValue(entity)
    )
}

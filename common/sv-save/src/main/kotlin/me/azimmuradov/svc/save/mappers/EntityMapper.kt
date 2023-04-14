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

package me.azimmuradov.svc.save.mappers

import me.azimmuradov.svc.engine.entity.Equipment
import me.azimmuradov.svc.engine.entity.PlacedEntity
import me.azimmuradov.svc.engine.geometry.xy
import me.azimmuradov.svc.engine.rectmap.placeIt
import me.azimmuradov.svc.metadata.EntityDataProvider.entityById
import me.azimmuradov.svc.metadata.EntityId
import me.azimmuradov.svc.metadata.EntityPage
import me.azimmuradov.svc.save.models.*


fun Object.toPlacedEntity(): PlacedEntity<*> {
    val entityId = EntityId(
        page = when (type) {
            "Crafting" -> EntityPage.Craftables
            else -> EntityPage.CommonObjects
        },
        index = parentSheetIndex
    )
    val entity = entityById[entityId] ?: Equipment.SimpleEquipment.Telephone

    return entity.placeIt(there = tileLocation.toCoordinate())
}

fun Furniture.toPlacedEntity(): PlacedEntity<*> {
    val entityId = EntityId(
        page = EntityPage.Furniture,
        index = parentSheetIndex
    )
    val entity = entityById[entityId] ?: Equipment.SimpleEquipment.Telephone

    return entity.placeIt(there = tileLocation.toCoordinate())
}


fun Position.toCoordinate() = xy(x = x.toInt() - 1, y = y.toInt() - 4)

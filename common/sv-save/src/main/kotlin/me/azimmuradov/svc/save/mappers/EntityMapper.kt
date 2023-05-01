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

import me.azimmuradov.svc.engine.entity.PlacedEntity
import me.azimmuradov.svc.engine.entity.Rotations
import me.azimmuradov.svc.engine.geometry.xy
import me.azimmuradov.svc.engine.layer.placeIt
import me.azimmuradov.svc.metadata.EntityDataProvider.entityById
import me.azimmuradov.svc.metadata.EntityId
import me.azimmuradov.svc.metadata.EntityPage
import me.azimmuradov.svc.save.models.*


fun Object.toPlacedEntityOrNull(): PlacedEntity<*>? {
    val entityId = EntityId(
        page = when (type) {
            "Crafting" -> EntityPage.Craftables
            else -> EntityPage.CommonObjects
        },
        localId = parentSheetIndex
    )
    return entityById[entityId]?.placeIt(there = tileLocation.toCoordinate())
}

fun Furniture.toPlacedEntityOrNull(): PlacedEntity<*>? {
    val rotation = when (rotations) {
        2 -> when (currentRotation) {
            1 -> Rotations.Rotations2.R0
            2 -> Rotations.Rotations2.R1
            else -> Rotations.Rotations2.R0
        }

        4 -> when (currentRotation) {
            1 -> Rotations.Rotations4.R0
            2 -> Rotations.Rotations4.R1
            3 -> Rotations.Rotations4.R2
            4 -> Rotations.Rotations4.R3
            else -> Rotations.Rotations4.R0
        }

        else -> null
    }

    val entityId = EntityId(
        page = EntityPage.Furniture,
        localId = parentSheetIndex,
        flavor = rotation
    )

    return entityById[entityId]?.placeIt(there = tileLocation.toCoordinate())
}

fun Item<Vector2Wrapper, TerrainFeatureWrapper>.toPlacedEntityOrNull(): PlacedEntity<*>? {
    val (v2, tfw) = this
    val entityId = EntityId(
        page = EntityPage.Flooring,
        localId = tfw.tf.whichFloor
    )
    return entityById[entityId]?.placeIt(there = v2.pos.toCoordinate())
}


fun Position.toCoordinate() = xy(x = x.toInt() - 1, y = y.toInt() - 4)

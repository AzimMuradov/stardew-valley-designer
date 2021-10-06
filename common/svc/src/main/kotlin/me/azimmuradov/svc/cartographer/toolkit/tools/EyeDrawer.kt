/*
 * Copyright 2021-2021 Azim Muradov
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

package me.azimmuradov.svc.cartographer.toolkit

import me.azimmuradov.svc.cartographer.history.HistoryUnit
import me.azimmuradov.svc.cartographer.history.HistoryUnitsRegisterer
import me.azimmuradov.svc.engine.entity.Entity
import me.azimmuradov.svc.engine.geometry.Coordinate


internal class EyeDrawer(
    unitsRegisterer: HistoryUnitsRegisterer,
    private val onPickStart: (c: Coordinate) -> Pair<Entity<*>?, Entity<*>?>,
    private val onPick: (c: Coordinate) -> Entity<*>?,
    private val onPickEnd: (pickedEntity: Entity<*>, previouslyInUse: Entity<*>?) -> HistoryUnit,
) : RevertibleTool(
    type = ToolType.EyeDropper,
    unitsRegisterer = unitsRegisterer,
) {

    override fun startBody(c: Coordinate): Pair<Boolean, HistoryUnit?> {
        val (pickedEntity, previouslyInUse) = onPickStart(c)
        this.pickedEntity = pickedEntity
        this.previouslyInUse = previouslyInUse
        return true to null
    }

    override fun keepBody(c: Coordinate): HistoryUnit? {
        pickedEntity = onPick(c)
        return null
    }

    override fun endBody(): HistoryUnit? {
        val pe = pickedEntity
        return if (pe != null && pe != previouslyInUse) onPickEnd(pe, previouslyInUse) else null
    }


    private var pickedEntity: Entity<*>? = null
    private var previouslyInUse: Entity<*>? = null
}
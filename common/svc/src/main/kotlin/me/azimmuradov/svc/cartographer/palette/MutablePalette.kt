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

package me.azimmuradov.svc.cartographer.palette

import me.azimmuradov.svc.engine.entity.ids.EntityId


interface MutablePalette : Palette {

    fun putInUse(item: EntityId<*>): EntityId<*>?

    fun clearUsed(): EntityId<*>?

    fun clearHotbar(): List<EntityId<*>?>


    fun putOnHotbar(index: Int, item: EntityId<*>): EntityId<*>?

    fun removeFromHotbar(index: Int): EntityId<*>?
}

fun MutablePalette.clear() {
    clearUsed()
    clearHotbar()
}
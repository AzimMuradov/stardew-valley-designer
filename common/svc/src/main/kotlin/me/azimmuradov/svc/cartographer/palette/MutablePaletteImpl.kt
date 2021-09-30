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

import androidx.compose.runtime.*
import me.azimmuradov.svc.engine.entity.Entity


fun mutablePaletteOf(size: UInt): MutablePalette = MutablePaletteImpl(size)


private class MutablePaletteImpl(size: UInt) : MutablePalette {

    override var inUse by mutableStateOf<Entity<*>?>(null)

    override val hotbar = mutableStateListOf(*Array<Entity<*>?>(size.toInt()) { null })


    override fun putInUse(entity: Entity<*>) = inUse.also { inUse = entity }

    override fun clearUsed() = inUse.also { inUse = null }

    override fun clearHotbar() = hotbar.toList().also { hotbar.clear() }


    override fun putOnHotbar(index: Int, entity: Entity<*>) = hotbar[index].also { hotbar[index] = entity }

    override fun removeFromHotbar(index: Int) = hotbar[index].also { hotbar[index] = null }
}
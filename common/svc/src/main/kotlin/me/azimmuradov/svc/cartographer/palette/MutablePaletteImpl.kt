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
import me.azimmuradov.svc.engine.entity.ids.EntityId


fun mutablePaletteOf(size: UInt): MutablePalette = MutablePaletteImpl(size)


private class MutablePaletteImpl(private val size: UInt) : MutablePalette {

    override var inUse by mutableStateOf<EntityId<*>?>(null)

    override var hotbar by mutableStateOf(List<EntityId<*>?>(size.toInt()) { null })


    override fun putInUse(item: EntityId<*>) = inUse.also { inUse = item }

    override fun clearUsed() = inUse.also { inUse = null }

    override fun clearHotbar() = hotbar.also {
        hotbar = List<EntityId<*>?>(size.toInt()) { null }
    }


    override fun putOnHotbar(index: Int, item: EntityId<*>) = hotbar[index].also {
        val mutHotbar = hotbar.toMutableList()
        mutHotbar[index] = item
        hotbar = mutHotbar
    }

    override fun removeFromHotbar(index: Int) = hotbar[index].also {
        val mutHotbar = hotbar.toMutableList()
        mutHotbar[index] = null
        hotbar = mutHotbar
    }
}
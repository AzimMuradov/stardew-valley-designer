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

package me.azimmuradov.svc.cartographer.modules.palette

import me.azimmuradov.svc.cartographer.state.PaletteState
import me.azimmuradov.svc.engine.entity.Entity

internal class ObservablePalette(
    size: UInt,
    private val onPaletteChanged: (PaletteState) -> Unit,
) : MutablePalette {

    override var inUse: Entity<*>? = null

    override val hotbar = MutableList<Entity<*>?>(size.toInt()) { null }


    override fun putInUse(entity: Entity<*>) = inUse
        .also { inUse = entity }
        .alsoObserve(inUse)

    override fun clearUsed() = inUse
        .also { inUse = null }
        .alsoObserve(inUse = null)


    override fun putOnHotbar(index: Int, entity: Entity<*>) = hotbar[index]
        .also { hotbar[index] = entity }
        .alsoObserve(hotbar)

    override fun removeFromHotbar(index: Int) = hotbar[index]
        .also { hotbar[index] = null }
        .alsoObserve(hotbar)

    override fun clearHotbar() = hotbar
        .also { hotbar.clear() }
        .alsoObserve(hotbar)


    // Observers & Updaters

    private fun <T> T.alsoObserve(inUse: Entity<*>?): T = also {
        onPaletteChanged(PaletteState(inUse /* TODO : hotbar */))
    }

    private fun <T> T.alsoObserve(hotbar: List<Entity<*>?>): T = also {
        onPaletteChanged(PaletteState(inUse /* TODO : hotbar.toList() */))
    }

    fun update(palette: PaletteState) {
        inUse = palette.inUse
        // TODO : Update hotbar
    }
}
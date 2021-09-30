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

package me.azimmuradov.svc.cartographer

import androidx.compose.runtime.*
import me.azimmuradov.svc.cartographer.palette.mutablePaletteOf
import me.azimmuradov.svc.cartographer.toolkit.Toolkit
import me.azimmuradov.svc.cartographer.toolkit.tools.*
import me.azimmuradov.svc.engine.*
import me.azimmuradov.svc.engine.entity.PlacedEntity
import me.azimmuradov.svc.engine.layout.Layout
import me.azimmuradov.svc.engine.rectmap.PlacedRectObject


fun svcOf(layout: Layout): Svc = SvcImpl(layout)


private class SvcImpl(override val layout: Layout) : Svc {

    // Backing svc engine

    val engine: SvcEngine = svcEngineOf(layout)


    // Views

    override val layers = engine.layers


    override var heldEntities by mutableStateOf(listOf<PlacedEntity<*>>())


    override val palette = mutablePaletteOf(size = 10u)


    override val toolkit: Toolkit = Toolkit(
        hand = Hand(
            startBlock = { c ->
                heldEntities = engine.remove(c).values.filterNotNull()

                heldEntities
            },
            keepBlock = { es -> heldEntities = es },
            endBlock = { es ->
                heldEntities = listOf()
                engine.putAll(es)
            },
        ),
        pen = Pen(
            startBlock = { c ->
                val entity = palette.inUse

                if (entity != null) {
                    engine.put(PlacedRectObject(
                        rectObj = entity,
                        place = c,
                    ))
                }
            },
            keepBlock = { c ->
                val entity = palette.inUse

                if (entity != null) {
                    engine.put(PlacedRectObject(
                        rectObj = entity,
                        place = c,
                    ))
                }
            },
            endBlock = {},
        ),
        eraser = Eraser(
            startBlock = { c -> engine.remove(c) },
            keepBlock = { c -> engine.remove(c) },
            endBlock = {},
        ),
    )
}
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

import io.stardewvalleydesigner.engine.geometry.Rect
import io.stardewvalleydesigner.engine.geometry.rectOf


enum class SpritePage(val width: Int, val height: Int, val grain: Rect = rectOf(1, 1)) {
    CommonObjects(width = 384, height = 624),
    Craftables(width = 128, height = 1152, grain = rectOf(1, 2)),
    Furniture(width = 512, height = 1488),
    Flooring(width = 256, height = 256),
    FlooringWinter(width = 256, height = 256),
    Fence1(width = 48, height = 352, grain = rectOf(1, 2)),
    Fence2(width = 48, height = 352, grain = rectOf(1, 2)),
    Fence3(width = 48, height = 352, grain = rectOf(1, 2)),
    Fence5(width = 48, height = 352, grain = rectOf(1, 2)),
    Crops(width = 32, height = 768, grain = rectOf(1, 2)),

    Barn1(width = 112, height = 112),
    Barn2(width = 112, height = 112),
    Barn3(width = 112, height = 112),

    Coop1(width = 96, height = 112),
    Coop2(width = 96, height = 112),
    Coop3(width = 96, height = 112),

    Shed(width = 112, height = 128),
    BigShed(width = 112, height = 128),

    StoneCabin(width = 240, height = 112),
    PlankCabin(width = 240, height = 112),
    LogCabin(width = 240, height = 112),

    EarthObelisk(width = 48, height = 128),
    WaterObelisk(width = 48, height = 128),
    DesertObelisk(width = 48, height = 128),
    IslandObelisk(width = 48, height = 128),
    JunimoHut(width = 256, height = 64),
    GoldClock(width = 48, height = 80),

    Mill(width = 64, height = 128),
    Silo(width = 48, height = 128),
    Well(width = 48, height = 80),
    Stable(width = 64, height = 96),
    FishPond(width = 80, height = 112),
    SlimeHutch(width = 176, height = 144),
    ShippingBin(width = 32, height = 32),
    ;


    companion object {

        const val UNIT = 16
    }
}

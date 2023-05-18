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

package me.azimmuradov.svc.cartographer.res

import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import me.azimmuradov.svc.metadata.EntityPage


object ImageProvider {

    fun imageOf(file: EntityPage) = images.getValue(file)

    private val images = mapOf(
        EntityPage.CommonObjects to useResource("entities/common-objects.png", ::loadImageBitmap),
        EntityPage.Craftables to useResource("entities/craftables.png", ::loadImageBitmap),
        EntityPage.Furniture to useResource("entities/furniture.png", ::loadImageBitmap),
        EntityPage.Flooring to useResource("entities/flooring.png", ::loadImageBitmap),
        EntityPage.Crops to useResource("entities/crops.png", ::loadImageBitmap),

        EntityPage.Barn1 to useResource("buildings/barn.png", ::loadImageBitmap),
        EntityPage.Barn2 to useResource("buildings/big-barn.png", ::loadImageBitmap),
        EntityPage.Barn3 to useResource("buildings/deluxe-barn.png", ::loadImageBitmap),

        EntityPage.Coop1 to useResource("buildings/coop.png", ::loadImageBitmap),
        EntityPage.Coop2 to useResource("buildings/big-coop.png", ::loadImageBitmap),
        EntityPage.Coop3 to useResource("buildings/deluxe-coop.png", ::loadImageBitmap),

        EntityPage.Shed to useResource("buildings/shed.png", ::loadImageBitmap),
        EntityPage.BigShed to useResource("buildings/big-shed.png", ::loadImageBitmap),

        EntityPage.StoneCabin to useResource("buildings/stone-cabin.png", ::loadImageBitmap),
        EntityPage.PlankCabin to useResource("buildings/plank-cabin.png", ::loadImageBitmap),
        EntityPage.LogCabin to useResource("buildings/log-cabin.png", ::loadImageBitmap),

        EntityPage.EarthObelisk to useResource("buildings/earth-obelisk.png", ::loadImageBitmap),
        EntityPage.WaterObelisk to useResource("buildings/water-obelisk.png", ::loadImageBitmap),
        EntityPage.DesertObelisk to useResource("buildings/desert-obelisk.png", ::loadImageBitmap),
        EntityPage.IslandObelisk to useResource("buildings/island-obelisk.png", ::loadImageBitmap),
        EntityPage.JunimoHut to useResource("buildings/junimo-hut.png", ::loadImageBitmap),
        EntityPage.GoldClock to useResource("buildings/gold-clock.png", ::loadImageBitmap),

        EntityPage.Mill to useResource("buildings/mill.png", ::loadImageBitmap),
        EntityPage.Silo to useResource("buildings/silo.png", ::loadImageBitmap),
        EntityPage.Well to useResource("buildings/well.png", ::loadImageBitmap),
        EntityPage.Stable to useResource("buildings/stable.png", ::loadImageBitmap),
        EntityPage.FishPond to useResource("buildings/fish-pond.png", ::loadImageBitmap),
        EntityPage.SlimeHutch to useResource("buildings/slime-hutch.png", ::loadImageBitmap),
        EntityPage.ShippingBin to useResource("buildings/shipping-bin.png", ::loadImageBitmap),
    )
}

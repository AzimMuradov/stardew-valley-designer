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

package me.azimmuradov.svc.engine.impl.entity.ids

import me.azimmuradov.svc.engine.Rect
import me.azimmuradov.svc.engine.impl.Color
import me.azimmuradov.svc.engine.impl.entity.EntityWithoutFloorType.BuildingType
import me.azimmuradov.svc.engine.impl.entity.ids.ColoredFlavor.ColoredFishPondFlavor
import me.azimmuradov.svc.engine.impl.entity.ids.ColoredFlavor.Colors.FishPondColors
import me.azimmuradov.svc.engine.impl.entity.ids.ColoredFlavor.Colors.FishPondColors.Default


sealed interface Building : CartographerEntityId<BuildingType> {

    enum class SimpleBuilding(override val size: Rect) : Building {

        // // TODO
        // // Farmhouse
        //
        // Farmhouse1(FARMHOUSE_SIZE),
        // Farmhouse2(FARMHOUSE_SIZE),


        // Barns

        Barn1(BARN_SIZE),
        Barn2(BARN_SIZE),


        // Coops

        Coop1(COOP_SIZE),
        Coop2(COOP_SIZE),


        // Sheds

        Shed1(SHED_SIZE),


        // Cabins

        StoneCabin1(CABIN_SIZE),
        StoneCabin2(CABIN_SIZE),

        PlankCabin1(CABIN_SIZE),
        PlankCabin2(CABIN_SIZE),

        LogCabin1(CABIN_SIZE),
        LogCabin2(CABIN_SIZE),


        // Magical Buildings

        EarthObelisk(MAGICAL_BUILDING_SIZE),
        WaterObelisk(MAGICAL_BUILDING_SIZE),
        DesertObelisk(MAGICAL_BUILDING_SIZE),
        IslandObelisk(MAGICAL_BUILDING_SIZE),
        JunimoHut(MAGICAL_BUILDING_SIZE),
        GoldClock(MAGICAL_BUILDING_SIZE),


        // Others

        Mill(size = Rect(w = 4, h = 2)),
        Silo(size = Rect(w = 3, h = 3)),
        SlimeHutch(size = Rect(w = 11, h = 6)),
        Well(size = Rect(w = 3, h = 3)),
        ShippingBin(size = Rect(w = 2, h = 1)),
    }

    sealed class ColoredFarmBuilding(override val size: Rect) : Building, ColoredFarmBuildingFlavor()

    // TODO
    // // Farmhouse
    //
    // data class Farmhouse3(
    //     override var c1: Color?,
    //     override var c2: Color?,
    //     override var c3: Color?,
    // ) : ColoredBuilding(FARMHOUSE_SIZE)

    data class Barn3(
        override var building: Color? = null,
        override var roof: Color? = null,
        override var trim: Color? = null,
    ) : ColoredFarmBuilding(BARN_SIZE)

    data class Coop3(
        override var building: Color? = null,
        override var roof: Color? = null,
        override var trim: Color? = null,
    ) : ColoredFarmBuilding(COOP_SIZE)

    data class Shed2(
        override var building: Color? = null,
        override var roof: Color? = null,
        override var trim: Color? = null,
    ) : ColoredFarmBuilding(SHED_SIZE)

    data class Stable(
        override var building: Color? = null,
        override var roof: Color? = null,
        override var trim: Color? = null,
    ) : ColoredFarmBuilding(size = Rect(w = 2, h = 1))

    data class StoneCabin3(
        override var building: Color? = null,
        override var roof: Color? = null,
        override var trim: Color? = null,
    ) : ColoredFarmBuilding(CABIN_SIZE)

    data class PlankCabin3(
        override var building: Color? = null,
        override var roof: Color? = null,
        override var trim: Color? = null,
    ) : ColoredFarmBuilding(CABIN_SIZE)

    data class LogCabin3(
        override var building: Color? = null,
        override var roof: Color? = null,
        override var trim: Color? = null,
    ) : ColoredFarmBuilding(CABIN_SIZE)

    data class FishPond(override var color: FishPondColors = Default) : Building, ColoredFishPondFlavor() {

        override val size: Rect = Rect(w = 5, h = 5)
    }


    override val type: BuildingType get() = BuildingType

    companion object {

        fun values(): List<Building> =
            SimpleBuilding.values().toList() + listOf(
                Barn3(), Coop3(), Shed2(), Stable(),
                StoneCabin3(), PlankCabin3(), LogCabin3(),
                FishPond(),
            )


        private val BARN_SIZE = Rect(w = 7, h = 4)

        private val COOP_SIZE = Rect(w = 6, h = 3)

        private val SHED_SIZE = Rect(w = 7, h = 3)

        // private val FARMHOUSE_SIZE = Rect(w = 9, h = 6)

        private val CABIN_SIZE = Rect(w = 5, h = 3)

        private val MAGICAL_BUILDING_SIZE = Rect(w = 3, h = 2)
    }
}
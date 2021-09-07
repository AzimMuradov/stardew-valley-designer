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

package me.azimmuradov.svc.engine.llsvc.entity.ids

import me.azimmuradov.svc.engine.Rect
import me.azimmuradov.svc.engine.llsvc.Color
import me.azimmuradov.svc.engine.llsvc.entity.EntityWithoutFloorType.BuildingType
import me.azimmuradov.svc.engine.llsvc.entity.ids.ColoredFlavor.ColoredFishPondFlavor
import me.azimmuradov.svc.engine.llsvc.entity.ids.ColoredFlavor.Colors.FishPondColors
import me.azimmuradov.svc.engine.llsvc.entity.ids.ColoredFlavor.Colors.FishPondColors.Default


sealed interface Building : SvcEntityId<BuildingType> {

    enum class SimpleBuilding(override val size: Rect) : Building {

        // Barns

        Barn1(barnSize),
        Barn2(barnSize),


        // Coops

        Coop1(coopSize),
        Coop2(coopSize),


        // Sheds

        Shed1(shedSize),


        // Cabins

        StoneCabin1(cabinSize),
        StoneCabin2(cabinSize),

        PlankCabin1(cabinSize),
        PlankCabin2(cabinSize),

        LogCabin1(cabinSize),
        LogCabin2(cabinSize),


        // Magical Buildings

        EarthObelisk(magicalBuildingSize),
        WaterObelisk(magicalBuildingSize),
        DesertObelisk(magicalBuildingSize),
        IslandObelisk(magicalBuildingSize),
        JunimoHut(magicalBuildingSize),
        GoldClock(magicalBuildingSize),


        // Others

        Mill(size = Rect(w = 4, h = 2)),
        Silo(size = Rect(w = 3, h = 3)),
        Well(size = Rect(w = 3, h = 3)),
        SlimeHutch(size = Rect(w = 11, h = 6)),
        ShippingBin(size = Rect(w = 2, h = 1)),
    }

    sealed class ColoredFarmBuilding(override val size: Rect) : Building, ColoredFarmBuildingFlavor()

    data class Barn3(
        override var building: Color? = null,
        override var roof: Color? = null,
        override var trim: Color? = null,
    ) : ColoredFarmBuilding(barnSize)

    data class Coop3(
        override var building: Color? = null,
        override var roof: Color? = null,
        override var trim: Color? = null,
    ) : ColoredFarmBuilding(coopSize)

    data class Shed2(
        override var building: Color? = null,
        override var roof: Color? = null,
        override var trim: Color? = null,
    ) : ColoredFarmBuilding(shedSize)

    data class Stable(
        override var building: Color? = null,
        override var roof: Color? = null,
        override var trim: Color? = null,
    ) : ColoredFarmBuilding(size = Rect(w = 2, h = 1))

    data class StoneCabin3(
        override var building: Color? = null,
        override var roof: Color? = null,
        override var trim: Color? = null,
    ) : ColoredFarmBuilding(cabinSize)

    data class PlankCabin3(
        override var building: Color? = null,
        override var roof: Color? = null,
        override var trim: Color? = null,
    ) : ColoredFarmBuilding(cabinSize)

    data class LogCabin3(
        override var building: Color? = null,
        override var roof: Color? = null,
        override var trim: Color? = null,
    ) : ColoredFarmBuilding(cabinSize)

    data class FishPond(override var color: FishPondColors = Default) : Building, ColoredFishPondFlavor() {

        override val size: Rect = Rect(w = 5, h = 5)
    }


    override val type: BuildingType get() = BuildingType
}

private val barnSize = Rect(w = 7, h = 4)
private val coopSize = Rect(w = 6, h = 3)
private val shedSize = Rect(w = 7, h = 3)
private val cabinSize = Rect(w = 5, h = 3)
private val magicalBuildingSize = Rect(w = 3, h = 2)
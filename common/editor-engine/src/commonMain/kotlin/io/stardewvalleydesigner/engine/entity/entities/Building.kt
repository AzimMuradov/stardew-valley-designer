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

@file:Suppress("PackageDirectoryMismatch")

package io.stardewvalleydesigner.engine.entity

import io.stardewvalleydesigner.engine.entity.Colored.ColoredFishPond
import io.stardewvalleydesigner.engine.entity.Colors.FishPondColors
import io.stardewvalleydesigner.engine.entity.Colors.FishPondColors.Default
import io.stardewvalleydesigner.engine.entity.EntityWithoutFloorType.BuildingType
import io.stardewvalleydesigner.engine.entity.RectsProvider.rectOf
import io.stardewvalleydesigner.engine.geometry.Rect


sealed interface Building : Entity<BuildingType> {

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

        Mill(size = rectOf(w = 4, h = 2)),
        Silo(size = rectOf(w = 3, h = 3)),
        Well(size = rectOf(w = 3, h = 3)),
        SlimeHutch(size = rectOf(w = 11, h = 6)),
        ShippingBin(size = rectOf(w = 2, h = 1)),
    }

    sealed class ColoredFarmBuilding(override val size: Rect) : Building, TripleColoredFarmBuilding()

    data class Barn3(
        override val farmBuildingColors: FarmBuildingColors = FarmBuildingColors(),
    ) : ColoredFarmBuilding(barnSize)

    data class Coop3(
        override val farmBuildingColors: FarmBuildingColors = FarmBuildingColors(),
    ) : ColoredFarmBuilding(coopSize)

    data class Shed2(
        override val farmBuildingColors: FarmBuildingColors = FarmBuildingColors(),
    ) : ColoredFarmBuilding(shedSize)

    data class Stable(
        override val farmBuildingColors: FarmBuildingColors = FarmBuildingColors(),
    ) : ColoredFarmBuilding(size = rectOf(w = 4, h = 2))

    data class StoneCabin3(
        override val farmBuildingColors: FarmBuildingColors = FarmBuildingColors(),
    ) : ColoredFarmBuilding(cabinSize)

    data class PlankCabin3(
        override val farmBuildingColors: FarmBuildingColors = FarmBuildingColors(),
    ) : ColoredFarmBuilding(cabinSize)

    data class LogCabin3(
        override val farmBuildingColors: FarmBuildingColors = FarmBuildingColors(),
    ) : ColoredFarmBuilding(cabinSize)

    data class FishPond(
        override val color: FishPondColors = Default,
    ) : Building, ColoredFishPond() {

        override val size: Rect = rectOf(w = 5, h = 5)
    }


    override val type: BuildingType get() = BuildingType


    companion object {

        val all by lazy {
            buildSet {
                addAll(SimpleBuilding.entries)
                add(Barn3())
                add(Coop3())
                add(Shed2())
                add(Stable())
                add(StoneCabin3())
                add(PlankCabin3())
                add(LogCabin3())
                add(FishPond())
            }
        }
    }
}

private val barnSize = rectOf(w = 7, h = 4)
private val coopSize = rectOf(w = 6, h = 3)
private val shedSize = rectOf(w = 7, h = 3)
private val cabinSize = rectOf(w = 5, h = 3)
private val magicalBuildingSize = rectOf(w = 3, h = 2)

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

package me.azimmuradov.svc.save

import kotlinx.serialization.decodeFromString
import me.azimmuradov.svc.engine.layers.*
import me.azimmuradov.svc.engine.layout.LayoutType
import me.azimmuradov.svc.engine.layout.LayoutsProvider
import me.azimmuradov.svc.engine.putAll
import me.azimmuradov.svc.engine.svcEngineOf
import me.azimmuradov.svc.save.mappers.toPlacedEntity
import me.azimmuradov.svc.save.models.SaveGame
import nl.adaptivity.xmlutil.serialization.XML
import java.io.File


object SaveDataSerializers {

    private const val RES_DIRECTORY = "../common/sv-save/src/main/resources"

    private const val SD_PATHNAME = "$RES_DIRECTORY/sd.xml"


    @JvmStatic
    fun main(args: Array<String>) {
        val objs = fromSD()

        val engine = svcEngineOf(layout = LayoutsProvider.layoutOf(LayoutType.BigShed)).apply {
            putAll(objs.toLayeredEntities())
        }
    }

    fun fromSD(): LayeredEntitiesData {
        val xml = XML {
            indent = 2
            customPolicy()
        }

        val text = File(SD_PATHNAME).readText().run {
            if (first() == '\uFEFF') drop(n = 1) else this
        }

        println(text.take(100))

        val f = xml.decodeFromString<SaveGame>(text)
        // println(f.gameVersion)
        // println(f.player)
        // println(f.locations.map { it.name })
        // println(f.locations.map { it.type })
        val teaHouse = f.locations.first { it.type == "Farm" }.buildings.filter { it.buildingType == "Big Shed" }[4]

        requireNotNull(teaHouse.indoors)

        // println(xml.encodeToString(teaHouse))
        // println("\n")
        // println(teaHouse.indoors!!.objects.map { it.value.obj.name })
        // println("\n")
        // println(teaHouse.indoors!!.objects.joinToString(separator = "\n") { it.value.obj.toPlacedEntity().toString() })
        // println("\n")
        // println(teaHouse.indoors.furniture.joinToString(separator = "\n") { it.toPlacedEntity().toString() })
        // println("\n")
        // println(teaHouse.indoors.furniture.map { it.toPlacedEntity().rectObject })
        // println("\n")
        // println(entityById.entries.joinToString(separator = "\n") {
        //     "${it.key} --- ${it.value}"
        // })


        return (teaHouse.indoors.furniture.map { it.toPlacedEntity() } +
                teaHouse.indoors.objects.map { it.value.obj.toPlacedEntity() })
            .filter { it.place.y >= 0 }.layeredData()
    }
}

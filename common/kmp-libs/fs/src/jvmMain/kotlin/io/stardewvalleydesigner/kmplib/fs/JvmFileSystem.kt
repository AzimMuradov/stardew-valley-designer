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

package io.stardewvalleydesigner.kmplib.fs

import dev.dirs.UserDirectories
import java.util.*
import kotlin.io.path.Path
import kotlin.io.path.exists
import java.io.File.separator as sep


object JvmFileSystem {

    /**
     * Get the user's home directory.
     */
    fun getHomeDir(): String = UserDirectories.get().homeDir

    /**
     * Get the user's documents directory.
     */
    fun getDocsDir(): String = UserDirectories.get().documentDir

    /**
     * Get the user's pictures directory.
     */
    fun getPicsDir(): String = UserDirectories.get().pictureDir


    fun relative(dir: String, filename: String): String = "$dir$sep$filename"
}


fun JvmFileSystem.getSvdSavesDir(): String = relative(
    dir = getDocsDir(),
    filename = "Stardew Valley Designer",
)

fun JvmFileSystem.getSvdImagesDir(): String = relative(
    dir = getPicsDir(),
    filename = "Stardew Valley Designer",
)

fun JvmFileSystem.getSvSavesDir(): String {
    val os = System.getProperty("os.name").uppercase(Locale.ENGLISH)
    val dataPath = if ("WIN" in os) {
        System.getenv("APPDATA")
    } else {
        "${getHomeDir()}${sep}.config"
    }

    return "$dataPath${sep}StardewValley${sep}Saves"
}


fun String.endSep(): String = "$this$sep"

fun String.takeIfExists(): String? = takeIf { Path(it).exists() }

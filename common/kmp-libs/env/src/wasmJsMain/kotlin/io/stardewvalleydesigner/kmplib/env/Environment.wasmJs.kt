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

package io.stardewvalleydesigner.kmplib.env


/**
 * Execution environment.
 */
actual object Environment {

    /**
     * Get the environment variable with the given [name] defined in `webpack`.
     */
    actual fun getVar(name: String): String? = ENV[name]

    private val ENV: Map<String, String> = customEnv()
        .asList()
        .associate { entry -> entry[0].toString() to entry[1] }
        .filterValues { it != null }
        .mapValues { (_, value) -> value.toString() }


    /**
     * Returns null.
     */
    actual fun getHomeDir(): String? = null

    /**
     * Returns null.
     */
    actual fun getDocsDir(): String? = null

    /**
     * Returns null.
     */
    actual fun getPicsDir(): String? = null


    actual fun getSvdImagesDir(): String? = null

    actual fun getSvdSavesDir(): String? = null


    actual fun relative(dir: String?, filename: String): String? = null

    actual fun relativeIfExists(dir: String?, filename: String): String? = null
}


private fun customEnv(): JsArray<JsArray<JsAny?>> = js("Object.entries(CUSTOM_ENV)")

private fun <T : JsAny?> JsArray<T>.asList(): List<T> = object : AbstractList<T>() {

    override val size: Int get() = this@asList.length

    @Suppress("UNCHECKED_CAST")
    override fun get(index: Int): T = when (index) {
        in 0..lastIndex -> this@asList[index] as T
        else -> throw IndexOutOfBoundsException("index $index is not in range [0..$lastIndex]")
    }
}

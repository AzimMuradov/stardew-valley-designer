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

    private val ENV: Map<String, String> =
        (js("Object.entries") as (dynamic) -> Array<Array<Any?>>)
            .invoke(js("CUSTOM_ENV"))
            .associate { entry -> entry[0] as String to entry[1] }
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
}

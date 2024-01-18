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
expect object Environment {

    /**
     * Get the environment variable with the given [name].
     */
    fun getVar(name: String): String?


    /**
     * Get the user's home directory.
     */
    fun getHomeDir(): String?

    /**
     * Get the user's documents directory.
     */
    fun getDocsDir(): String?

    /**
     * Get the user's pictures directory.
     */
    fun getPicsDir(): String?


    fun getSvdImagesDir(): String?

    fun getSvdSavesDir(): String?


    fun relative(dir: String?, filename: String): String?

    fun relativeIfExists(dir: String?, filename: String): String?
}

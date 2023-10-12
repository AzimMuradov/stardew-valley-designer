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
 *
 * It's a combination of the process environment and the Browser environment.
 */
actual object Environment {

    /**
     * Get the process environment variable.
     */
    actual fun getVar(name: String): String? = TODO()


    actual fun getHomeDir(): String? = null

    actual fun getDocsDir(): String? = null

    actual fun getPicsDir(): String? = null
}

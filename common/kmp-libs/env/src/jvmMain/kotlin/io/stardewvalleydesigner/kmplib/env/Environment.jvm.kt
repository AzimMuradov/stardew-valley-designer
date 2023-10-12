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
 * It's a combination of the process environment and the JVM environment.
 */
actual object Environment {

    /**
     * Get the process environment variable and if there is none get the JVM system property.
     */
    actual fun getVar(name: String): String? = try {
        System.getenv(name)
    } catch (e: SecurityException) {
        null
    } ?: try {
        System.getProperty(name)
    } catch (e: SecurityException) {
        null
    }
}

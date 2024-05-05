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

package io.stardewvalleydesigner.ui.component.editor.screen.topmenu.savedesignasimg

import kotlinx.datetime.*


internal fun Clock.nowFormatted(): String {
    val now = now().toLocalDateTime(TimeZone.currentSystemDefault())
    return with(now) {
        buildList {
            addAll(listOf(year.pad4(), monthNumber.pad2(), dayOfMonth.pad2()))
            addAll(listOf(hour.pad2(), minute.pad2(), second.pad2()))
        }
    }.joinToString(separator = "-")
}


private fun Int.pad2() = toString().padStart(length = 2, padChar = '0')

private fun Int.pad4() = toString().padStart(length = 4, padChar = '0')

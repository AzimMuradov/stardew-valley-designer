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

package io.stardewvalleydesigner.cmplib.filedialogs

import androidx.compose.runtime.Composable
import io.stardewvalleydesigner.LoggerUtils.logger
import io.stardewvalleydesigner.cmplib.filedialogs.DomDialogsWrapper.loadFileFromDisk
import kotlinx.browser.document


@Composable
actual fun FileSaver(
    title: String?,
    defaultPathAndFile: String?,
    extensions: List<String>?,
    extensionsDescription: String?,
    onPathProvided: (String?) -> Unit,
) {
    TODO()
}

@Composable
actual fun FilePicker(
    title: String?,
    defaultPathAndFile: String?,
    extensions: List<String>?,
    extensionsDescription: String?,
    multiSelect: Boolean,
    onFilesSelected: (List<String>?) -> Unit,
) {
    if (title != null) logger.warn { "`title` was ignored" }
    if (defaultPathAndFile != null) logger.warn { "`defaultPathAndFile` was ignored" }
    if (extensionsDescription != null) logger.warn { "`extensionsDescription` was ignored" }

    document.loadFileFromDisk(
        accept = (extensions ?: emptyList()).joinToString { ".$it" },
        multiple = multiSelect,
        onLoaded = { onFilesSelected(listOf(it)) }
    )
}

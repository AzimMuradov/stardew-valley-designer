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

package io.stardewvalleydesigner.cmplib.filedialogs

import androidx.compose.runtime.Composable


@Composable
expect fun FileSaver(
    title: String? = null,
    defaultPathAndFile: String? = null,
    extensions: List<String>? = null,
    extensionsDescription: String? = null,
    bytes: ByteArray,
    onFileSaved: (FileSaverResult?) -> Unit,
)

@Composable
expect fun FilePicker(
    title: String? = null,
    defaultPathAndFile: String? = null,
    extensions: List<String>? = null,
    extensionsDescription: String? = null,
    // multiSelect: Boolean = false, // TODO : Add support for multiple files?
    onFilePicked: (FilePickerResult?) -> Unit,
)


data class FileSaverResult(
    val absolutePath: String?,
)

data class FilePickerResult(
    val text: String,
    // val name: String, // TODO : Add support for file name?
    val absolutePath: String?,
)

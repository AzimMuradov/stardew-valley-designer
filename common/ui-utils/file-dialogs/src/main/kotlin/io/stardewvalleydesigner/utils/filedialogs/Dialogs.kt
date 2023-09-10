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

package io.stardewvalleydesigner.utils.filedialogs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogState
import kotlinx.coroutines.CoroutineScope
import java.nio.file.Path


@Composable
fun FileSaver(
    title: String,
    defaultPathAndFile: String? = null,
    extensions: List<String>? = null,
    extensionsDescription: String? = null,
    onPathProvided: (Path?) -> Unit,
) {
    ModalDialog {
        val paths = TinyFileDialogsWrapper.createSaveFileDialog(
            title,
            defaultPathAndFile = defaultPathAndFile ?: System.getProperty("user.dir"),
            extensions,
            extensionsDescription,
        )?.let(Path::of)

        onPathProvided(paths)
    }
}

@Composable
fun FilePicker(
    title: String,
    defaultPathAndFile: String? = null,
    extensions: List<String>? = null,
    extensionsDescription: String? = null,
    multiSelect: Boolean = false,
    onFilesSelected: (List<Path>?) -> Unit,
) {
    ModalDialog {
        val paths = TinyFileDialogsWrapper.createOpenFileDialog(
            title,
            defaultPathAndFile = defaultPathAndFile ?: System.getProperty("user.dir"),
            extensions,
            extensionsDescription,
            multiSelect,
        )?.map(Path::of)

        onFilesSelected(paths)
    }
}

@Composable
fun DirectoryPicker(
    title: String,
    defaultPath: String? = null,
    onDirectorySelected: (Path?) -> Unit,
) {
    ModalDialog {
        val dirPath = TinyFileDialogsWrapper.createSelectDirectoryDialog(
            title,
            defaultPath = defaultPath ?: System.getProperty("user.dir"),
        )?.let(Path::of)

        onDirectorySelected(dirPath)
    }
}


/**
 * Hack to make [FileSaver], [FilePicker] and [DirectoryPicker] modal.
 */
@Composable
private fun ModalDialog(content: suspend CoroutineScope.() -> Unit) {
    Dialog(
        onCloseRequest = {},
        state = DialogState(size = DpSize.Zero),
        undecorated = true,
        resizable = false,
        enabled = false,
        focusable = false,
        content = { LaunchedEffect(Unit, content) },
    )
}

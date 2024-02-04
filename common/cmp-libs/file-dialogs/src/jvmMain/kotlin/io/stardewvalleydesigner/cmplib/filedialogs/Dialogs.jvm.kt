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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.DialogState
import androidx.compose.ui.window.DialogWindow
import dev.dirs.UserDirectories
import io.stardewvalleydesigner.LoggerUtils.logger
import io.stardewvalleydesigner.kmplib.dispatcher.PlatformDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import java.io.IOException
import kotlin.io.path.*
import java.io.File.separator as sep


@Composable
actual fun FileSaver(
    title: String?,
    defaultPathAndFile: String?,
    extensions: List<String>?,
    extensionsDescription: String?,
    bytes: () -> ByteArray,
    onFileSaved: (FileSaverResult?) -> Unit,
) {
    ModalDialog {
        val result = withContext(PlatformDispatcher.IO) {
            TinyFileDialogsWrapper.createSaveFileDialog(
                title,
                defaultPathAndFile = processDefaultPath(defaultPathAndFile),
                extensions,
                extensionsDescription,
                bytes = bytes(),
            )
        }

        onFileSaved(result)
    }
}

@Composable
actual fun FilePicker(
    title: String?,
    defaultPathAndFile: String?,
    extensions: List<String>?,
    extensionsDescription: String?,
    onFilePicked: (FilePickerResult?) -> Unit,
) {
    ModalDialog {
        val result = withContext(PlatformDispatcher.IO) {
            TinyFileDialogsWrapper.createOpenFileDialog(
                title,
                defaultPathAndFile = processDefaultPath(defaultPathAndFile),
                extensions,
                extensionsDescription,
            )
        }

        onFilePicked(result)
    }
}


private fun processDefaultPath(defaultPathAndFile: String?): String {
    val pathString = (defaultPathAndFile ?: UserDirectories.get().homeDir) + sep

    try {
        val path = Path(pathString)
        if (path.isDirectory()) path.createDirectories() else path.createParentDirectories()
    } catch (e: IOException) {
        logger.error(e) { "Can't create directories for '$pathString'" }
    }

    return pathString
}

/**
 * Hack to make [FileSaver] and [FilePicker] modal.
 */
@Composable
private fun ModalDialog(content: suspend CoroutineScope.() -> Unit) {
    DialogWindow(
        onCloseRequest = {},
        state = DialogState(size = DpSize.Zero),
        undecorated = true,
        resizable = false,
        enabled = false,
        focusable = false,
        content = { LaunchedEffect(Unit, content) },
    )
}

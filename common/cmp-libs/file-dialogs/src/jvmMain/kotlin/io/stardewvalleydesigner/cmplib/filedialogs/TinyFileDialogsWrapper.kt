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

import io.stardewvalleydesigner.LoggerUtils.logger
import io.stardewvalleydesigner.kmplib.dispatcher.PlatformDispatcher
import kotlinx.coroutines.withContext
import org.lwjgl.PointerBuffer
import org.lwjgl.system.MemoryStack
import org.lwjgl.util.tinyfd.TinyFileDialogs
import java.io.File
import kotlin.io.path.*


internal object TinyFileDialogsWrapper {

    suspend fun createSaveFileDialog(
        title: String? = null,
        defaultPathAndFile: String? = null,
        extensions: List<String>? = null,
        extensionsDescription: String? = null,
        bytes: ByteArray,
    ): FileSaverResult? = withContext(PlatformDispatcher.IO) {
        catchingNativeCall {
            val path = MemoryStack.stackPush().use { stack ->
                TinyFileDialogs.tinyfd_saveFileDialog(
                    title,
                    defaultPathAndFile,
                    extensions?.map { "*.$it" }?.putOn(stack),
                    extensionsDescription,
                )
            }

            if (path != null) {
                val absolutePath = Path(path)
                    .normalize()
                    .createParentDirectories()
                    .apply { writeBytes(bytes) }
                    .pathString
                FileSaverResult(absolutePath)
            } else {
                null
            }
        }
    }

    suspend fun createOpenFileDialog(
        title: String? = null,
        defaultPathAndFile: String? = null,
        extensions: List<String>? = null,
        extensionsDescription: String? = null,
    ): FilePickerResult? = withContext(PlatformDispatcher.IO) {
        catchingNativeCall {
            val path = MemoryStack.stackPush().use { stack ->
                val multiSelect = false
                TinyFileDialogs.tinyfd_openFileDialog(
                    title,
                    defaultPathAndFile,
                    extensions?.map { "*.$it" }?.putOn(stack),
                    extensionsDescription,
                    multiSelect,
                )?.split('|')?.firstOrNull()
            }

            if (path != null) {
                val file = File(path)
                FilePickerResult(file.readText(), file.absolutePath)
            } else {
                null
            }
        }
    }


    private fun List<String>.putOn(
        stack: MemoryStack,
    ): PointerBuffer = stack.mallocPointer(size).apply {
        forEach { str -> put(stack.UTF8(str)) }
        flip()
    }

    private inline fun <T> catchingNativeCall(b: () -> T): T? = runCatching {
        b()
    }.onFailure { nativeException ->
        logger.error { "`tinyfd` failed: ${nativeException.message}" }
    }.getOrNull()
}

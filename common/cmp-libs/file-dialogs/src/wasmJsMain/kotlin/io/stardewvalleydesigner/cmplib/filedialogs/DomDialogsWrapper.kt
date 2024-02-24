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

import org.w3c.dom.*
import org.w3c.files.*


internal object DomDialogsWrapper {

    fun Document.downloadFileToDisk(
        filename: String?,
        content: ByteArray,
    ) {
        val array = JsArray<JsAny?>().apply { set(0, content.decodeToString().toJsString()) }
        val blob = Blob(array)

        saveAs(blob, filename?.toJsString())
    }

    fun Document.loadFileFromDisk(
        extensionsString: String,
        onLoaded: (String) -> Unit,
    ) {
        val multiSelect = false
        val tempInput = (createElement("input") as HTMLInputElement).apply {
            type = "file"
            style.display = "none"
            accept = extensionsString
            multiple = multiSelect
        }

        tempInput.onchange = { changeEvent ->
            val file = targetFiles(changeEvent.target!!).asList().first()

            FileReader().run {
                onload = { loadEvent ->
                    val content = targetResult(loadEvent.target!!)
                    onLoaded(content)
                }
                readAsText(file, label = "UTF-8")
            }
        }

        body!!.append(tempInput)
        tempInput.click()
        tempInput.remove()
    }
}


@JsModule("file-saver")
private external fun saveAs(blob: Blob, filename: JsString?)


private fun targetFiles(target: JsAny): ItemArrayLike<File> = js("target.files")

private fun targetResult(target: JsAny): String = js("target.result")

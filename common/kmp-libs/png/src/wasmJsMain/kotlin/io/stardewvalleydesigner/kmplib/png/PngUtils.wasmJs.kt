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

package io.stardewvalleydesigner.kmplib.png

import io.stardewvalleydesigner.LoggerUtils.logger
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.await
import org.khronos.webgl.*
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import org.w3c.fetch.Response


actual object PngUtils {

    @OptIn(ExperimentalStdlibApi::class)
    actual suspend fun generatePngBytes(argbBytes: IntArray, width: Int, height: Int): ByteArray {
        val canvas = (document.createElement(localName = "canvas") as HTMLCanvasElement).apply {
            this.width = width
            this.height = height

            val context = getContext(contextId = "2d") as CanvasRenderingContext2D
            for ((i, pixel) in argbBytes.withIndex()) {
                val r = ((pixel and (0xff shl 16)) shr 16).toByte().toHexString()
                val g = ((pixel and (0xff shl 8)) shr 8).toByte().toHexString()
                val b = ((pixel and (0xff shl 0)) shr 0).toByte().toHexString()
                val a = ((pixel and (0xff shl 24)) shr 24).toByte().toHexString()

                context.fillStyle = "#$r$g$b$a".toJsString()
                context.fillRect(x = (i % width).toDouble(), y = (i / width).toDouble(), w = 1.0, h = 1.0)
            }
        }

        val dataUrl = canvas.toDataURL(type = "image/png")

        logger.debug { dataUrl }

        val response = window.fetch(dataUrl).await<Response>()
        val arrayBuffer = response.arrayBuffer().await<ArrayBuffer>()
        val uint8Array = Uint8Array(arrayBuffer)
        val byteArray = ByteArray(size = uint8Array.length, init = uint8Array::get)

        return byteArray
    }
}

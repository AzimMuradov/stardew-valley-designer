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

import java.awt.Point
import java.awt.image.*
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO


actual object PngUtils {

    actual fun generatePngBytes(argbBytes: IntArray, width: Int, height: Int): ByteArray {
        val sampleModel = run {
            val bitMasks = run {
                val a = 0xff shl 24
                val r = 0xff shl 16
                val g = 0xff shl 8
                val b = 0xff shl 0

                intArrayOf(r, g, b, a)
            }

            SinglePixelPackedSampleModel(DataBuffer.TYPE_INT, width, height, bitMasks)
        }

        val raster = run {
            val dataBuffer = DataBufferInt(argbBytes, argbBytes.size)
            val location: Point? = null

            Raster.createWritableRaster(sampleModel, dataBuffer, location)
        }

        val image = run {
            val colorModel = ColorModel.getRGBdefault()
            val isRasterPremultiplied = false
            val properties = null

            BufferedImage(colorModel, raster, isRasterPremultiplied, properties)
        }

        val pngBytes = ByteArrayOutputStream(width * height * 4).use { output ->
            ImageIO.write(image, PNG_FORMAT, output)
            output.toByteArray()
        }

        return pngBytes
    }
}

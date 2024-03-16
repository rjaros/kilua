/*
 * Copyright (c) 2024 Robert Jaros
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.kilua.ssr

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

/**
 * Converted from https://github.com/eugenp/tutorials/blob/master/core-java-modules/core-java-io/src/main/java/com/baeldung/unzip/UnzipFile.java
 */
internal object FileUtils {
    @Throws(IOException::class)
    @JvmStatic
    fun unzip(inputStream: InputStream, destinationDir: File) {
        val buffer = ByteArray(1024)
        val zis = ZipInputStream(inputStream)
        var zipEntry = zis.nextEntry
        while (zipEntry != null) {
            val newFile = newFile(destinationDir, zipEntry)
            if (zipEntry.isDirectory) {
                if (!newFile.isDirectory && !newFile.mkdirs()) {
                    throw IOException("Failed to create directory $newFile")
                }
            } else {
                val parent = newFile.parentFile
                if (!parent.isDirectory && !parent.mkdirs()) {
                    throw IOException("Failed to create directory $parent")
                }

                val fos = FileOutputStream(newFile)
                var len: Int
                while ((zis.read(buffer).also { len = it }) > 0) {
                    fos.write(buffer, 0, len)
                }
                fos.close()
            }
            zipEntry = zis.nextEntry
        }
        zis.closeEntry()
        zis.close()
    }

    @Throws(IOException::class)
    private fun newFile(destinationDir: File, zipEntry: ZipEntry): File {
        val destFile = File(destinationDir, zipEntry.name)

        val destDirPath = destinationDir.canonicalPath
        val destFilePath = destFile.canonicalPath

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw IOException("Entry is outside of the target dir: " + zipEntry.name)
        }

        return destFile
    }
}
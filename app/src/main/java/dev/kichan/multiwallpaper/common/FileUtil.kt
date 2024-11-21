package dev.kichan.multiwallpaper.common

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

object FileUtil {
    fun saveImage(context: Context, imageUri : Uri, fileName : String) : String {
        val inputStream = context.contentResolver.openInputStream(imageUri)

        val file = File(context.filesDir, fileName)
        val outputStream = FileOutputStream(file)

        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()

        return file.absolutePath
    }
}
package dev.abhaycloud.fdtracker.utils

import android.content.ContentResolver
import android.content.ContentValues
import android.net.Uri
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Environment
import android.provider.MediaStore
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FileUtils @Inject constructor(private var contentResolver: ContentResolver) {
    fun saveFileToDownloads(csvData: String, fileName: String): Uri? {
        if (VERSION.SDK_INT >= VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, "$fileName.csv")
                put(MediaStore.MediaColumns.MIME_TYPE, "text/csv")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            }

            val resolver = contentResolver
            val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

            uri?.let {
                val outputStream: OutputStream? = resolver.openOutputStream(it)
                outputStream?.use { stream ->
                    stream.write(csvData.toByteArray())
                }
            }
            return uri
        }
        return null
    }

//    fun readCsvFromUri(uri: Uri): String {
//        val stringBuilder = StringBuilder()
//        contentResolver.openInputStream(uri)?.use { inputStream ->
//            BufferedReader(InputStreamReader(inputStream)).use { reader ->
//                var line: String?
//                while (reader.readLine().also { line = it } != null) {
//                    stringBuilder.append(line).append("\n")
//                }
//            }
//        }
//        return stringBuilder.toString()
//    }
}
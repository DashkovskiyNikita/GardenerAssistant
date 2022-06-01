package com.example.gardenerassistant.data.sources

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import org.koin.core.component.KoinComponent
import java.io.ByteArrayOutputStream
import java.util.*

interface AbstractImageStorage {
    suspend fun selectImage(fileName: String): Bitmap?
    suspend fun saveImage(bitmap: Bitmap): String
}

class ImageStorage : AbstractImageStorage, KoinComponent {

    override suspend fun selectImage(fileName: String): Bitmap? =
        getKoin().get<Context>().loadBitmap(fileName)

    override suspend fun saveImage(bitmap: Bitmap) =
        getKoin().get<Context>().saveBitmap(bitmap)

}

fun Bitmap.toByteArray(): ByteArray {
    val stream = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.PNG, 100, stream)
    return stream.toByteArray()
}

fun Context.loadBitmap(fileName: String): Bitmap? =
    openFileInput(fileName).use { BitmapFactory.decodeStream(it) }

fun Context.saveBitmap(bitmap: Bitmap): String {
    val fileName = "${UUID.randomUUID()}.png"
    openFileOutput(fileName, Context.MODE_PRIVATE).use {
        it.write(bitmap.toByteArray())
    }
    return fileName
}

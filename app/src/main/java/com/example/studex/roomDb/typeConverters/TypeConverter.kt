package com.example.studex.roomDb.typeConverters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class TypeConverter {

    @TypeConverter
    fun getBitmapFromString(byteArray: ByteArray?): Bitmap? {
        return byteArray?.size?.let { BitmapFactory.decodeByteArray(byteArray, 0, it) }
    }

    @TypeConverter
    fun getStringFromBitmap(bitmap: Bitmap?): ByteArray {
        if (bitmap == null) {
            return ByteArray(10)
        }
        val outputStream = ByteArrayOutputStream()
        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, false)
        scaledBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }

}

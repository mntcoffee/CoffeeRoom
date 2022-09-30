package com.example.coffeeroom.data.model.coffee

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream
import java.time.LocalDate
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    @TypeConverter
    fun fromByteArray(bytes: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    @TypeConverter
    fun bitmapToByteArray(bmp: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }

    @TypeConverter
    fun stringToUri(str: String?): Uri? {
//        return Uri.parse(string)
        return str?.let { Uri.parse(it) }
    }

    @TypeConverter
    fun uriToString(uri: Uri?): String? {
        return uri?.toString()
    }

}
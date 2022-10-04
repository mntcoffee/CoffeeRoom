package com.example.coffeeroom.data.model.coffee

import android.net.Uri
import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun stringToUri(str: String?): Uri? {
        return str?.let { Uri.parse(it) }
    }

    @TypeConverter
    fun uriToString(uri: Uri?): String? {
        return uri?.toString()
    }

}

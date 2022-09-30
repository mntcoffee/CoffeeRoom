package com.example.coffeeroom.ui.camera

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.BufferedInputStream
import javax.inject.Inject


@HiltViewModel
class CameraViewModel @Inject constructor(): ViewModel() {

    fun rotateBitmap(bitmap: Bitmap, degrees: Float = 90F): Bitmap? {
        val w = bitmap.width
        val h = bitmap.height
        val m = Matrix()
        m.setRotate(degrees)
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, m, false)
    }

    fun uriToBitmap(uri: Uri, context: Context): Bitmap {
        val inputStream = context.contentResolver.openInputStream(uri)
        return BitmapFactory.decodeStream(BufferedInputStream(inputStream))
    }
}
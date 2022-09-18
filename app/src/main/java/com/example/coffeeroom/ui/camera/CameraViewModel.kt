package com.example.coffeeroom.ui.camera

import android.graphics.Bitmap
import android.graphics.Matrix
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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
}
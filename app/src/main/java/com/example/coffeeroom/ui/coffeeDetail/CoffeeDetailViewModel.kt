package com.example.coffeeroom.ui.coffeeDetail

import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Log
import androidx.lifecycle.*
import com.example.coffeeroom.data.model.coffee.Coffee
import com.example.coffeeroom.data.repository.CoffeeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class CoffeeDetailViewModel
@Inject constructor(
    private val coffeeRepository: CoffeeRepository
): ViewModel() {

    lateinit var coffeeDetail: LiveData<Coffee>

    private var _coffeeImage = MutableLiveData<Bitmap?>()
    val coffeeImage: LiveData<Bitmap?> get() = _coffeeImage

    var isEditMode = false

    fun onStart(id: Long) {
        // 新規追加でない(id!=0)ならデータベースからコーヒー情報を取得
        coffeeDetail = coffeeRepository.getCoffee(id).asLiveData()
    }

    fun update(coffee: Coffee) {
        viewModelScope.launch {
            coffeeRepository.updateCoffee(coffee)
        }
    }

    fun add(coffee: Coffee) {
        viewModelScope.launch {
            coffeeRepository.insertCoffee(coffee)
        }
    }

    fun updateFavorite(isChecked: Boolean) {
        val coffee = coffeeDetail.value?.copy(isFavorite = isChecked)
        if(coffee != null) update(coffee)
    }

    fun setImage(bitmap: Bitmap) {
        Log.d("image", "called!!")
        _coffeeImage.value = bitmap
    }

}


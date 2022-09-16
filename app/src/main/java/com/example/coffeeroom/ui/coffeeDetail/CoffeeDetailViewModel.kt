package com.example.coffeeroom.ui.coffeeDetail

import android.util.Log
import androidx.lifecycle.*
import com.example.coffeeroom.R
import com.example.coffeeroom.data.model.coffee.Coffee
import com.example.coffeeroom.data.repository.CoffeeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoffeeDetailViewModel
@Inject constructor(
    private val coffeeRepository: CoffeeRepository
): ViewModel() {

    private var _coffeeDetail = MutableLiveData<Coffee>()
    val coffeeDetail: LiveData<Coffee> get() = _coffeeDetail

    fun onStart(id: Long) {
        viewModelScope.launch {
            _coffeeDetail.value = coffeeRepository.getCoffee(id)
            Log.d("favorite", "onStart: ${_coffeeDetail.value.toString()}")
        }
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
        Log.d("favorite", "is favorite: $isChecked")
        val coffee = _coffeeDetail.value?.copy(isFavorite = isChecked)
        Log.d("favorite", "update: ${coffee.toString()}")
        update(coffee!!)
    }
}

package com.example.coffeeroom.ui.coffeeList

import android.util.Log
import androidx.lifecycle.*
import com.example.coffeeroom.data.model.coffee.Coffee
import com.example.coffeeroom.data.repository.CoffeeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoffeeListViewModel
@Inject constructor(
    private val coffeeRepository: CoffeeRepository
): ViewModel() {

    val allCoffee: LiveData<List<Coffee>> = coffeeRepository.allCoffee.asLiveData()

    fun add(coffee: Coffee) {
        viewModelScope.launch {
            coffeeRepository.insertCoffee(coffee)
            Log.d("test", "${coffee.toString()}")
        }
    }

    fun update(coffee: Coffee) {
        viewModelScope.launch {
            coffeeRepository.updateCoffee(coffee)
        }
    }

    fun delete(coffee: Coffee) {
        viewModelScope.launch {
            coffeeRepository.deleteCoffee(coffee)
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            coffeeRepository.deleteAll()
        }
    }
}


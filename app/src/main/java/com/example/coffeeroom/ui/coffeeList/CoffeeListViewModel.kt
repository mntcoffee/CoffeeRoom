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

    private val _filteredCoffeeList = MutableLiveData<List<Coffee>>()
    val filteredCoffeeList: LiveData<List<Coffee>> get() = _filteredCoffeeList

    fun add(coffee: Coffee) {
        viewModelScope.launch {
            coffeeRepository.insertCoffee(coffee)
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

    fun filteringCoffeeList(text: String) {
        // 検索開始
        val list = allCoffee.value
        val filteredList = mutableListOf<Coffee>()
        if (list != null) {
            for (item in list) {
                if (item.title?.contains(text, ignoreCase = true)!!
                    || item.country?.contains(text, ignoreCase = true)!!
                    || item.farm?.contains(text, ignoreCase = true)!!
                    || item.process?.contains(text, ignoreCase = true)!!
                    || item.roaster?.contains(text, ignoreCase = true)!!
                    || item.roastingDegree?.contains(text, ignoreCase = true)!!
                ) {
                    filteredList.add(item)
                }
            }
        }
        _filteredCoffeeList.value = filteredList
        Log.d("search", filteredList.toString())
    }
}


package com.example.coffeeroom.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeeroom.data.model.coffee.Coffee
import com.example.coffeeroom.data.repository.CoffeeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
@Inject constructor(
    private val coffeeRepository: CoffeeRepository
): ViewModel() {

    private val _searchedCoffee = MutableLiveData<List<Coffee>>()
    val searchedCoffee: LiveData<List<Coffee>> get() = _searchedCoffee

    // コーヒーの検索
    fun searchCoffee(text: String) {
        if(text.isNullOrBlank()) {
            _searchedCoffee.value = mutableListOf()
        } else {
            viewModelScope.launch {
                _searchedCoffee.value = coffeeRepository.searchCoffee(text)
            }
        }
    }
}

package com.example.coffeeroom.ui.coffeeDetail

import android.net.Uri
import androidx.lifecycle.*
import com.example.coffeeroom.data.model.coffee.Coffee
import com.example.coffeeroom.data.repository.CoffeeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoffeeDetailViewModel
@Inject constructor(
    private val coffeeRepository: CoffeeRepository
): ViewModel() {

    lateinit var coffeeDetail: LiveData<Coffee>

    private var _coffeeImage = MutableLiveData<Uri?>()
    val coffeeImage: LiveData<Uri?> get() = _coffeeImage

    // 編集モード or 追加モード
    var isEditMode = false

    fun onStart(id: Long) {
        // 新規追加でない(id!=0)ならデータベースからコーヒー情報を取得
        coffeeDetail = coffeeRepository.getCoffee(id).asLiveData()
    }

    // データベース更新
    fun update(coffee: Coffee) {
        viewModelScope.launch {
            coffeeRepository.updateCoffee(coffee)
        }
    }

    // データベースに新規追加
    fun add(coffee: Coffee) {
        viewModelScope.launch {
            coffeeRepository.insertCoffee(coffee)
        }
    }

    // お気に入りの更新
    fun updateFavorite(isChecked: Boolean) {
        val coffee = coffeeDetail.value?.copy(isFavorite = isChecked)
        if(coffee != null) update(coffee)
    }

    // 画像を更新
    fun setImage(uri: Uri) {
        _coffeeImage.value = uri
    }
}

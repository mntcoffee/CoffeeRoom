package com.example.coffeeroom.data.repository

import androidx.annotation.WorkerThread
import com.example.coffeeroom.data.model.coffee.Coffee
import com.example.coffeeroom.data.model.coffee.CoffeeDao
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

class CoffeeRepository @Inject constructor(
    private val coffeeDao: CoffeeDao
    ) {

    val allCoffee: Flow<List<Coffee>> = coffeeDao.getAllCoffee()
    val allCoffeeByCountry: Flow<List<Coffee>> = coffeeDao.getAllCoffeeByCountry()
    val allCoffeeByProcess: Flow<List<Coffee>> = coffeeDao.getAllCoffeeByProcess()
    val allCoffeeByCreated: Flow<List<Coffee>> = coffeeDao.getAllCoffeeByCreated()
    val allCoffeeByUpdated: Flow<List<Coffee>> = coffeeDao.getAllCoffeeByUpdated()
    val allCoffeeByRoaster: Flow<List<Coffee>> = coffeeDao.getAllCoffeeByRoaster()
    val allCoffeeByRoastingDegree: Flow<List<Coffee>> = coffeeDao.getAllCoffeeByRoastingDegree()
    val favoriteCoffee: Flow<List<Coffee>> = coffeeDao.getFavoriteCoffee()

    @WorkerThread
    suspend fun searchCoffee(text: String): List<Coffee> {
        return withContext(Dispatchers.IO) {
            coffeeDao.getSearchedCoffee(text)
        }
    }

    @WorkerThread
    suspend fun insertCoffee(coffee: Coffee) {
        withContext(Dispatchers.IO) {
            coffeeDao.insertCoffee(coffee)
        }
    }

    @WorkerThread
    suspend fun updateCoffee(coffee: Coffee) {
        withContext(Dispatchers.IO) {
            coffeeDao.updateCoffee(coffee)
        }
    }

    @WorkerThread
    suspend fun deleteCoffee(coffee: Coffee) {
        withContext(Dispatchers.IO) {
            coffeeDao.deleteCoffee(coffee)
        }
    }

    @WorkerThread
    suspend fun getCoffee(id: Long): Coffee {
        return withContext(Dispatchers.IO) {
            coffeeDao.getCoffee(id)
        }
    }

    @WorkerThread
    suspend fun deleteAll() {
        withContext(Dispatchers.IO) {
            coffeeDao.deleteAll()
        }
    }
}

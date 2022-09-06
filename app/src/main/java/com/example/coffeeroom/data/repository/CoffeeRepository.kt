package com.example.coffeeroom.data.repository

import androidx.annotation.WorkerThread
import com.example.coffeeroom.data.model.coffee.Coffee
import com.example.coffeeroom.data.model.coffee.CoffeeDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

class CoffeeRepository(private val coffeeDao: CoffeeDao) {

    val allCoffee: Flow<List<Coffee>> = coffeeDao.getAllCoffee()
    val allCoffeeByCountry: Flow<List<Coffee>> = coffeeDao.getAllCoffeeByCountry()
    val allCoffeeByProcess: Flow<List<Coffee>> = coffeeDao.getAllCoffeeByProcess()
    val allCoffeeByCreated: Flow<List<Coffee>> = coffeeDao.getAllCoffeeByCreated()
    val allCoffeeByUpdated: Flow<List<Coffee>> = coffeeDao.getAllCoffeeByUpdated()
    val allCoffeeByRoaster: Flow<List<Coffee>> = coffeeDao.getAllCoffeeByRoaster()
    val allCoffeeByRoastingDegree: Flow<List<Coffee>> = coffeeDao.getAllCoffeeByRoastingDegree()
    val favoriteCoffee: Flow<List<Coffee>> = coffeeDao.getFavoriteCoffee()

    @WorkerThread
    suspend fun insertCoffee(coffee: Coffee) {
        coffeeDao.insertCoffee(coffee)
    }

    @WorkerThread
    suspend fun deleteCoffee(coffee: Coffee) {
        coffeeDao.deleteCoffee(coffee)
    }
}

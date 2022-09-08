package com.example.coffeeroom

import android.app.Application
import com.example.coffeeroom.data.model.coffee.CoffeeRoomDatabase
import com.example.coffeeroom.data.repository.CoffeeRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@HiltAndroidApp
class MainApplication : Application() {
//    val applicationScope = CoroutineScope(SupervisorJob())
//    val database by lazy { CoffeeRoomDatabase.getDatabase(this, applicationScope) }
//    val repository by lazy { CoffeeRepository(database.coffeeDao()) }
}
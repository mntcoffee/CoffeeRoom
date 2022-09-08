package com.example.coffeeroom.data.model.coffee

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kotlinx.coroutines.CoroutineScope

@Database(entities = [Coffee::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
public abstract class CoffeeRoomDatabase : RoomDatabase() {
    abstract fun coffeeDao(): CoffeeDao
}

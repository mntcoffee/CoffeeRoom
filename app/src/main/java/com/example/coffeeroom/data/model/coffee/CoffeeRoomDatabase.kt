package com.example.coffeeroom.data.model.coffee

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Coffee::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class CoffeeRoomDatabase : RoomDatabase() {
    abstract fun coffeeDao(): CoffeeDao
}

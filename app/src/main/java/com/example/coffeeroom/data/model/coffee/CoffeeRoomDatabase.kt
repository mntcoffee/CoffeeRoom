package com.example.coffeeroom.data.model.coffee

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Coffee::class], version = 1, exportSchema = false)
public abstract class CoffeeRoomDatabase : RoomDatabase() {

    abstract fun coffeeDao(): CoffeeDao

    // データベースはシングルトン
    companion object {
        // 最初にアクセスされた時にデータベースを作成
        @Volatile
        private var INSTANCE: CoffeeRoomDatabase? = null

        fun getDatabase(context: Context): CoffeeRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CoffeeRoomDatabase::class.java,
                    "coffee_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
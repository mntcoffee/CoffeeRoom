package com.example.coffeeroom.di

import android.content.Context
import androidx.room.Room
import androidx.room.TypeConverters
import com.example.coffeeroom.data.model.coffee.CoffeeRoomDatabase
import com.example.coffeeroom.data.model.coffee.Converters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoffeeModule {

    @Singleton
    @Provides
    @TypeConverters(Converters::class)
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, CoffeeRoomDatabase::class.java, "coffee_table").build()

    @Singleton
    @Provides
    fun provideDao(db: CoffeeRoomDatabase) = db.coffeeDao()
}
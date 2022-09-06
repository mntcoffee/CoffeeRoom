package com.example.coffeeroom.data.model

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "coffee_table")
data class Coffee(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "created_at") val createdAt: LocalDate,
    @ColumnInfo(name = "updated_at") val updatedAt: LocalDate,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean,
    val image: Bitmap,
    val country: String,
    val farm: String,
    val process: String,
    val roaster: String,
    @ColumnInfo(name = "roasting_degree") val roastingDegree: String,
    val comment: String
)

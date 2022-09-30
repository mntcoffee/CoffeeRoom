package com.example.coffeeroom.data.model.coffee

import android.graphics.Bitmap
import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.net.URI
import java.time.LocalDate
import java.util.*

@Entity(tableName = "coffee_table")
data class Coffee(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "created_at") val createdAt: String,
    @ColumnInfo(name = "updated_at") val updatedAt: String,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean,
    val image: Uri?,
    val title: String?,
    val country: String?,
    val farm: String?,
    val process: String?,
    val roaster: String?,
    @ColumnInfo(name = "roasting_degree") val roastingDegree: String?,
    val comment: String?
)


package com.example.coffeeroom.data.model.coffee

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CoffeeDao {
    // 全件取得
    @Query("SELECT * FROM coffee_table")
    fun getAllCoffee(): List<Coffee>

    // 国順で全件取得
    @Query("SELECT * FROM coffee_table ORDER BY country ASC")
    fun getAllCoffeeByCountry(): List<Coffee>

    // プロセス順で全件取得
    @Query("SELECT * FROM coffee_table ORDER BY process ASC")
    fun getAllCoffeeByProcess(): List<Coffee>

    // 作成日順で全件取得
    @Query("SELECT * FROM coffee_table ORDER BY created_at")
    fun getAllCoffeeByCreated(): List<Coffee>

    // 更新日順で全件取得
    @Query("SELECT * FROM coffee_table ORDER BY updated_at")
    fun getAllCoffeeByUpdated(): List<Coffee>

    // ロースター順で全件取得
    @Query("SELECT * FROM coffee_table ORDER BY roaster ASC")
    fun getAllCoffeeByRoaster(): List<Coffee>

    // 焙煎度順で全件取得
    @Query("SELECT * FROM coffee_table ORDER BY roasting_degree ASC")
    fun getAllCoffeeByRoastingDegree(): List<Coffee>

    // お気に入りのコーヒーのみ取得
    @Query("SELECT * FROM coffee_table WHERE is_favorite = 1")

    // データベースに追加
    @Insert
    suspend fun insertCoffee(coffee: Coffee)

    // データベースから1件削除
    @Delete
    suspend fun deleteCoffee(coffee: Coffee)
}

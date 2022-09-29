package com.example.coffeeroom.data.model.coffee

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CoffeeDao {
    // 全件取得
    @Query("SELECT * FROM coffee_table")
    fun getAllCoffee(): Flow<List<Coffee>>

    // 国順で全件取得
    @Query("SELECT * FROM coffee_table ORDER BY country ASC")
    fun getAllCoffeeByCountry(): Flow<List<Coffee>>

    // プロセス順で全件取得
    @Query("SELECT * FROM coffee_table ORDER BY process ASC")
    fun getAllCoffeeByProcess(): Flow<List<Coffee>>

    // 作成日順で全件取得
    @Query("SELECT * FROM coffee_table ORDER BY created_at")
    fun getAllCoffeeByCreated(): Flow<List<Coffee>>

    // 更新日順で全件取得
    @Query("SELECT * FROM coffee_table ORDER BY updated_at")
    fun getAllCoffeeByUpdated(): Flow<List<Coffee>>

    // ロースター順で全件取得
    @Query("SELECT * FROM coffee_table ORDER BY roaster ASC")
    fun getAllCoffeeByRoaster(): Flow<List<Coffee>>

    // 焙煎度順で全件取得
    @Query("SELECT * FROM coffee_table ORDER BY roasting_degree ASC")
    fun getAllCoffeeByRoastingDegree(): Flow<List<Coffee>>

    // お気に入りのコーヒーのみ取得
    @Query("SELECT * FROM coffee_table WHERE is_favorite = 1")
    fun getFavoriteCoffee(): Flow<List<Coffee>>

    // 検索ワードからコーヒーを取得
    @Query("SELECT * FROM coffee_table WHERE title LIKE '%' || :text || '%'")
    suspend fun getSearchedCoffee(text: String): List<Coffee>

    // データベースに追加
    @Insert
    suspend fun insertCoffee(coffee: Coffee)

    // データベースを更新
    @Update
    suspend fun updateCoffee(coffee: Coffee)

    // データベースからIDで1件取得
    @Query("SELECT * FROM coffee_table WHERE id = :id")
    fun getCoffee(id: Long): Flow<Coffee>

    // データベースから1件削除
    @Delete
    suspend fun deleteCoffee(coffee: Coffee)

    // データベースから全て削除
    @Query("DELETE FROM coffee_table")
    suspend fun deleteAll()

}

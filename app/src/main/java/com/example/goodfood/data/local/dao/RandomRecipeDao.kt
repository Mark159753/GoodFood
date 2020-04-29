package com.example.goodfood.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.goodfood.model.recipe.Recipe

@Dao
interface RandomRecipeDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRandomRecipes(list: List<Recipe>)

    @Query("SELECT * FROM recipes")
    fun getAllRandomRecipes():LiveData<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE typeRequest = :type")
    fun getRandomRecipesByRequestType(type:String):LiveData<List<Recipe>>

    @Query("DELETE FROM recipes")
    suspend fun clearAll()

    @Query("DELETE FROM recipes WHERE typeRequest = :type")
    suspend fun clearByRequestType(type: String)
}
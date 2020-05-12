package com.example.goodfood.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.goodfood.data.local.entitys.RecipeEntity

@Dao
interface RandomRecipeDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRandomRecipes(list: List<RecipeEntity>)

    @Query("SELECT * FROM recipes")
    fun getAllRandomRecipes():LiveData<List<RecipeEntity>>

    @Query("SELECT * FROM recipes WHERE typeRequest = :type")
    suspend fun getRandomRecipesByRequestType(type:String):List<RecipeEntity>

    @Query("DELETE FROM recipes")
    suspend fun clearAll()

    @Query("DELETE FROM recipes WHERE typeRequest = :type")
    suspend fun clearByRequestType(type: String)
}
package com.example.goodfood.data.repositorys.home

import androidx.lifecycle.LiveData
import com.example.goodfood.data.local.entitys.RecipeEntity
import com.example.goodfood.untils.LoadState


interface HomeRepository {

    val randomRecipes: LiveData<LoadState<List<RecipeEntity>>>
    suspend fun getRandomRecipes(numbers:Int, forceLoad: Boolean, tags:String? = null)

    val randomVeganRecipes:LiveData<LoadState<List<RecipeEntity>>>
    suspend fun getRandomVeganRecipes(numbers:Int, forceLoad: Boolean)

    val randomDrinkRecipes:LiveData<LoadState<List<RecipeEntity>>>
    suspend fun getRandomDrinkRecipes(numbers:Int, forceLoad: Boolean)

    val randomDessertRecipes:LiveData<LoadState<List<RecipeEntity>>>
    suspend fun getRandomDessertRecipes(numbers:Int, forceLoad: Boolean)

    val randomSaladRecipes:LiveData<LoadState<List<RecipeEntity>>>
    suspend fun getRandomSaladRecipes(numbers:Int, forceLoad: Boolean)

    val randomSoupRecipes:LiveData<LoadState<List<RecipeEntity>>>
    suspend fun getRandomSoupRecipes(numbers:Int, forceLoad: Boolean)

}
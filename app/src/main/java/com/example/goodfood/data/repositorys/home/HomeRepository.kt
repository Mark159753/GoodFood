package com.example.goodfood.data.repositorys.home

import androidx.lifecycle.LiveData
import com.example.goodfood.model.recipe.Recipe
import com.example.goodfood.untils.LoadState


interface HomeRepository {

    val randomRecipes: LiveData<LoadState<List<Recipe>>>
    suspend fun getRandomRecipes(numbers:Int, forceLoad: Boolean, tags:String? = null)

    val randomVeganRecipes:LiveData<LoadState<List<Recipe>>>
    suspend fun getRandomVeganRecipes(numbers:Int, forceLoad: Boolean)

    val randomDrinkRecipes:LiveData<LoadState<List<Recipe>>>
    suspend fun getRandomDrinkRecipes(numbers:Int, forceLoad: Boolean)

    val randomDessertRecipes:LiveData<LoadState<List<Recipe>>>
    suspend fun getRandomDessertRecipes(numbers:Int, forceLoad: Boolean)

    val randomSaladRecipes:LiveData<LoadState<List<Recipe>>>
    suspend fun getRandomSaladRecipes(numbers:Int, forceLoad: Boolean)

    val randomSoupRecipes:LiveData<LoadState<List<Recipe>>>
    suspend fun getRandomSoupRecipes(numbers:Int, forceLoad: Boolean)

}
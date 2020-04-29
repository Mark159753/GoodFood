package com.example.goodfood.data.repositorys.home

import androidx.lifecycle.LiveData
import com.example.goodfood.model.recipe.Recipe
import com.example.goodfood.untils.LoadState
import kotlin.coroutines.CoroutineContext

interface HomeRepository {

    fun getRandomRecipes(numbers:Int,  scope:CoroutineContext, tags:String? = null): LiveData<LoadState<List<Recipe>>>

    fun getRandomVeganRecipes(numbers:Int,  scope:CoroutineContext): LiveData<LoadState<List<Recipe>>>

    fun getRandomDrinkRecipes(numbers:Int,  scope:CoroutineContext): LiveData<LoadState<List<Recipe>>>

    fun getRandomDessertRecipes(numbers:Int,  scope:CoroutineContext): LiveData<LoadState<List<Recipe>>>

    fun getRandomSaladRecipes(numbers:Int,  scope:CoroutineContext): LiveData<LoadState<List<Recipe>>>

    fun getRandomSoupRecipes(numbers:Int,  scope:CoroutineContext): LiveData<LoadState<List<Recipe>>>

}
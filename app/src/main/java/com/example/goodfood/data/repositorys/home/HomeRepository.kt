package com.example.goodfood.data.repositorys.home

import androidx.lifecycle.LiveData
import com.example.goodfood.model.recipe.Recipe
import com.example.goodfood.untils.LoadState
import kotlin.coroutines.CoroutineContext

interface HomeRepository {

    fun getRandomRecipes(numbers:Int,  scope:CoroutineContext, tags:String? = null): LiveData<LoadState<List<Recipe>>>
}
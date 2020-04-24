package com.example.goodfood.data.repositorys.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.goodfood.data.local.dao.RandomRecipeDao
import com.example.goodfood.data.network.FoodServer
import com.example.goodfood.model.recipe.Recipe
import com.example.goodfood.untils.LoadState
import com.example.goodfood.untils.TimeRequestHelper
import com.example.goodfood.untils.saveRequest
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class HomeRepositoryImpl @Inject constructor(
    private val foodServer: FoodServer,
    private val randomRecipeDao: RandomRecipeDao,
    private val context: Context
):HomeRepository {

    companion object{
        private const val RANDOM_RECIPE_REQUEST = "com.example.goodfood.data.repositorys.home.RANDOM_RECIPE_REQUEST"
    }

    override fun getRandomRecipes(numbers: Int, scope:CoroutineContext, tags: String?) = liveData<LoadState<List<Recipe>>>(scope) {
        if (TimeRequestHelper.isUpdateNeeded(RANDOM_RECIPE_REQUEST, 1, context)){
            Log.d("UPDATE NEEDED", "GO")
            emit(LoadState.LOADING)
            val res = saveRequest { foodServer.getRandom(numbers, tags) }
            if (res.data != null){
                randomRecipeDao.clearAll()
                randomRecipeDao.insertRandomRecipes(res.data.recipes)
                emitSource(randomRecipeDao.getAllRandomRecipes().map { LoadState.LOADED<List<Recipe>>(it) })
                TimeRequestHelper.saveCurrentTime(RANDOM_RECIPE_REQUEST, context)
            }else{
                emit(LoadState.ERROR(res.errorMsg!!))
            }
        }else{
            Log.d("UPDATE IS NOT NEEDED", "STOP")
            emit(LoadState.LOADING)
            emitSource(randomRecipeDao.getAllRandomRecipes().map { LoadState.LOADED<List<Recipe>>(it) })
        }
    }
}
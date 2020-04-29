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
        private const val RANDOM_VEGAN_RECIPE_REQUEST = "com.example.goodfood.data.repositorys.home.RANDOM_VEGAN_RECIPE_REQUEST"
        private const val RANDOM_DRINK_RECIPE_REQUEST = "com.example.goodfood.data.repositorys.home.RANDOM_DRINK_RECIPE_REQUEST"
        private const val RANDOM_DESSERT_RECIPE_REQUEST = "com.example.goodfood.data.repositorys.home.RANDOM_DESSERT_RECIPE_REQUEST"
        private const val RANDOM_SALAD_RECIPE_REQUEST = "com.example.goodfood.data.repositorys.home.RANDOM_SALAD_RECIPE_REQUEST"
        private const val RANDOM_SOUP_RECIPE_REQUEST = "com.example.goodfood.data.repositorys.home.RANDOM_SOUP_RECIPE_REQUEST"

        private const val RANDOM_ALL = "random_all"
        private const val RANDOM_VEGAN = "random_vegan"
        private const val RANDOM_DRINK = "random_drink"
        private const val RANDOM_DESSERT = "random_dessert"
        private const val RANDOM_SALAD = "random_salad"
        private const val RANDOM_SOUP = "random_soup"
    }

    override fun getRandomRecipes(numbers: Int, scope:CoroutineContext, tags: String?)
            = fetchData(numbers, null, RANDOM_RECIPE_REQUEST, RANDOM_ALL, scope)

    override fun getRandomVeganRecipes(
        numbers: Int,
        scope: CoroutineContext
    ) = fetchData(numbers, "vegan", RANDOM_VEGAN_RECIPE_REQUEST, RANDOM_VEGAN, scope)

    override fun getRandomDrinkRecipes(
        numbers: Int,
        scope: CoroutineContext
    ) = fetchData(numbers, "drink", RANDOM_DRINK_RECIPE_REQUEST, RANDOM_DRINK, scope)

    override fun getRandomDessertRecipes(
        numbers: Int,
        scope: CoroutineContext
    ) = fetchData(numbers, "dessert", RANDOM_DESSERT_RECIPE_REQUEST, RANDOM_DESSERT, scope)

    override fun getRandomSaladRecipes(
        numbers: Int,
        scope: CoroutineContext
    ) = fetchData(numbers, "salad", RANDOM_SALAD_RECIPE_REQUEST, RANDOM_SALAD, scope)

    override fun getRandomSoupRecipes(
        numbers: Int,
        scope: CoroutineContext
    ) = fetchData(numbers, "soup", RANDOM_SOUP_RECIPE_REQUEST, RANDOM_SOUP, scope)



    private fun fetchData(numbers: Int, tags:String?, timeKey:String, dataType:String, scope: CoroutineContext) = liveData<LoadState<List<Recipe>>>(scope) {
        if (TimeRequestHelper.isUpdateNeeded(timeKey, 1, context)){
            Log.d("UPDATE NEEDED", "GO")
            emit(LoadState.LOADING)
            val res = saveRequest { foodServer.getRandom(numbers, tags) }
            if (res.data != null){
                res.data.recipes.forEach { it.typeRequest = dataType }
                randomRecipeDao.clearByRequestType(dataType)
                randomRecipeDao.insertRandomRecipes(res.data.recipes)
                emitSource(randomRecipeDao.getRandomRecipesByRequestType(dataType).map { LoadState.LOADED<List<Recipe>>(it) })
                TimeRequestHelper.saveCurrentTime(timeKey, context)
            }else{
                emit(LoadState.ERROR(res.errorMsg!!))
            }
        }else{
            Log.d("UPDATE IS NOT NEEDED", "STOP")
            emit(LoadState.LOADING)
            emitSource(randomRecipeDao.getRandomRecipesByRequestType(dataType).map { LoadState.LOADED<List<Recipe>>(it) })
        }
    }
}
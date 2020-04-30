package com.example.goodfood.data.repositorys.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val _randomRecipes = MutableLiveData<LoadState<List<Recipe>>>()
    override val randomRecipes:LiveData<LoadState<List<Recipe>>>
        get() = _randomRecipes

    override suspend fun getRandomRecipes(numbers: Int, forceLoad: Boolean, tags: String?){
        fetchData(numbers, null, RANDOM_RECIPE_REQUEST, RANDOM_ALL, forceLoad, _randomRecipes)
    }

    private val _randomVeganRecipes = MutableLiveData<LoadState<List<Recipe>>>()
    override val randomVeganRecipes:LiveData<LoadState<List<Recipe>>>
        get() = _randomVeganRecipes

    override suspend fun getRandomVeganRecipes(
        numbers: Int,
        forceLoad: Boolean
    ){
        fetchData(numbers, "vegan", RANDOM_VEGAN_RECIPE_REQUEST, RANDOM_VEGAN, forceLoad, _randomVeganRecipes)
    }

    private val _randomDrinkRecipes = MutableLiveData<LoadState<List<Recipe>>>()
    override val randomDrinkRecipes:LiveData<LoadState<List<Recipe>>>
        get() = _randomDrinkRecipes

    override suspend fun getRandomDrinkRecipes(
        numbers: Int,
        forceLoad: Boolean
    ) {
        fetchData(numbers, "drink", RANDOM_DRINK_RECIPE_REQUEST, RANDOM_DRINK, forceLoad, _randomDrinkRecipes)
    }

    private val _randomDessertRecipes = MutableLiveData<LoadState<List<Recipe>>>()
    override val randomDessertRecipes:LiveData<LoadState<List<Recipe>>>
        get() = _randomDessertRecipes

    override suspend fun getRandomDessertRecipes(
        numbers: Int,
        forceLoad: Boolean
    ) {
        fetchData(numbers, "dessert", RANDOM_DESSERT_RECIPE_REQUEST, RANDOM_DESSERT, forceLoad, _randomDessertRecipes)
    }

    private val _randomSaladRecipes = MutableLiveData<LoadState<List<Recipe>>>()
    override val randomSaladRecipes:LiveData<LoadState<List<Recipe>>>
        get() = _randomSaladRecipes

    override suspend fun getRandomSaladRecipes(
        numbers: Int,
        forceLoad: Boolean
    ) {
        fetchData(numbers, "salad", RANDOM_SALAD_RECIPE_REQUEST, RANDOM_SALAD, forceLoad, _randomSaladRecipes)
    }

    private val _randomSoupRecipes = MutableLiveData<LoadState<List<Recipe>>>()
    override val randomSoupRecipes:LiveData<LoadState<List<Recipe>>>
        get() = _randomSoupRecipes

    override suspend fun getRandomSoupRecipes(
        numbers: Int,
        forceLoad: Boolean
    ) {
        fetchData(numbers, "soup", RANDOM_SOUP_RECIPE_REQUEST, RANDOM_SOUP, forceLoad, _randomSoupRecipes)
    }



    private suspend fun fetchData(numbers: Int, tags:String?, timeKey:String, dataType:String, forceLoad:Boolean, liveData: MutableLiveData<LoadState<List<Recipe>>>) {
        if (TimeRequestHelper.isUpdateNeeded(timeKey, 1, context) || forceLoad){
            Log.d("UPDATE NEEDED", "GO")
            liveData.postValue(LoadState.LOADING)
            val res = saveRequest { foodServer.getRandom(numbers, tags) }
            if (res.data != null){
                res.data.recipes.forEach { it.typeRequest = dataType }
                randomRecipeDao.clearByRequestType(dataType)
                randomRecipeDao.insertRandomRecipes(res.data.recipes)
                liveData.postValue(LoadState.LOADED(randomRecipeDao.getRandomRecipesByRequestType(dataType)))
                TimeRequestHelper.saveCurrentTime(timeKey, context)
            }else{
                liveData.postValue(LoadState.ERROR(res.errorMsg!!))
            }
        }else{
            Log.d("UPDATE IS NOT NEEDED", "STOP")
            liveData.postValue(LoadState.LOADING)
            liveData.postValue(LoadState.LOADED(randomRecipeDao.getRandomRecipesByRequestType(dataType)))
        }
    }
}
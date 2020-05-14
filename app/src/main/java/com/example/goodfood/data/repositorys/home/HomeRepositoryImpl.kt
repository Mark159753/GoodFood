package com.example.goodfood.data.repositorys.home

import android.content.Context
import android.util.Log
import com.example.goodfood.data.local.dao.RandomRecipeDao
import com.example.goodfood.data.local.entitys.RecipeEntity
import com.example.goodfood.data.network.FoodServer
import com.example.goodfood.data.repositorys.NetworkBoundResource
import com.example.goodfood.model.RandomResponse
import com.example.goodfood.untils.Resource
import com.example.goodfood.untils.TimeRequestHelper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val foodServer: FoodServer,
    private val randomRecipeDao: RandomRecipeDao,
    private val context: Context
):HomeRepository {

    override fun getRandomRecipes(
        numbers: Int,
        forceLoad: Boolean,
        tags: String?
    ): Flow<Resource<List<RecipeEntity>>> {
        val dataType = tags ?: "random_all"
        val timeKey = "${javaClass.`package`?.name}.${dataType}"
        return object : NetworkBoundResource<RandomResponse, List<RecipeEntity>>(
            cacheCall = {randomRecipeDao.getRandomRecipesByRequestType(dataType)},
            apiCall = {foodServer.getRandom(numbers, tags)}
        ){
            override suspend fun saveCallResult(item: RandomResponse?) {
                if (item != null){
                    randomRecipeDao.clearByRequestType(dataType)
                    val recipes = ArrayList<RecipeEntity>()
                    for (i in item.recipes) {
                        val recipe = i.toRecipeEntity()
                        recipe.typeRequest = dataType
                        recipes.add(recipe)
                    }
                    randomRecipeDao.insertRandomRecipes(recipes)
                    TimeRequestHelper.saveCurrentTime(timeKey, context)
                }
            }

            override suspend fun shouldFetch(data: List<RecipeEntity>?): Boolean {
                return data == null || data.isEmpty() || TimeRequestHelper.isUpdateNeeded(timeKey, 1, context) || forceLoad
            }
        }.result
    }
}
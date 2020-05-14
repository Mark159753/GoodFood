package com.example.goodfood.data.repositorys.home

import com.example.goodfood.data.local.entitys.RecipeEntity
import com.example.goodfood.untils.Resource
import kotlinx.coroutines.flow.Flow


interface HomeRepository {

    fun getRandomRecipes(numbers:Int, forceLoad: Boolean, tags:String? = null): Flow<Resource<List<RecipeEntity>>>

}
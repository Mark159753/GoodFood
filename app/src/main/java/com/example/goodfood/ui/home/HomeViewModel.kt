package com.example.goodfood.ui.home

import android.util.Log
import androidx.lifecycle.*
import com.example.goodfood.data.local.entitys.RecipeEntity
import com.example.goodfood.data.repositorys.home.HomeRepository
import com.example.goodfood.untils.Resource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val repository:HomeRepository
):ViewModel() {

    private var recipeJob:Job? = null

    private val _randomRecipes = MutableLiveData<Resource<List<RecipeEntity>>>()
    val randomRecipes:LiveData<Resource<List<RecipeEntity>>>
        get() = _randomRecipes

    private val _randomVeganRecipes = MutableLiveData<Resource<List<RecipeEntity>>>()
    val randomVeganRecipes:LiveData<Resource<List<RecipeEntity>>>
        get() = _randomVeganRecipes

    private val _randomDrinkRecipes = MutableLiveData<Resource<List<RecipeEntity>>>()
    val randomDrinkRecipes:LiveData<Resource<List<RecipeEntity>>>
        get() = _randomDrinkRecipes

    private val _randomDessertRecipes = MutableLiveData<Resource<List<RecipeEntity>>>()
    val randomDessertRecipes:LiveData<Resource<List<RecipeEntity>>>
        get() = _randomDessertRecipes

    private val _randomSaladRecipes = MutableLiveData<Resource<List<RecipeEntity>>>()
    val randomSaladRecipes:LiveData<Resource<List<RecipeEntity>>>
        get() = _randomSaladRecipes

    private val _randomSoupRecipes = MutableLiveData<Resource<List<RecipeEntity>>>()
    val randomSoupRecipes:LiveData<Resource<List<RecipeEntity>>>
        get() = _randomSoupRecipes

    init {
        refreshRecipes(false)
    }

    fun refreshRecipes(forceLoad:Boolean){
        recipeJob?.cancel()
        recipeJob = SupervisorJob()

        val all = repository.getRandomRecipes(6, forceLoad)
        val vegan = repository.getRandomRecipes(12, forceLoad, "vegan")
        val drink = repository.getRandomRecipes(12, forceLoad, "drink")
        val dessert = repository.getRandomRecipes(12, forceLoad, "dessert")
        val salad = repository.getRandomRecipes(12, forceLoad, "salad")
        val soup = repository.getRandomRecipes(12, forceLoad, "soup")

        viewModelScope.launch(recipeJob!!) {
            launch {
                all.collect {
                    _randomRecipes.postValue(it)
                }
            }
            launch {
                vegan.collect {
                    _randomVeganRecipes.postValue(it)
                }
            }
            launch {
                drink.collect {
                    _randomDrinkRecipes.postValue(it)
                }
            }
            launch {
                dessert.collect {
                    _randomDessertRecipes.postValue(it)
                }
            }
            launch {
                salad.collect {
                    _randomSaladRecipes.postValue(it)
                }
            }
            launch {
                soup.collect {
                    _randomSoupRecipes.postValue(it)
                }
            }
        }
    }

}
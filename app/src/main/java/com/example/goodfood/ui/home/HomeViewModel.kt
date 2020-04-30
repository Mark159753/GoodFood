package com.example.goodfood.ui.home

import androidx.lifecycle.*
import com.example.goodfood.data.repositorys.home.HomeRepository
import com.example.goodfood.model.recipe.Recipe
import com.example.goodfood.untils.LoadState
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
):ViewModel() {

    val randomRecipes:LiveData<LoadState<List<Recipe>>> =
        homeRepository.randomRecipes

    val randomVeganRecipes = homeRepository.randomVeganRecipes

    val randomDrinkRecipes = homeRepository.randomDrinkRecipes

    val randomDessertRecipes = homeRepository.randomDessertRecipes

    val randomSaladRecipes = homeRepository.randomSaladRecipes

    val randomSoupRecipes = homeRepository.randomSoupRecipes


    fun initRandomRecipe(forceLoad:Boolean){
        viewModelScope.launch {
            homeRepository.getRandomRecipes(5, forceLoad)
        }
    }

    fun initRandomVeganRecipe(forceLoad:Boolean){
        viewModelScope.launch {
            homeRepository.getRandomVeganRecipes(10, forceLoad)
        }
    }

    fun initRandomDrinkRecipe(forceLoad:Boolean){
        viewModelScope.launch {
            homeRepository.getRandomDrinkRecipes(10, forceLoad)
        }
    }

    fun initRandomDessertRecipe(forceLoad:Boolean){
        viewModelScope.launch {
            homeRepository.getRandomDessertRecipes(10, forceLoad)
        }
    }

    fun initRandomSaladRecipe(forceLoad:Boolean){
        viewModelScope.launch {
            homeRepository.getRandomSaladRecipes(10, forceLoad)
        }
    }

    fun initRandomSoupRecipe(forceLoad:Boolean){
        viewModelScope.launch {
            homeRepository.getRandomSoupRecipes(10, forceLoad)
        }
    }

    fun refreshRecipes(){
        initRandomRecipe(true)
        initRandomVeganRecipe(true)
        initRandomDrinkRecipe(true)
        initRandomDessertRecipe(true)
        initRandomSaladRecipe(true)
        initRandomSoupRecipe(true)
    }
}
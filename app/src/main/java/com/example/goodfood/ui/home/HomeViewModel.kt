package com.example.goodfood.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goodfood.data.repositorys.home.HomeRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
):ViewModel() {

    val randomRecipesDataListener = homeRepository
        .getRandomRecipes(5, viewModelScope.coroutineContext + Dispatchers.IO)

    val randomVeganRecipes = homeRepository
        .getRandomVeganRecipes(10, viewModelScope.coroutineContext + Dispatchers.IO)

    val randomDrinkRecipes = homeRepository
        .getRandomDrinkRecipes(10, viewModelScope.coroutineContext + Dispatchers.IO)

    val randomDessertRecipes = homeRepository
        .getRandomDessertRecipes(10, viewModelScope.coroutineContext + Dispatchers.IO)

    val randomSaladRecipes = homeRepository
        .getRandomSaladRecipes(10, viewModelScope.coroutineContext + Dispatchers.IO)

    val randomSoupRecipes = homeRepository
        .getRandomSoupRecipes(10, viewModelScope.coroutineContext + Dispatchers.IO)



}
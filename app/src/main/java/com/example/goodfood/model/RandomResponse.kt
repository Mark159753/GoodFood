package com.example.goodfood.model

import com.example.goodfood.model.recipe.Recipe


data class RandomResponse(
    val recipes: List<Recipe>
)
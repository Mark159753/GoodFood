package com.example.goodfood.model

import com.example.goodfood.model.recipe.RecipeResponse


data class RandomResponse(
    val recipes: List<RecipeResponse>
)
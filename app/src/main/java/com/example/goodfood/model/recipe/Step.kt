package com.example.goodfood.model.recipe


import com.example.goodfood.model.recipe.Equipment
import com.example.goodfood.model.recipe.Ingredient
import com.example.goodfood.model.recipe.Length

data class Step(
    val equipment: List<Equipment>,
    val ingredients: List<Ingredient>,
    val length: Length,
    val number: Int,
    val step: String
)
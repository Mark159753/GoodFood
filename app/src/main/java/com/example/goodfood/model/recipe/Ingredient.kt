package com.example.goodfood.model.recipe


import com.google.gson.annotations.SerializedName

data class Ingredient(
    val id: Int,
    val image: String,
    val name: String
)
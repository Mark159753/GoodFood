package com.example.goodfood.model.search


import com.google.gson.annotations.SerializedName

data class Result(
    val id: Int,
    val image: String,
    val openLicense: Int,
    val readyInMinutes: Int,
    val servings: Int,
    val sourceUrl: String,
    val title: String
)
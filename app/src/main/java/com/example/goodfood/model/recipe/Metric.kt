package com.example.goodfood.model.recipe


import com.google.gson.annotations.SerializedName

data class Metric(
    val amount: Double,
    val unitLong: String,
    val unitShort: String
)
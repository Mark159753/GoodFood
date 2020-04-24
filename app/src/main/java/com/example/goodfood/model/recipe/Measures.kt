package com.example.goodfood.model.recipe

import androidx.room.Embedded


data class Measures(
    val metric: Metric,
    val us: Us
)
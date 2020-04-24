package com.example.goodfood.model.recipe


data class AnalyzedInstruction(
    val name: String,
    val steps: List<Step>
)
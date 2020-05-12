package com.example.goodfood.model.search


import com.google.gson.annotations.SerializedName

data class SearchResponse(
    val baseUri: String,
    val expires: Long,
    val number: Int,
    val offset: Int,
    val processingTimeMs: Int,
    val results: List<Result>,
    val totalResults: Int
)
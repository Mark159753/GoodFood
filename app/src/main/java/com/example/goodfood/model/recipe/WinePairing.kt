package com.example.goodfood.model.recipe


import com.google.gson.annotations.SerializedName

data class WinePairing(
    val pairedWines: List<String>?,
    val pairingText: String?
)
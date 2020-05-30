package com.example.goodfood.model.recipe


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ingredient(
    val id: Int = 0,
    val image: String = "",
    val name: String = ""
):Parcelable
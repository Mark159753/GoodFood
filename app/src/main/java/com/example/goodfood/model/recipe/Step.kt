package com.example.goodfood.model.recipe

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Step(
    val equipment: List<Equipment> = emptyList(),
    val ingredients: List<Ingredient> = emptyList(),
    val length: Length? = null,
    val number: Int? = null,
    val step: String? = null
):Parcelable
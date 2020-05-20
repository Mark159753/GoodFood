package com.example.goodfood.model.recipe

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Step(
    val equipment: List<Equipment>,
    val ingredients: List<Ingredient>,
    val length: Length?,
    val number: Int?,
    val step: String?
):Parcelable
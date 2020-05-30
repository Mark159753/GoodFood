package com.example.goodfood.model.recipe

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ExtendedIngredient(
    val aisle: String? = null,
    val amount: Double = 0.0,
    val consistency: String? = null,
    val id: Int = 0,
    val image: String? = null,
    val measures: Measures? = null,
    val meta: List<String> = emptyList(),
    val metaInformation: List<String> = emptyList(),
    val name: String? = null,
    val original: String? = null,
    val originalName: String? = null,
    val originalString: String? = null,
    val unit: String? = null
):Parcelable
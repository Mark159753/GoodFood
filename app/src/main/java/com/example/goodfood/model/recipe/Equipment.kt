package com.example.goodfood.model.recipe

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Equipment(
    val id: Int? = 0,
    val image: String? = null,
    val name: String? = null,
    val temperature: Temperature? = null
):Parcelable
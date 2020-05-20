package com.example.goodfood.model.recipe

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Temperature(
    val number: Double,
    val unit: String
):Parcelable
package com.example.goodfood.model.recipe

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Us(
    val amount: Double,
    val unitLong: String,
    val unitShort: String
):Parcelable
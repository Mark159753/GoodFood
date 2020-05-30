package com.example.goodfood.model.recipe

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Us(
    val amount: Double = 0.0,
    val unitLong: String = "",
    val unitShort: String = ""
):Parcelable
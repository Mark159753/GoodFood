package com.example.goodfood.model.recipe

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Measures(
    val metric: Metric = Metric(),
    val us: Us = Us()
):Parcelable
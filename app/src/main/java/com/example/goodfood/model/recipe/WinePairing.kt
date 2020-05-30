package com.example.goodfood.model.recipe

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class WinePairing(
    val pairedWines: List<String>? = null,
    val pairingText: String? = null
):Parcelable
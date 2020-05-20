package com.example.goodfood.model.recipe

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AnalyzedInstruction(
    val name: String,
    val steps: List<Step>
):Parcelable
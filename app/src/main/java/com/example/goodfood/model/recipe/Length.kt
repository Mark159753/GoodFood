package com.example.goodfood.model.recipe


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Length(
    val number: Int = 0,
    val unit: String = ""
):Parcelable
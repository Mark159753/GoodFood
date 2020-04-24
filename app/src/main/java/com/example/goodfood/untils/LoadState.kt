package com.example.goodfood.untils

import com.example.goodfood.untils.LoadState as LoadState1

sealed class LoadState<out T> {
    data class LOADED<T>(val data:T): LoadState1<T>()
    object LOADING: LoadState1<Nothing>()
    data class ERROR(val msg:String): LoadState1<Nothing>()
}
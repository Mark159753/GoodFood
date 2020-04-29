package com.example.goodfood.untils

sealed class NetworkState {
    object LOADING:NetworkState()
    object  LOADED:NetworkState()
    data class ERROR(val msg:String):NetworkState()
}
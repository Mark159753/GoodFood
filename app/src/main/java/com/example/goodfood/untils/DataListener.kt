package com.example.goodfood.untils

import androidx.lifecycle.LiveData

data class DataListener<out T>(
    val data:T?,
    val errorMsg:String?
)
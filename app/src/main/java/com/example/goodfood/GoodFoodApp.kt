package com.example.goodfood

import android.app.Application
import com.example.goodfood.di.components.DaggerApplicationComponent

class GoodFoodApp :Application(){

    val appComponent = DaggerApplicationComponent.factory().create(this)
}
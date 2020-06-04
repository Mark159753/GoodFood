package com.example.goodfood.untils

import android.view.View
import com.example.goodfood.data.local.entitys.RecipeEntity

interface ItemSelectedListener {

    fun onItemSelected(data:RecipeEntity,view:View? = null)
}
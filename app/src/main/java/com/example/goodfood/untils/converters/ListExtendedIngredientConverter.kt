package com.example.goodfood.untils.converters

import androidx.room.TypeConverter
import com.example.goodfood.model.recipe.ExtendedIngredient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ListExtendedIngredientConverter {

    @TypeConverter
    fun fromList(list:List<ExtendedIngredient>?):String?{
        val gson = Gson()
        return if (list != null) gson.toJson(list) else null
    }

    @TypeConverter
    fun toList(list:String?):List<ExtendedIngredient>?{
        val listType: Type = object : TypeToken<ArrayList<ExtendedIngredient?>?>() {}.type
        return if (list != null) Gson().fromJson(list, listType) else null
    }
}
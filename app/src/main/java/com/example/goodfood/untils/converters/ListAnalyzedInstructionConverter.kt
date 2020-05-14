package com.example.goodfood.untils.converters

import androidx.room.TypeConverter
import com.example.goodfood.model.recipe.AnalyzedInstruction
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ListAnalyzedInstructionConverter {
    @TypeConverter
    fun fromList(list:List<AnalyzedInstruction>?):String?{
        val gson = Gson()
        return if (list != null) gson.toJson(list) else null
    }

    @TypeConverter
    fun toList(list:String?):List<AnalyzedInstruction>?{
        val listType: Type = object : TypeToken<ArrayList<AnalyzedInstruction?>?>() {}.type
        return if (list != null) Gson().fromJson(list, listType) else null
    }
}
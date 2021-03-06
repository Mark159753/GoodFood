package com.example.goodfood.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.goodfood.data.local.dao.RandomRecipeDao
import com.example.goodfood.data.local.entitys.RecipeEntity
import com.example.goodfood.untils.converters.ListAnalyzedInstructionConverter
import com.example.goodfood.untils.converters.ListExtendedIngredientConverter
import com.example.goodfood.untils.converters.ListStringConverter

@Database(entities = [
    RecipeEntity::class
], version = 9, exportSchema = false)
@TypeConverters(
    ListStringConverter::class,
    ListAnalyzedInstructionConverter::class,
    ListExtendedIngredientConverter::class
)
abstract class RecipeDb:RoomDatabase(){

    abstract fun getRandomRecipesDao():RandomRecipeDao

    companion object{
        @Volatile
        private var instance:RecipeDb? = null

        operator fun invoke(context: Context) = instance ?: synchronized(this){
            instance ?: buildDataBase(context).also { instance = it }
        }

        private fun buildDataBase(context: Context) = Room.databaseBuilder(
            context, RecipeDb::class.java, "RecipeDB"
        ).fallbackToDestructiveMigration()
            .fallbackToDestructiveMigration()
            .build()
    }
}
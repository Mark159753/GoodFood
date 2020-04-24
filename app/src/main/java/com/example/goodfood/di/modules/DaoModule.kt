package com.example.goodfood.di.modules

import com.example.goodfood.data.local.RecipeDb
import com.example.goodfood.data.local.dao.RandomRecipeDao
import com.example.goodfood.di.components.FragmentScope
import dagger.Module
import dagger.Provides

@Module
object DaoModule {

    @JvmStatic
    @FragmentScope
    @Provides
    fun provideRandomRecipeDao(recipeDb: RecipeDb):RandomRecipeDao{
        return recipeDb.getRandomRecipesDao()
    }
}
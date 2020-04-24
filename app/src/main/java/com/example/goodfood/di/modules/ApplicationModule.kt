package com.example.goodfood.di.modules

import android.content.Context
import com.example.goodfood.data.local.RecipeDb
import com.example.goodfood.data.network.FoodServer
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object ApplicationModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideRecipeDb(context: Context):RecipeDb{
        return RecipeDb.invoke(context)
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideFoodServer():FoodServer{
        return FoodServer.invoke()
    }
}
package com.example.goodfood.di.modules

import android.content.Context
import com.example.goodfood.data.local.RecipeDb
import com.example.goodfood.data.network.FoodServer
import com.example.goodfood.untils.FirestoreHelper
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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

    @JvmStatic
    @Singleton
    @Provides
    fun provideFirestoreHalper():FirestoreHelper{
        return FirestoreHelper(Firebase.auth)
    }
}
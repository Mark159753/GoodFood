package com.example.goodfood.di.modules

import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.example.goodfood.di.ViewModelFactoryDI
import com.example.goodfood.di.components.FragmentScope
import com.example.goodfood.fragments.MainFragmentFactory
import com.example.goodfood.untils.FirestoreHelper
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
object FragmentFactoryModule{

    @JvmStatic
    @FragmentScope
    @Provides
    fun provideMainFragmentFactory(factory:  ViewModelProvider.Factory, firestoreHelper: FirestoreHelper):FragmentFactory{
        return MainFragmentFactory(factory as ViewModelFactoryDI, firestoreHelper)
    }

}
package com.example.goodfood.di.components

import com.example.goodfood.di.modules.DaoModule
import com.example.goodfood.di.modules.FragmentFactoryModule
import com.example.goodfood.di.modules.RepositoryModule
import com.example.goodfood.di.modules.ViewModelsModule
import com.example.goodfood.ui.MainActivity
import com.example.goodfood.ui.home.HomeFragment
import com.example.goodfood.ui.search.SearchFragment
import dagger.Subcomponent
import javax.inject.Scope

@FragmentScope
@Subcomponent(modules = [
    ViewModelsModule::class,
    RepositoryModule::class,
    DaoModule::class,
    FragmentFactoryModule::class
])
interface FragmentComponent {

    fun inject(main:MainActivity)

    @Subcomponent.Factory
    interface Factory{
        fun create():FragmentComponent
    }
}

@Scope
@MustBeDocumented
@Retention(value = AnnotationRetention.RUNTIME)
annotation class FragmentScope
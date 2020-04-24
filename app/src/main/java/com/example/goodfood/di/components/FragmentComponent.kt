package com.example.goodfood.di.components

import com.example.goodfood.di.modules.DaoModule
import com.example.goodfood.di.modules.RepositoryModule
import com.example.goodfood.di.modules.ViewModelsModule
import com.example.goodfood.ui.home.HomeFragment
import dagger.Subcomponent
import javax.inject.Scope

@FragmentScope
@Subcomponent(modules = [
    ViewModelsModule::class,
    RepositoryModule::class,
    DaoModule::class
])
interface FragmentComponent {

    fun inject(main:HomeFragment)

    @Subcomponent.Factory
    interface Factory{
        fun create():FragmentComponent
    }
}

@Scope
@MustBeDocumented
@Retention(value = AnnotationRetention.RUNTIME)
annotation class FragmentScope
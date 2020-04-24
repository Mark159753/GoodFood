package com.example.goodfood.di.components

import android.content.Context
import com.example.goodfood.di.modules.ApplicationModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationModule::class
])
interface ApplicationComponent {

    fun getFragmentComponent():FragmentComponent.Factory

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance applicationContext: Context):ApplicationComponent
    }
}
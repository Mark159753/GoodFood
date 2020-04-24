package com.example.goodfood.di.modules

import com.example.goodfood.data.repositorys.home.HomeRepository
import com.example.goodfood.data.repositorys.home.HomeRepositoryImpl
import com.example.goodfood.di.components.FragmentScope
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @FragmentScope
    @Binds
    abstract fun provideHomeRepository(repository:HomeRepositoryImpl):HomeRepository
}
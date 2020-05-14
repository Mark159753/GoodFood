package com.example.goodfood.di.modules

import com.example.goodfood.data.repositorys.account.AccountRepository
import com.example.goodfood.data.repositorys.account.AccountRepositoryImpl
import com.example.goodfood.data.repositorys.home.HomeRepository
import com.example.goodfood.data.repositorys.home.HomeRepositoryImpl
import com.example.goodfood.di.components.FragmentScope
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun provideHomeRepository(repository:HomeRepositoryImpl):HomeRepository

    @Binds
    abstract fun provideAccountRepository(repository:AccountRepositoryImpl):AccountRepository
}
package com.example.goodfood.fragments

import androidx.fragment.app.FragmentFactory
import com.example.goodfood.di.ViewModelFactoryDI
import com.example.goodfood.di.components.FragmentScope
import com.example.goodfood.ui.bookmark.BookmarkFragment
import com.example.goodfood.ui.home.HomeFragment
import com.example.goodfood.ui.search.SearchFragment
import com.example.goodfood.ui.user.AccountFragment
import javax.inject.Inject

@FragmentScope
class MainFragmentFactory @Inject constructor(
    private val viewModelFactory: ViewModelFactoryDI
):FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String) =
        when(className){
            HomeFragment::class.java.name -> HomeFragment(viewModelFactory)
            SearchFragment::class.java.name -> SearchFragment(viewModelFactory)
            BookmarkFragment::class.java.name -> BookmarkFragment(viewModelFactory)
            AccountFragment::class.java.name -> AccountFragment(viewModelFactory)
            else -> HomeFragment(viewModelFactory)
    }
}
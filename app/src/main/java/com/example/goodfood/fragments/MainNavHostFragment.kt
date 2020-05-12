package com.example.goodfood.fragments

import android.content.Context
import androidx.navigation.fragment.NavHostFragment
import com.example.goodfood.ui.MainActivity

class MainNavHostFragment:NavHostFragment() {

    override fun onAttach(context: Context) {
        childFragmentManager.fragmentFactory =
            (activity as MainActivity).mainFragmentFactory
        super.onAttach(context)
    }
}
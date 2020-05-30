package com.example.goodfood.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentFactory
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.goodfood.GoodFoodApp
import com.example.goodfood.R
import com.example.goodfood.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity() {

    private lateinit var navController: NavController

    @Inject
    lateinit var mainFragmentFactory: FragmentFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        (application as GoodFoodApp).appComponent.getFragmentComponent().create().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.main_host_fragment)
        NavigationUI.setupWithNavController(bottom_navigation, navController)
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        checkIsUserSignIn(currentUser)
    }

}

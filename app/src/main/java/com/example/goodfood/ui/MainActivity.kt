package com.example.goodfood.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentFactory
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.goodfood.GoodFoodApp
import com.example.goodfood.R
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import javax.inject.Named

class MainActivity : AppCompatActivity() {

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
}

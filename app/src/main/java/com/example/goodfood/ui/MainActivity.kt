package com.example.goodfood.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentFactory
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.goodfood.GoodFoodApp
import com.example.goodfood.R
import com.example.goodfood.ui.base.BaseActivity
import com.example.goodfood.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity() {

    private lateinit var navController: NavController

    private lateinit var auth: FirebaseAuth

    @Inject
    lateinit var mainFragmentFactory: FragmentFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        (application as GoodFoodApp).appComponent.getFragmentComponent().create().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth

        navController = Navigation.findNavController(this, R.id.main_host_fragment)
        NavigationUI.setupWithNavController(bottom_navigation, navController)
    }

    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        checkIsUserSignIn(currentUser)
    }

    fun signOut(){
        auth.signOut()
        checkIsUserSignIn(null)
    }

    private fun checkIsUserSignIn(user:FirebaseUser?){
        if (user == null){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}

package com.example.goodfood.ui.login

import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.goodfood.R
import com.example.goodfood.ui.base.BaseActivity

import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun showHideProgressBar(isLoading:Boolean){
        if (isLoading){
            loading_view.visibility = View.VISIBLE
        }else{
            loading_view.visibility = View.GONE
        }
    }
}

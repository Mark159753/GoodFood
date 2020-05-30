package com.example.goodfood.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.goodfood.R
import com.example.goodfood.ui.MainActivity
import com.example.goodfood.ui.base.BaseActivity
import com.google.firebase.auth.ktx.auth

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

    fun createAccount(email:String, password:String){
        showHideProgressBar(true)
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    Log.d("Create User Account", "Is Success")
                    showHideProgressBar(false)
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }else {
                    Log.e("CANT CREATE ACCOUNT", task.exception?.message ?: "Sorry")
                    showHideProgressBar(false)
                    showErrorDialog(resources.getString(R.string.create_account_error))
                }
            }
    }

    fun signIn(email:String, password:String){
        showHideProgressBar(true)
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    Log.d("LogIn", "SUCCESS")
                    showHideProgressBar(false)
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Log.e("LogIn", "ERROR", task.exception)
                    showHideProgressBar(false)
                    showErrorDialog(resources.getString(R.string.sign_in_error))
                }
            }
    }
}

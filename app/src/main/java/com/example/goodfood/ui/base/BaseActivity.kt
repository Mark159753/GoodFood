package com.example.goodfood.ui.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onDismiss
import com.example.goodfood.R
import com.example.goodfood.ui.login.LoginActivity
import com.example.goodfood.untils.LocaleHelper
import com.example.goodfood.untils.ThemeHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

abstract class BaseActivity: AppCompatActivity(), UICommunication {

    private var viewDialog:MaterialDialog? = null

    protected lateinit var auth: FirebaseAuth

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase!!, LocaleHelper.getLanguage(newBase)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

        val theme = PreferenceManager.getDefaultSharedPreferences(this).getString("them_options", "NULL")
        ThemeHelper.applyTheme(theme!!)
    }


    override fun singOut() {
        auth.signOut()
        checkIsUserSignIn(null)
    }


    protected fun checkIsUserSignIn(user: FirebaseUser?){
        if (user == null){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun showErrorDialog(msg: String) {
        viewDialog = displayErrorDialog(msg)
    }

    override fun makeToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun displayErrorDialog(msg: String):MaterialDialog{
        return MaterialDialog(this)
            .show {
                title(R.string.error_dialog_title)
                message(text = msg)
                positiveButton(R.string.text_ok){
                    dismiss()
                }
                    .onDismiss { viewDialog = null }
                    .cancelable(false)
            }
    }



    override fun onPause() {
        if (viewDialog != null){
            viewDialog!!.dismiss()
            viewDialog = null
        }
        super.onPause()
    }
}
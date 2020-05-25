package com.example.goodfood.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText

import com.example.goodfood.R
import com.example.goodfood.ui.MainActivity
import com.example.goodfood.ui.base.BaseFragment
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginFragment : BaseFragment() {

    private lateinit var auth: FirebaseAuth

    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var logInAccountBtn: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emailInput = view.findViewById(R.id.login_email_input)
        passwordInput = view.findViewById(R.id.login_password_input)
        logInAccountBtn = view.findViewById(R.id.button_login)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        auth = Firebase.auth

        logInAccountBtn.setOnClickListener {
            if (isInvalidForm())
                signIn(emailInput.text.toString(), passwordInput.text.toString())
        }
    }

    private fun signIn(email:String, password:String){
        val loginActivity = activity as LoginActivity
        loginActivity.showHideProgressBar(true)
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    Log.d("LogIn", "SUCCESS")
                    loginActivity.showHideProgressBar(false)
                    val intent = Intent(activity, MainActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }else{
                    Log.e("LogIn", "ERROR", task.exception)
                    loginActivity.showHideProgressBar(false)
                    uiCommunication.showErrorDialog(resources.getString(R.string.sign_in_error))
                }
            }
    }

    private fun isInvalidForm():Boolean{
        if (TextUtils.isEmpty(emailInput.text))
            return false
        if (TextUtils.isEmpty(passwordInput.text))
            return false
        return true
    }
}

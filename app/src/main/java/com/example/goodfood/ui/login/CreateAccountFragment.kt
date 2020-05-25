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
import android.widget.Toast

import com.example.goodfood.R
import com.example.goodfood.ui.MainActivity
import com.example.goodfood.ui.base.BaseFragment
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class CreateAccountFragment : BaseFragment() {

    private lateinit var auth: FirebaseAuth

    private lateinit var emailInput:EditText
    private lateinit var passwordInput:EditText
    private lateinit var createAccountBtn:MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emailInput = view.findViewById(R.id.create_email_input)
        passwordInput = view.findViewById(R.id.create_password_input)
        createAccountBtn = view.findViewById(R.id.button_create)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        auth = Firebase.auth

        createAccountBtn.setOnClickListener {
            if (isInvalidForm())
                createAccount(emailInput.text.toString(), passwordInput.text.toString())
        }
    }

    private fun createAccount(email:String, password:String){
        val loginActivity = activity as LoginActivity
        loginActivity.showHideProgressBar(true)
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    Log.d("Create User Account", "Is Success")
                    loginActivity.showHideProgressBar(false)
                    val intent = Intent(activity, MainActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }else {
                    Log.e("CANT CREATE ACCOUNT", task.exception?.message ?: "Sorry")
                    loginActivity.showHideProgressBar(false)
                    uiCommunication.showErrorDialog(resources.getString(R.string.create_account_error))
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

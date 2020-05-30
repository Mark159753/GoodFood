package com.example.goodfood.ui.login

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText

import com.example.goodfood.R
import com.example.goodfood.ui.base.BaseFragment
import com.google.android.material.button.MaterialButton


class CreateAccountFragment : BaseFragment() {

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

        createAccountBtn.setOnClickListener {
            if (isInvalidForm())
                (activity as LoginActivity).createAccount(emailInput.text.toString(), passwordInput.text.toString())
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

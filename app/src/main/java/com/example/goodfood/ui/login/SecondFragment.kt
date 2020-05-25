package com.example.goodfood.ui.login


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.goodfood.R


class SecondFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.login_second_create_account_btn).setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_createAccountFragment)
        }

        view.findViewById<TextView>(R.id.login_second_have_account).setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_loginFragment)
        }
    }

}

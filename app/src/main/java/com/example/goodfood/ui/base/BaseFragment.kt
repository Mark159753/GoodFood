package com.example.goodfood.ui.base

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import java.lang.ClassCastException

abstract class BaseFragment: Fragment() {

    protected lateinit var uiCommunication: UICommunication

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            uiCommunication = context as UICommunication
        }catch (e: ClassCastException){
            Log.e("HomeFragment", "Context must implement UICommunication")
        }
    }
}
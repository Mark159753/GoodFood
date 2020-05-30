package com.example.goodfood.ui.base

interface UICommunication {

    fun showErrorDialog(msg:String)

    fun makeToast(msg: String)

    fun singOut()
}
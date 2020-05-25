package com.example.goodfood.ui.base

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onDismiss
import com.example.goodfood.R

abstract class BaseActivity: AppCompatActivity(), UICommunication {

    private var viewDialog:MaterialDialog? = null


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
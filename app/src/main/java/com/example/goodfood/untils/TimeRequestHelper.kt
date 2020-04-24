package com.example.goodfood.untils

import android.content.Context
import android.text.format.DateUtils
import androidx.preference.PreferenceManager
import java.util.*

object TimeRequestHelper {

    @JvmStatic
    fun saveCurrentTime(key:String, context: Context){
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        with(preferences.edit()){
            putLong(key, Date().time)
            commit()
        }
    }

    @JvmStatic
    fun isUpdateNeeded(key: String, wait:Int, context: Context):Boolean{
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val requestTime = Date(preferences.getLong(key, 0L))
        val after = Date(requestTime.time + wait * DateUtils.HOUR_IN_MILLIS)
        return Date(Date().time).after(after)
    }
}
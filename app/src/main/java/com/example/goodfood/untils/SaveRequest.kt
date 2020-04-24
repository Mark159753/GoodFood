package com.example.goodfood.untils

import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception

@Suppress("UNREACHABLE_CODE")
suspend fun <T>saveRequest(networkCall: suspend () -> Response<T>): DataListener<T> {
    return return try {
        val response = networkCall.invoke()
        if (response.isSuccessful){
            DataListener(response.body(), null)
        }else{
            DataListener(null, "code error ${response.code()}")
        }
    }catch (e:Exception){
        DataListener(null, e.message ?: "Unknown Error")
    }
}
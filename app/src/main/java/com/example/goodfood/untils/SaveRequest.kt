package com.example.goodfood.untils

import com.example.goodfood.untils.api.ApiResponse
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception

suspend fun <T>saveApiRequest(networkCall: suspend () -> Response<T>): ApiResponse<T> {
    return ApiResponse.create(networkCall.invoke())
}
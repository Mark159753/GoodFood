package com.example.goodfood.untils.api

import retrofit2.Response

sealed class ApiResponse<T> {
    companion object{

        fun <T> create(response: Response<T>): ApiResponse<T> {
            return try {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body == null || response.code() == 204) {
                        ApiEmptyResponse()
                    } else {
                        ApiSuccessResponse(
                            body = body
                        )
                    }
                } else {
                    val msg = response.errorBody()?.string()
                    val errorMsg = if (msg.isNullOrEmpty()) {
                        response.message()
                    } else {
                        msg
                    }
                    ApiErrorResponse(errorMsg ?: "unknown error")
                }
            }catch (e:Exception){
                ApiErrorResponse(e.message ?: "unknown error")
            }
        }
    }

    class ApiEmptyResponse<T> : ApiResponse<T>()

    data class ApiSuccessResponse<T>(
        val body: T
    ) : ApiResponse<T>()

    data class ApiErrorResponse<T>(val errorMessage: String) : ApiResponse<T>()
}
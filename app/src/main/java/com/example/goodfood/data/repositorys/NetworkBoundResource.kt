package com.example.goodfood.data.repositorys

import android.util.Log
import com.example.goodfood.untils.Resource
import com.example.goodfood.untils.api.ApiResponse
import com.example.goodfood.untils.saveApiRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

abstract class NetworkBoundResource<NetworkObj, ResultType>(
    private val cacheCall: suspend () -> ResultType,
    private val apiCall: suspend () -> Response<NetworkObj>
) {

    val result:Flow<Resource<ResultType>> = flow {
        emit(Resource.loading(null))
        val cache = cacheCall.invoke()
        if (shouldFetch(cache)){
            try {
                when(val network = saveApiRequest { apiCall.invoke() }){
                    is ApiResponse.ApiSuccessResponse -> {
                        saveCallResult(network.body)
                    }
                    is ApiResponse.ApiEmptyResponse -> {
                        saveCallResult(null)
                    }
                    is ApiResponse.ApiErrorResponse -> {
                        emit(Resource.error(network.errorMessage, null))
                    }
                }
            }catch (e:Exception){
                emit(Resource.error(e.message ?: "Unknown Error", null))
            }
        }
        val result = cacheCall.invoke()
        emit(Resource.success(result))
    }


    abstract suspend fun saveCallResult(item: NetworkObj?)

    abstract suspend fun shouldFetch(data: ResultType?): Boolean
}
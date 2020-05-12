package com.example.goodfood.ui.search.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.goodfood.data.network.FoodServer
import com.example.goodfood.model.search.Result
import com.example.goodfood.model.search.SearchResponse
import com.example.goodfood.untils.NetworkState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchDataSource(
    private val recipeServer: FoodServer,
    private val request:String,
    private val number:Int
):PageKeyedDataSource<Int, Result>() {

    private val networkState = MutableLiveData<NetworkState>()

    fun getNetworkState() = networkState

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Result>
    ) {
        networkState.postValue(NetworkState.LOADING)
        recipeServer.getSearch(request, number).enqueue(object :Callback<SearchResponse>{
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                if (response.isSuccessful){
                    callback.onResult(response.body()?.results ?: emptyList(), null, number)
                    networkState.postValue(NetworkState.LOADED)
                }else{
                    networkState.postValue(NetworkState.ERROR("code ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                networkState.postValue(NetworkState.ERROR(t.message ?: "Unknown Error"))
            }
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Result>) {
        networkState.postValue(NetworkState.LOADING)
        recipeServer.getSearch(request, number, params.key).enqueue(object :Callback<SearchResponse>{
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                if (response.isSuccessful){
                    val nextKey = if (params.key == response.body()?.totalResults) null else params.key + number
                    networkState.postValue(NetworkState.LOADED)
                    callback.onResult(response.body()?.results ?: emptyList(), nextKey)
                }else{
                    networkState.postValue(NetworkState.ERROR("code ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                networkState.postValue(NetworkState.ERROR(t.message ?: "Unknown Error"))
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Result>) {
        // Just Ignore This
    }
}
package com.example.goodfood.ui.search.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.goodfood.data.network.FoodServer
import com.example.goodfood.model.search.Result

class SearchDataFactory(
    private val recipeServer: FoodServer,
    private val number:Int,
    private val request:String
): DataSource.Factory<Int, Result>() {

    private val dataSourceLiveData = MutableLiveData<SearchDataSource>()
    private lateinit var searchDataSource: SearchDataSource

    override fun create(): DataSource<Int, Result> {
        searchDataSource = SearchDataSource(
            recipeServer,
            request,
            number
        )
        dataSourceLiveData.postValue(searchDataSource)
        return searchDataSource
    }

    fun getDataSourceLiveData() = dataSourceLiveData
}
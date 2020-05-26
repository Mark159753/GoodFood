package com.example.goodfood.ui.search

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.goodfood.data.local.entitys.RecipeEntity
import com.example.goodfood.data.network.FoodServer
import com.example.goodfood.ui.search.paging.SearchDataFactory
import com.example.goodfood.untils.Listener
import com.example.goodfood.untils.Resource
import com.example.goodfood.untils.api.ApiResponse
import com.example.goodfood.untils.saveApiRequest
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val recipeServer: FoodServer
) :ViewModel() {

    private val executor = Executors.newSingleThreadExecutor()

    fun makeSearch(request:String): Listener {
        val dataFactory = SearchDataFactory(recipeServer, 20, request)

        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(10)
            .setPageSize(20)
            .build()
        return Listener(
            networkState = Transformations.switchMap(dataFactory.getDataSourceLiveData()){it.getNetworkState()},
            data = LivePagedListBuilder(dataFactory, pagedListConfig)
                .setFetchExecutor(executor)
                .build()
        )
    }

}
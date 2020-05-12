package com.example.goodfood.untils

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.goodfood.model.search.Result

data class Listener(
    val networkState: LiveData<NetworkState>,
    val data: LiveData<PagedList<Result>>
)
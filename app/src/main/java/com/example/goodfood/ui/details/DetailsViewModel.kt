package com.example.goodfood.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goodfood.data.local.entitys.RecipeEntity
import com.example.goodfood.data.network.FoodServer
import com.example.goodfood.untils.Resource
import com.example.goodfood.untils.api.ApiResponse
import com.example.goodfood.untils.saveApiRequest
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val recipeServer: FoodServer
) : ViewModel() {

    private val _recipe = MutableLiveData<Resource<RecipeEntity>>()
    val recipe: LiveData<Resource<RecipeEntity>>
        get() = _recipe

    private var isRecipeLoaded = false

    fun getRecipeById(id:Int){
        if (!isRecipeLoaded || _recipe.value == null) {
            viewModelScope.launch {
                _recipe.postValue(Resource.loading(null))
                try {
                    when (val response = saveApiRequest { recipeServer.getRecipeById(id) }) {
                        is ApiResponse.ApiSuccessResponse -> {
                            val recipeEntity = response.body.toRecipeEntity()
                            _recipe.postValue(Resource.success(recipeEntity))
                        }
                        is ApiResponse.ApiEmptyResponse -> {
                            _recipe.postValue(Resource.success(null))
                        }
                        is ApiResponse.ApiErrorResponse -> {
                            _recipe.postValue(Resource.error(response.errorMessage, null))
                        }
                    }
                } catch (e: Exception) {
                    _recipe.postValue(Resource.error(e.message ?: "Unknown Error", null))
                }
            }
            isRecipeLoaded = true
        }
    }
}

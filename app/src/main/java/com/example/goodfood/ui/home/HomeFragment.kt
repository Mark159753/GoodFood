package com.example.goodfood.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.goodfood.GoodFoodApp

import com.example.goodfood.R
import com.example.goodfood.di.ViewModelFactoryDI
import com.example.goodfood.model.recipe.Recipe
import com.example.goodfood.ui.MainActivity
import com.example.goodfood.untils.LoadState
import javax.inject.Inject


class HomeFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactoryDI
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        ((requireActivity() as MainActivity).application as GoodFoodApp)
            .appComponent
            .getFragmentComponent()
            .create()
            .inject(this)

        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        viewModel.randomRecipesDataListener.observe(viewLifecycleOwner, Observer {
            when(it){
                is LoadState.LOADING -> Log.e("STATE", "LOADING")
                is LoadState.LOADED<List<Recipe>> -> {
                    Log.e("STATE", "LOADED")
                    for (i in it.data){
                        Log.e("RECIPE: ", i.toString())
                    }
                }
                is LoadState.ERROR -> {
                    Log.e("STATE", "ERROR ${it.msg}")
                }
            }
        })

    }

}

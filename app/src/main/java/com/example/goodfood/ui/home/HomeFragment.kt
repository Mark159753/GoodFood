package com.example.goodfood.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.goodfood.GoodFoodApp

import com.example.goodfood.R
import com.example.goodfood.data.local.entitys.RecipeEntity
import com.example.goodfood.databinding.FragmentHomeBinding
import com.example.goodfood.di.ViewModelFactoryDI
import com.example.goodfood.ui.MainActivity
import com.example.goodfood.ui.home.adapters.MarginItemDecorator
import com.example.goodfood.ui.home.adapters.recycler.HomeMainRecyclerAdapter
import com.example.goodfood.ui.home.adapters.recyclerItemInner.HomeInnerRecyclerAdapter
import com.example.goodfood.ui.home.adapters.viewPager.CarouselEffectTransformer
import com.example.goodfood.ui.home.adapters.viewPager.ViewPagerAdapterR
import com.example.goodfood.untils.LoadState
import com.example.goodfood.untils.NetworkState
import javax.inject.Inject


class HomeFragment(
    private val viewModelFactory: ViewModelFactoryDI
) : Fragment() {

    private lateinit var binder:FragmentHomeBinding

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binder.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        initViewPager()
        initRecyclerList()
        initRefresh()
    }


    private fun initRefresh(){
        binder.homeRefresh.setOnRefreshListener {
            viewModel.refreshRecipes()
            binder.homeRefresh.isRefreshing = false
        }
    }

    private fun initRecyclerList(){
        val recyclerAdapter = HomeMainRecyclerAdapter(requireContext())
        binder.homeRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerAdapter
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
            addItemDecoration(MarginItemDecorator(resources.getDimension(R.dimen.home_inner_item_padding).toInt(), 0))
        }
        val nestedAdapters:Array<HomeInnerRecyclerAdapter> = Array(5){ HomeInnerRecyclerAdapter()}

        recyclerAdapter.setAdapters(nestedAdapters)
        viewModel.initRandomVeganRecipe(false)
        viewModel.randomVeganRecipes.observe(viewLifecycleOwner, Observer {
            checkAdapterLoadState(nestedAdapters[0], it)
        })
        viewModel.initRandomDrinkRecipe(false)
        viewModel.randomDrinkRecipes.observe(viewLifecycleOwner, Observer {
            checkAdapterLoadState(nestedAdapters[1], it)
        })
        viewModel.initRandomDessertRecipe(false)
        viewModel.randomDessertRecipes.observe(viewLifecycleOwner, Observer {
            checkAdapterLoadState(nestedAdapters[2], it)
        })
        viewModel.initRandomSaladRecipe(false)
        viewModel.randomSaladRecipes.observe(viewLifecycleOwner, Observer {
            checkAdapterLoadState(nestedAdapters[3], it)
        })
        viewModel.initRandomSoupRecipe(false)
        viewModel.randomSoupRecipes.observe(viewLifecycleOwner, Observer {
            checkAdapterLoadState(nestedAdapters[4], it)
        })
    }

    private fun checkAdapterLoadState(adapter: HomeInnerRecyclerAdapter, state: LoadState<List<RecipeEntity>>){
        when (state) {
            is LoadState.LOADED -> {
                adapter.setNetworkState(NetworkState.LOADED)
                adapter.setDataList(state.data)
            }
            is LoadState.LOADING -> {
                adapter.setNetworkState(NetworkState.LOADING)
            }
            is LoadState.ERROR -> {
                adapter.setNetworkState(NetworkState.ERROR(state.msg))
            }
        }
    }

    private fun initViewPager() {
        val pagerAdapter = ViewPagerAdapterR(requireContext())
        viewModel.initRandomRecipe(false)
        viewModel.randomRecipes.observe(viewLifecycleOwner, Observer {
            when (it) {
                is LoadState.LOADED -> {
                    binder.homeViewpagerLoading.visibility = View.GONE
                    binder.homeViewpagerErrorMsg.visibility = View.GONE
                    binder.homeViewpager.visibility = View.VISIBLE
                    pagerAdapter.setDataList(it.data)
                    binder.homeViewpager.setPageTransformer(true, CarouselEffectTransformer(requireContext()))

                    if (binder.homeViewpager.childCount != 0){
                        binder.homeViewpager.beginFakeDrag()
                        binder.homeViewpager.fakeDragBy(-10f)
                        binder.homeViewpager.endFakeDrag()
                    }
                }
                is LoadState.LOADING -> {
                    binder.homeViewpager.visibility = View.GONE
                    binder.homeViewpagerErrorMsg.visibility = View.GONE
                    binder.homeViewpagerLoading.visibility = View.VISIBLE
                }
                is LoadState.ERROR -> {
                    binder.homeViewpager.visibility = View.GONE
                    binder.homeViewpagerLoading.visibility = View.GONE
                    binder.homeViewpagerErrorMsg.text = it.msg
                    binder.homeViewpagerErrorMsg.visibility = View.VISIBLE
                }
            }
        })

        binder.homeViewpager.apply {
            adapter = pagerAdapter
            pageMargin = resources.getDimension(R.dimen.home_viewpager_offset).toInt()
            offscreenPageLimit = 3
        }
    }


}

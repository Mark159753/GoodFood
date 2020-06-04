package com.example.goodfood.ui.home

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ActivityNavigator
import androidx.navigation.ActivityNavigatorExtras
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.goodfood.R
import com.example.goodfood.data.local.entitys.RecipeEntity
import com.example.goodfood.databinding.FragmentHomeBinding
import com.example.goodfood.di.ViewModelFactoryDI
import com.example.goodfood.ui.base.BaseFragment
import com.example.goodfood.ui.home.adapters.MarginItemDecorator
import com.example.goodfood.ui.home.adapters.recycler.HomeMainRecyclerAdapter
import com.example.goodfood.ui.home.adapters.recyclerItemInner.HomeInnerRecyclerAdapter
import com.example.goodfood.ui.home.adapters.viewPager.CarouselEffectTransformer
import com.example.goodfood.ui.home.adapters.viewPager.ViewPagerAdapterR
import com.example.goodfood.untils.ItemSelectedListener
import com.example.goodfood.untils.NetworkState
import com.example.goodfood.untils.Resource
import com.example.goodfood.untils.Status

import android.util.Pair as UtilPair


class HomeFragment(
    private val viewModelFactory: ViewModelFactoryDI
) : BaseFragment(), ItemSelectedListener {

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
            viewModel.refreshRecipes(true)
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
        val nestedAdapters:Array<HomeInnerRecyclerAdapter> = Array(5){
            HomeInnerRecyclerAdapter().also { it.setListener(this) }
        }

        recyclerAdapter.setAdapters(nestedAdapters)
        viewModel.randomVeganRecipes.observe(viewLifecycleOwner, Observer {
            checkAdapterLoadState(nestedAdapters[0], it)
        })
        viewModel.randomDrinkRecipes.observe(viewLifecycleOwner, Observer {
            checkAdapterLoadState(nestedAdapters[1], it)
        })
        viewModel.randomDessertRecipes.observe(viewLifecycleOwner, Observer {
            checkAdapterLoadState(nestedAdapters[2], it)
        })
        viewModel.randomSaladRecipes.observe(viewLifecycleOwner, Observer {
            checkAdapterLoadState(nestedAdapters[3], it)
        })
        viewModel.randomSoupRecipes.observe(viewLifecycleOwner, Observer {
            checkAdapterLoadState(nestedAdapters[4], it)
        })
    }

    private fun checkAdapterLoadState(adapter: HomeInnerRecyclerAdapter, state: Resource<List<RecipeEntity>>){
        when (state.status) {
            is Status.SUCCESS -> {
                adapter.setNetworkState(NetworkState.LOADED)
                adapter.setDataList(state.data!!)
            }
            is Status.LOADING -> {
                adapter.setNetworkState(NetworkState.LOADING)
            }
            is Status.ERROR -> {
                adapter.setNetworkState(NetworkState.ERROR(state.message ?: "Unknown Error"))
            }
        }
    }

    private fun initViewPager() {
        val pagerAdapter = ViewPagerAdapterR(requireContext())
        pagerAdapter.setItemSelectedListener(this)
        viewModel.randomRecipes.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                is Status.SUCCESS -> {
                    binder.homeViewpagerLoading.visibility = View.GONE
                    binder.homeViewpagerErrorMsg.visibility = View.GONE
                    binder.homeViewpager.visibility = View.VISIBLE
                    pagerAdapter.setDataList(it.data!!)
                    binder.homeViewpager.setPageTransformer(true, CarouselEffectTransformer(requireContext()))

                    if (binder.homeViewpager.childCount != 0){
                        binder.homeViewpager.beginFakeDrag()
                        binder.homeViewpager.fakeDragBy(-10f)
                        binder.homeViewpager.endFakeDrag()
                    }
                }
                is Status.LOADING -> {
                    binder.homeViewpager.visibility = View.GONE
                    binder.homeViewpagerErrorMsg.visibility = View.GONE
                    binder.homeViewpagerLoading.visibility = View.VISIBLE
                }
                is Status.ERROR -> {
                    binder.homeViewpager.visibility = View.GONE
                    binder.homeViewpagerLoading.visibility = View.GONE
                    binder.homeViewpagerErrorMsg.text = it.message
                    binder.homeViewpagerErrorMsg.visibility = View.VISIBLE
                    uiCommunication.showErrorDialog(it.message ?: "Something was Broken")
                }
            }
        })

        binder.homeViewpager.apply {
            adapter = pagerAdapter
            pageMargin = resources.getDimension(R.dimen.home_viewpager_offset).toInt()
            offscreenPageLimit = 3
        }
    }

    override fun onItemSelected(data: RecipeEntity, sharedView:View?) {
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity(),
            Pair.create(sharedView!!, "${requireContext().getString(R.string.transition_key)}_${data.id}")
        )
        val extras = ActivityNavigatorExtras(options)
        val action = HomeFragmentDirections.actionHomeFragmentToDetailActivity(data)
        findNavController().navigate(action, extras)
    }
}

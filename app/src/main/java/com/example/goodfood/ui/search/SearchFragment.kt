package com.example.goodfood.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.os.bundleOf
import androidx.core.util.Pair
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ActivityNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.goodfood.R
import com.example.goodfood.data.local.entitys.RecipeEntity
import com.example.goodfood.databinding.FragmentSearchBinding
import com.example.goodfood.di.ViewModelFactoryDI
import com.example.goodfood.ui.details.DetailActivity
import com.example.goodfood.ui.home.adapters.MarginItemDecorator
import com.example.goodfood.ui.search.adapter.SearchAdapter
import com.example.goodfood.ui.search.adapter.SearchItemSelected


class SearchFragment(
    private val viewModelFactory: ViewModelFactoryDI
) : Fragment(), SearchItemSelected {

    private lateinit var binder:FragmentSearchBinding

    private lateinit var mAdapter:SearchAdapter
    private val adapterDataObserver = object :RecyclerView.AdapterDataObserver(){
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            if (positionStart == 0){
                binder.searchList.scrollToPosition(0)
            }
        }
    }

    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        return binder.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory).get(SearchViewModel::class.java)

        initSearchList()
        initSearchInputField()
    }

    private fun initSearchInputField(){
        binder.searchInput.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (count > 0){
                    updateSearchList(s.toString())
                }
            }
        })
        if (binder.searchInput.text.toString() == ""){
            updateSearchList("")
        }
    }

    private fun initSearchList(){
        mAdapter = SearchAdapter()
        mAdapter.setListener(this)
        mAdapter.registerAdapterDataObserver(adapterDataObserver)
        binder.searchList.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            addItemDecoration(MarginItemDecorator(resources.getDimension(R.dimen.home_inner_item_padding).toInt(), 0))
        }
    }

    override fun onSearchItemSelected(id: Int, sharedView: View?) {
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity(),
            Pair.create(sharedView!!, "${requireContext().getString(R.string.transition_key)}_${id}")
        )
        val extras = ActivityNavigatorExtras(options)

        val action = SearchFragmentDirections.actionSearchFragmentToDetailActivity(id)
        findNavController().navigate(action, extras)
    }

    private fun updateSearchList(q:String){
        val listener = viewModel.makeSearch(q)

        listener.networkState.observe(viewLifecycleOwner, Observer {
            mAdapter.setNetworkState(it)
        })
        listener.data.observe(viewLifecycleOwner, Observer {
            mAdapter.submitList(it)
            Log.e("PAGING", "SUBMIT")
        })
    }

    override fun onDestroy() {
        mAdapter.unregisterAdapterDataObserver(adapterDataObserver)
        super.onDestroy()
    }

}

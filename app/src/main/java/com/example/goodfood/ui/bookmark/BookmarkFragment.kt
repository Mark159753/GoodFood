package com.example.goodfood.ui.bookmark

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.goodfood.R
import com.example.goodfood.data.local.entitys.RecipeEntity
import com.example.goodfood.di.ViewModelFactoryDI
import com.example.goodfood.ui.base.BaseFragment
import com.example.goodfood.untils.FirestoreHelper

class BookmarkFragment(
    private val firestoreHelper: FirestoreHelper
) : BaseFragment() {

    private lateinit var bookmarkList:RecyclerView
    private lateinit var emptyScreen:RelativeLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bookmark, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bookmarkList = view.findViewById(R.id.bookmark_list)
        emptyScreen = view.findViewById(R.id.bookmark_empty)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val mAdapter = FavoriteAdapter(viewLifecycleOwner, firestoreHelper)
        mAdapter.setEmptyListener(this::showEmptyScreen)

        bookmarkList.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        }
        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(mAdapter))
        itemTouchHelper.attachToRecyclerView(bookmarkList)
    }

    private fun showEmptyScreen(isEmpty:Boolean){
        if (isEmpty){
            emptyScreen.visibility = View.VISIBLE
            bookmarkList.visibility = View.GONE
        }else{
            emptyScreen.visibility = View.GONE
            bookmarkList.visibility = View.VISIBLE
        }
    }
}

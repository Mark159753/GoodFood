package com.example.goodfood.ui.details.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.goodfood.R
import com.example.goodfood.model.recipe.Step
import com.example.goodfood.ui.details.adpter.DirectionAdapter

private const val ARG_PARAM1 = "com.example.goodfood.ui.details.fragments.Step"

class DirectionsFragment: Fragment() {
    private var param1: List<Step>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getParcelableArrayList(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ingredients, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.ingredients_recyclerview)
        val mAdapter = DirectionAdapter()
        param1?.let { mAdapter.setList(it) }
        recyclerView.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        }
    }

    companion object {
        fun newInstance(list: ArrayList<Step>) =
            DirectionsFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_PARAM1, list)
                }
            }
    }
}
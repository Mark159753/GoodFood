package com.example.goodfood.ui.details.adpter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.goodfood.R
import com.example.goodfood.model.recipe.ExtendedIngredient

class IngredientsAdapter: RecyclerView.Adapter<IngredientsAdapter.Holder>() {

    private var dataList:List<ExtendedIngredient> = emptyList()

    fun setDataList(list:List<ExtendedIngredient>){
        this.dataList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.ingedients_item, parent, false)
        return Holder(v)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val text = dataList[position].original
        holder.title.text = text
    }

    inner class Holder(view:View):RecyclerView.ViewHolder(view){
        val title = view.findViewById<TextView>(R.id.ingredients_title)
    }
}
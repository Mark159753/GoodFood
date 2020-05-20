package com.example.goodfood.ui.details.adpter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.goodfood.R
import com.example.goodfood.model.recipe.Step

class DirectionAdapter:RecyclerView.Adapter<DirectionAdapter.Holder>() {

    private var listData:List<Step> = emptyList()

    fun setList(list: List<Step>){
        this.listData = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.directions_item, parent, false)
        return Holder(v)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = listData[position]
        holder.stepNumber.text = "Step ${item.number}"
        holder.description.text = item.step
    }

    inner class Holder(view:View):RecyclerView.ViewHolder(view){
        val stepNumber = view.findViewById<TextView>(R.id.direction_step_number)
        val description = view.findViewById<TextView>(R.id.direction_description)
    }
}
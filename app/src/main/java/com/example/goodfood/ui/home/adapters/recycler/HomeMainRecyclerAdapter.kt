package com.example.goodfood.ui.home.adapters.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.goodfood.R
import com.example.goodfood.ui.home.adapters.MarginItemDecorator
import com.example.goodfood.ui.home.adapters.recyclerItemInner.HomeInnerRecyclerAdapter

class HomeMainRecyclerAdapter(context: Context) :RecyclerView.Adapter<HomeMainRecyclerAdapter.Holder>(){

    private val titles:Array<String> = arrayOf(
        context.getString(R.string.home_category_vegan),
        context.getString(R.string.home_category_drink),
        context.getString(R.string.home_category_dessert),
        context.getString(R.string.home_category_salad),
        context.getString(R.string.home_category_soup)
    )

    private var adapters:Array<HomeInnerRecyclerAdapter> = emptyArray()
    private val recyclerPool = RecyclerView.RecycledViewPool()
    private val decorator = MarginItemDecorator(0, context.resources.getDimension(R.dimen.home_inner_item_padding).toInt())

    fun setAdapters(list: Array<HomeInnerRecyclerAdapter>){
        this.adapters = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.home_recycler_item, parent, false)
        val holder = Holder(v)
        holder.list.setRecycledViewPool(recyclerPool)
        return holder
    }

    override fun getItemCount(): Int {
        return adapters.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.title.text = titles[position]
        holder.list.apply {
            adapter = adapters[position]
            setHasFixedSize(true)
            addItemDecoration(decorator)
        }
    }

    inner class Holder(view:View):RecyclerView.ViewHolder(view){
        val list = view.findViewById<RecyclerView>(R.id.home_recycler_item_list)
        val title = view.findViewById<TextView>(R.id.home_recycler_item_title)
    }

}
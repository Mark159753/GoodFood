package com.example.goodfood.ui.home.adapters.recyclerItemInner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.goodfood.R
import com.example.goodfood.data.local.entitys.RecipeEntity
import com.example.goodfood.untils.NetworkState
import com.squareup.picasso.Picasso

class HomeInnerRecyclerAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listData:List<RecipeEntity> = emptyList()

    private var networkState: NetworkState? = null

    fun setNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyDataSetChanged()
            } else {
                notifyDataSetChanged()
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyDataSetChanged()
        }
    }

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED


    fun setDataList(list: List<RecipeEntity>){
        this.listData = list
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
         return when(viewType){
            R.layout.home_inner_recycler_item ->{
                val v = LayoutInflater.from(parent.context).inflate(R.layout.home_inner_recycler_item, parent, false)
                Holder(v)
            }
            R.layout.home_inner_item_network_state ->{
                val v = LayoutInflater.from(parent.context).inflate(R.layout.home_inner_item_network_state, parent, false)
                NetworkStateHolder(v)
            }
            else -> throw IllegalStateException("unknown view type $viewType")
        }
    }

    override fun getItemCount(): Int {
        return listData.size + if (hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount -1){
            R.layout.home_inner_item_network_state
        }else{
            R.layout.home_inner_recycler_item
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is NetworkStateHolder -> { holder.bind(networkState)}
            is Holder -> {
                val item = listData[position]
                holder.title.text = item.title
                Picasso.get()
                    .load(item.image)
                    .placeholder(R.drawable.placeholder)
                    .into(holder.img)
            }
        }
    }

    inner class Holder(view:View):RecyclerView.ViewHolder(view){
        val img = view.findViewById<ImageView>(R.id.inner_item_img)
        val title = view.findViewById<TextView>(R.id.inner_item_title)
    }

    inner class NetworkStateHolder(view: View):RecyclerView.ViewHolder(view){
        val progress = view.findViewById<ProgressBar>(R.id.home_item_load_progress)
        val msgLoadError = view.findViewById<TextView>(R.id.error_loading_msg)

        fun bind(networkState: NetworkState?){
            progress.visibility = toVisibility(networkState is NetworkState.LOADING)
            if (networkState is NetworkState.ERROR){
                msgLoadError.text = networkState.msg
            }
        }
    }

    private fun toVisibility(constraint : Boolean): Int {
        return if (constraint) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}
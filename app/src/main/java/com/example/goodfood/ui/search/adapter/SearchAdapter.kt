package com.example.goodfood.ui.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RatingBar
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.goodfood.R
import com.example.goodfood.model.search.Result
import com.example.goodfood.untils.NetworkState
import com.squareup.picasso.Picasso

class SearchAdapter:PagedListAdapter<Result, RecyclerView.ViewHolder>(COMPARATOR) {

    private var networkState: NetworkState? = null
    private var listener:SearchItemSelected? = null

    fun setListener(listener: SearchItemSelected){
        this.listener = listener
    }

    fun setNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount -1){
            R.layout.search_item_network_state
        }else{
            R.layout.search_item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            R.layout.search_item -> SearchViewHolder.create(parent)
            R.layout.search_item_network_state -> SearchNetworkStateViewHolder.create(parent)
            else -> throw IllegalStateException("unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)){
            R.layout.search_item -> (holder as SearchViewHolder).bind(getItem(position), listener)
            R.layout.search_item_network_state -> (holder as SearchNetworkStateViewHolder).bind(networkState)
        }
    }


    class SearchViewHolder(private val view:View):RecyclerView.ViewHolder(view){

        private val title:TextView = view.findViewById(R.id.search_item_title)
        private val sourceName:TextView = view.findViewById(R.id.search_item_source_name)
        private val rating:RatingBar = view.findViewById(R.id.search_item_aggregateLikes)
        private val img:ImageView = view.findViewById(R.id.search_item_img)

        fun bind(data:Result?, listener: SearchItemSelected?){
            view.setOnClickListener {
                listener?.onSearchItemSelected(data!!.id)
            }
            title.text = data?.title
            Picasso.get()
                .load("https://spoonacular.com/recipeImages/${data?.image}")
                .into(img)
        }

        companion object{
            fun create(parent: ViewGroup):SearchViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                return SearchViewHolder(inflater.inflate(R.layout.search_item, parent, false))
            }
        }
    }

    class SearchNetworkStateViewHolder(view:View):RecyclerView.ViewHolder(view){

        private val errorMessage:TextView = view.findViewById(R.id.search_network_error_msg)
        private val progress:ProgressBar = view.findViewById(R.id.search_network_progress)

        fun bind(networkState: NetworkState?){
            progress.visibility = toVisibility(networkState is NetworkState.LOADING)
            errorMessage.visibility = toVisibility(networkState is NetworkState.ERROR)
            errorMessage.text = if (networkState is NetworkState.ERROR) networkState.msg else ""
        }

        private fun toVisibility(constraint : Boolean): Int {
            return if (constraint) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        companion object{
            fun create(parent: ViewGroup):SearchNetworkStateViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                return SearchNetworkStateViewHolder(inflater.inflate(R.layout.search_item_network_state, parent, false))
            }
        }
    }

    companion object{
        val COMPARATOR = object :DiffUtil.ItemCallback<Result>(){
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem == newItem
            }
        }
    }
}
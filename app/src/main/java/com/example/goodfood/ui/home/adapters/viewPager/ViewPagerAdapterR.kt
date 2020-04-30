package com.example.goodfood.ui.home.adapters.viewPager

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.example.goodfood.R
import com.example.goodfood.model.recipe.Recipe
import com.squareup.picasso.Picasso

class ViewPagerAdapterR
    (private val context: Context):PagerAdapter() {

    private var dataList:List<Recipe> = emptyList()

    fun setDataList(list: List<Recipe>){
        this.dataList = list
        notifyDataSetChanged()
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return dataList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val v = LayoutInflater.from(context).inflate(R.layout.home_view_pager_item, container, false)
        val img = v.findViewById<ImageView>(R.id.vp_item_img)
        val text = v.findViewById<TextView>(R.id.vp_item_title)

        Picasso.get()
            .load(dataList[position].image)
            .placeholder(R.drawable.placeholder)
            .into(img)
        text.text = dataList[position].title

        container.addView(v)
        return v
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}
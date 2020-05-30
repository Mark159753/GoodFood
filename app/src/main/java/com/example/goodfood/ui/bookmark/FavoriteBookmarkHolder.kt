package com.example.goodfood.ui.bookmark

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.goodfood.R
import com.example.goodfood.data.local.entitys.RecipeEntity
import com.squareup.picasso.Picasso

class FavoriteBookmarkHolder(view:View):RecyclerView.ViewHolder(view) {
    private val img:ImageView = view.findViewById(R.id.favorite_img)
    private val title:TextView = view.findViewById(R.id.favorite_title)
    private val sourceName:TextView = view.findViewById(R.id.favorite_source_name)

    fun bind(item:RecipeEntity?){
        item?.let { recipeEntity ->
            Picasso.get()
                .load(recipeEntity.image)
                .into(img)
            title.text = recipeEntity.title
            sourceName.text = recipeEntity.sourceName
        }
    }
}
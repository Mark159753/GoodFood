package com.example.goodfood.ui.details.adpter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.goodfood.R
import com.example.goodfood.model.recipe.ExtendedIngredient
import com.example.goodfood.model.recipe.Step
import com.example.goodfood.ui.details.fragments.DirectionsFragment
import com.example.goodfood.ui.details.fragments.IngredientsFragment

class VpAdapter(private val context: Context,
                fragmentManager: FragmentManager):FragmentPagerAdapter(fragmentManager) {

    private var ingredientsList:List<ExtendedIngredient> = emptyList()
    private var stepsList:ArrayList<Step> = ArrayList()

    fun setData(ingredientsList: List<ExtendedIngredient>, step: List<Step>){
        this.ingredientsList = ingredientsList
        if (step.isNotEmpty())
            this.stepsList = step as ArrayList<Step>
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> IngredientsFragment.newInstance(ingredientsList as ArrayList<ExtendedIngredient>)
            1 -> DirectionsFragment.newInstance(stepsList)
            else -> throw IllegalStateException("Out of range")
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> context.resources.getString(R.string.details_ingredients)
            1 -> context.resources.getString(R.string.details_directions)
            else -> null
        }
    }

}
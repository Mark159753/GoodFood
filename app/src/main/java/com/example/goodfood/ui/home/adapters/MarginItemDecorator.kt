package com.example.goodfood.ui.home.adapters

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.goodfood.R

class MarginItemDecorator(
    private val verticalSpace:Int,
    private val horizontalSpace:Int
):RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        if (parent.getChildAdapterPosition(view) == 0){
            outRect.set(horizontalSpace *2, verticalSpace, horizontalSpace *2, verticalSpace)
        }else if (parent.getChildAdapterPosition(view) == parent.childCount -1){
            outRect.set(horizontalSpace *2, verticalSpace, horizontalSpace *2, verticalSpace)
        }else{
            outRect.set(horizontalSpace, verticalSpace, horizontalSpace, verticalSpace)
        }
    }

}
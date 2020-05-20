package com.example.goodfood.binding

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.text.Html
import android.text.Spannable
import android.text.TextPaint
import android.text.style.URLSpan
import android.text.style.UnderlineSpan
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.goodfood.R
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.internal.ViewUtils.dpToPx
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import jp.wasabeef.blurry.Blurry
import java.lang.Exception

@BindingAdapter("app:loadImg")
fun loadImg(view:ImageView, loadImg:String?){
    Picasso.get()
        .load(loadImg)
        .into(view)
}

@BindingAdapter("app:htmlText")
fun htmlText(view:TextView, text:String){
    val s = (Html.fromHtml(text)) as Spannable
    for (u in s.getSpans(0, s.length, URLSpan::class.java)){
        s.setSpan(object :UnderlineSpan(){
            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = false
            }
        }, s.getSpanStart(u), s.getSpanEnd(u), 0)
    }
    view.text = s
}
package com.example.goodfood.binding

import android.text.Html
import android.text.Spannable
import android.text.TextPaint
import android.text.style.URLSpan
import android.text.style.UnderlineSpan
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("app:loadImg")
fun loadImg(view:ImageView, loadImg:String?){
    Picasso.get()
        .load(loadImg)
        .into(view)
}

@BindingAdapter("app:htmlText")
fun htmlText(view:TextView, text:String?){
    if (text != null) {
        val s = (Html.fromHtml(text)) as Spannable
        for (u in s.getSpans(0, s.length, URLSpan::class.java)) {
            s.setSpan(object : UnderlineSpan() {
                override fun updateDrawState(ds: TextPaint) {
                    ds.isUnderlineText = false
                }
            }, s.getSpanStart(u), s.getSpanEnd(u), 0)
        }
        view.text = s
    }
}
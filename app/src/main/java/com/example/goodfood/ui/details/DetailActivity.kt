package com.example.goodfood.ui.details

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnAttach
import androidx.core.view.doOnLayout
import androidx.core.view.doOnPreDraw
import androidx.core.view.drawToBitmap
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navArgs
import com.example.goodfood.GoodFoodApp
import com.example.goodfood.R
import com.example.goodfood.databinding.ActivityDetailBinding
import com.example.goodfood.di.ViewModelFactoryDI
import com.example.goodfood.model.recipe.ExtendedIngredient
import com.example.goodfood.model.recipe.Step
import com.example.goodfood.ui.details.adpter.VpAdapter
import com.google.android.material.appbar.AppBarLayout
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import jp.wasabeef.blurry.Blurry
import java.lang.Exception
import javax.inject.Inject


class DetailActivity : AppCompatActivity() {

    private val args:DetailActivityArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: ViewModelFactoryDI
    private lateinit var viewModel: DetailsViewModel

    private lateinit var binder:ActivityDetailBinding

    private val targetBlurImg = object: Target{
        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {}

        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
            binder.detailAppbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                val height = binder.detailAppbar.height  - binder.detailToolbar.height - dpToPx(24f)
                val percentage = Math.abs(verticalOffset) / height
                val radius = (15 * percentage).toInt()
                if (radius > 0){
                    Blurry.with(applicationContext)
                        .async()
                        .radius(radius)
                        .from(bitmap)
                        .into(binder.detailMainImg)
                }else{
                    binder.detailMainImg.setImageBitmap(bitmap)
                }
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as GoodFoodApp).appComponent.getFragmentComponent().create().inject(this)
        binder = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        args.recipe?.let {
            binder.recipe = it
            initBlur(it.image)
            if (it.extendedIngredients != null && it.analyzedInstructions != null)
                initViewPager(it.extendedIngredients, if (it.analyzedInstructions.isEmpty()) emptyList()
                        else it.analyzedInstructions[0].steps)
        }
        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailsViewModel::class.java)

        initToolbar()
    }

    private fun initViewPager(listIngredients:List<ExtendedIngredient>, steps:List<Step>){
        val mAdapter = VpAdapter(applicationContext, supportFragmentManager)
        mAdapter.setData(listIngredients, steps)
        binder.detailViewpager.apply {
            adapter = mAdapter
        }
        binder.detailTabs.setupWithViewPager(binder.detailViewpager)
    }

    private fun initToolbar(){
        binder.detailToolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp)
        binder.detailToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initBlur(url:String?){
        Picasso.get()
            .load(url)
            .into(targetBlurImg)
    }


    private fun dpToPx(dp:Float) =
        resources.displayMetrics.density * dp

    override fun onDestroy() {
        Picasso.get().cancelRequest(targetBlurImg)
        super.onDestroy()
    }

}

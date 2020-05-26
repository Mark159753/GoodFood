package com.example.goodfood.ui.details

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navArgs
import com.example.goodfood.GoodFoodApp
import com.example.goodfood.R
import com.example.goodfood.data.local.entitys.RecipeEntity
import com.example.goodfood.databinding.ActivityDetailBinding
import com.example.goodfood.di.ViewModelFactoryDI
import com.example.goodfood.model.recipe.ExtendedIngredient
import com.example.goodfood.model.recipe.Step
import com.example.goodfood.ui.base.BaseActivity
import com.example.goodfood.ui.details.adpter.VpAdapter
import com.example.goodfood.untils.Status
import com.google.android.material.appbar.AppBarLayout
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import jp.wasabeef.blurry.Blurry
import java.lang.Exception
import javax.inject.Inject


class DetailActivity : BaseActivity() {

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

        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailsViewModel::class.java)
        checksArgs()

        initToolbar()

        binder.detailAddToFavBtn.setOnClickListener {
            makeToast("Add Recipe to Favorite")
        }
    }

    private fun checksArgs(){
        when {
            args.recipe != null -> {
                val recipe = args.recipe!!
                setRecipe(recipe)
            }
            args.recipeId != -1 -> {
                viewModel.getRecipeById(args.recipeId)
                subscribeOnLoading()
            }
            else -> {
                showErrorDialog(resources.getString(R.string.bad_request))
            }
        }
    }

    private fun setRecipe(recipe:RecipeEntity?){
        if (recipe == null) showErrorDialog(resources.getString(R.string.empty_result))
        binder.recipe = recipe
        initBlur(recipe?.image)
        if (recipe?.extendedIngredients != null && recipe.analyzedInstructions != null)
            initViewPager(recipe.extendedIngredients, if (recipe.analyzedInstructions.isEmpty()) emptyList()
            else recipe.analyzedInstructions[0].steps)
    }

    private fun subscribeOnLoading(){
        viewModel.recipe.observe(this, Observer {
            when (it.status){
                is Status.SUCCESS -> {
                    showHideProgressBar(false)
                    setRecipe(it.data)
                }
                is Status.LOADING -> { showHideProgressBar(true) }
                is Status.ERROR -> {
                    showHideProgressBar(false)
                    showErrorDialog(resources.getString(R.string.we_got_a_problem))
                }
            }
        })
    }

    private fun showHideProgressBar(isLoading:Boolean){
        if (isLoading)
            binder.detailLoadingProgress.visibility = View.VISIBLE
        else
            binder.detailLoadingProgress.visibility = View.GONE
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

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
import com.example.goodfood.untils.FirestoreHelper
import com.example.goodfood.untils.Status
import com.google.android.material.appbar.AppBarLayout
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import jp.wasabeef.blurry.Blurry
import java.lang.Exception
import javax.inject.Inject


class DetailActivity : BaseActivity(), EventListener<QuerySnapshot> {

    private val args:DetailActivityArgs by navArgs()

    private var isFav = false

    @Inject
    lateinit var viewModelFactory: ViewModelFactoryDI
    private lateinit var viewModel: DetailsViewModel

    @Inject
    lateinit var firestoreHelper: FirestoreHelper

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
        firestoreHelper.subscribeOnRecipesDb(this)

        binder.detailAddToFavBtn.setOnClickListener {
        val recipe = binder.recipe
            if (isFav){
                firestoreHelper.removeFromFirestore(recipe!!)
            }else{
                firestoreHelper.addToFirestore(recipe!!)
            }
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
        initFavBtn(recipe)
        binder.recipe = recipe
        initBlur(recipe?.image)
        if (recipe?.extendedIngredients != null && recipe.analyzedInstructions != null)
            initViewPager(
                recipe.extendedIngredients,
                if (recipe.analyzedInstructions.isEmpty()) emptyList()
                else recipe.analyzedInstructions[0].steps
            )
    }

    private fun initFavBtn(recipe: RecipeEntity?){
        recipe?.let { recipeEntity ->
            firestoreHelper.isFavorite(recipeEntity){fav ->
                isFav = fav
                if (fav)
                    binder.detailAddToFavBtn.text = resources.getText(R.string.remove_from_fav)
                else
                    binder.detailAddToFavBtn.text = resources.getText(R.string.add_to_fav_btn)
            }
        }
    }

    private var isInitEvent:Boolean = false

    override fun onEvent(snapShot: QuerySnapshot?, e: FirebaseFirestoreException?) {
        if (e != null){
            Log.e("FavoriteAdapter", e.message ?: "UnknownError")
            return
        }
        if (isInitEvent){
            when (snapShot!!.documentChanges[0].type) {
                DocumentChange.Type.ADDED -> {
                    binder.detailAddToFavBtn.text = resources.getText(R.string.remove_from_fav)
                    isFav = true
                    makeToast(resources.getString(R.string.recipe_added_msg))
                }
                DocumentChange.Type.REMOVED -> {
                    binder.detailAddToFavBtn.text = resources.getText(R.string.add_to_fav_btn)
                    isFav = false
                    makeToast(resources.getString(R.string.recipe_removed_msg))
                }
            }
        }
        // Bad solution using flag, need to find better
        isInitEvent = true
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
        firestoreHelper.unsubscribeFromRecipeDb()
        super.onDestroy()
    }

}

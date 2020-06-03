package com.example.goodfood.ui.bookmark

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.RecyclerView
import com.example.goodfood.R
import com.example.goodfood.data.local.entitys.RecipeEntity
import com.example.goodfood.untils.FirestoreHelper
import com.example.goodfood.untils.ItemSelectedListener
import com.google.firebase.firestore.*

class FavoriteAdapter(private val lifecycleOwner: LifecycleOwner, private val firestoreHelper: FirestoreHelper)
    : RecyclerView.Adapter<FavoriteBookmarkHolder>(), EventListener<QuerySnapshot>, LifecycleObserver {

    private val listData = ArrayList<RecipeEntity>()
    private var listener:ItemSelectedListener? = null

    private var emptyListener: ((Boolean) -> Unit)? = null

    init {
        lifecycleOwner.lifecycle.addObserver(this)
        firestoreHelper.subscribeOnRecipesDb(this)
    }

    fun setEmptyListener(listener: (isEmpty:Boolean) -> Unit){
        this.emptyListener = listener
    }

    fun setItemClickListener(listener: ItemSelectedListener){
        this.listener = listener
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stopListening(){
        firestoreHelper.unsubscribeFromRecipeDb()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun removeLifecycleObserver(){
        lifecycleOwner.lifecycle.removeObserver(this)
    }

    fun deleteItem(position: Int){
        firestoreHelper.removeFromFirestore(listData[position])
    }

    override fun onEvent(snapshot: QuerySnapshot?, e: FirebaseFirestoreException?) {
        if (e != null){
            Log.e("FavoriteAdapter", e.message ?: "UnknownError")
        }
        for (dc in snapshot!!.documentChanges){
            when(dc.type){
                DocumentChange.Type.ADDED -> { addNewItem(dc.document.toObject(RecipeEntity::class.java))}
                DocumentChange.Type.MODIFIED -> { }
                DocumentChange.Type.REMOVED -> { removeItem(dc.document.toObject(RecipeEntity::class.java))}
            }
        }
        if (snapshot.isEmpty) emptyListener?.invoke(true)
        Log.e("DOCUMENTS_SIZE", snapshot.documents.size.toString())
    }

    private fun addNewItem(recipeEntity: RecipeEntity){
        listData.add(recipeEntity)
        notifyItemInserted(listData.indexOf(recipeEntity))
    }

    private fun removeItem(recipeEntity: RecipeEntity){
        val position = listData.indexOf(recipeEntity)
        listData.remove(recipeEntity)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteBookmarkHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.favorite_item, parent, false)
        return FavoriteBookmarkHolder(v)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: FavoriteBookmarkHolder, position: Int) {
        val item = listData[position]
        holder.bind(item, listener)
    }

}
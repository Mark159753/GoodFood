package com.example.goodfood.untils

import android.util.Log
import com.example.goodfood.data.local.entitys.RecipeEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class FirestoreHelper(private val auth: FirebaseAuth) {

    private val db = Firebase.firestore

    private var listenerRegistrar:ListenerRegistration? = null


    fun subscribeOnRecipesDb(listener: EventListener<QuerySnapshot>){
        listenerRegistrar = db.collection(auth.uid!!).addSnapshotListener(listener)
    }

    fun unsubscribeFromRecipeDb(){
        listenerRegistrar?.remove()
    }

    fun addToFirestore(recipe: RecipeEntity) {
        db.collection(auth.uid!!)
            .document(recipe.id.toString())
            .set(recipe)
            .addOnSuccessListener { Log.d("FireStoreHelper", "Recipe was Added")}
            .addOnFailureListener {
                Log.e("ERROR", it.message ?: "FUCK")
            }
    }

    fun removeFromFirestore(recipe: RecipeEntity) {
        db.collection(auth.uid!!)
            .document(recipe.id.toString())
            .delete()
            .addOnSuccessListener { Log.d("FireStoreHelper", "Recipe was Removed") }
            .addOnFailureListener { e ->
                Log.e("DELETE_ERROR", e.message ?: "Unknown Error")
            }
    }

    fun isFavorite(recipe:RecipeEntity, block: (isFavorite:Boolean) -> Unit) {
        db.collection(auth.uid!!)
            .document(recipe.id.toString())
            .get(Source.CACHE)
            .addOnSuccessListener { doc ->
                block(doc.exists())
            }
            .addOnFailureListener {
                Log.e("ERROR", "SORRY")
            }
    }

    fun getFavoriteQuery(): Query {
        return db.collection(auth.uid!!)
    }

    fun getRecipesFromFavorite(callback: (recipes:List<RecipeEntity>?) -> Unit){
        db.collection(auth.uid!!)
            .get()
            .addOnSuccessListener { result ->
                val recipes = ArrayList<RecipeEntity>()
                for (document in result) {
                    recipes.add(document.toObject())
                }
                callback(recipes)
            }
            .addOnFailureListener { exception ->
                Log.e("FAVORITE", "Error getting documents.", exception)
                callback(null)
            }
    }

}
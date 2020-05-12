package com.example.goodfood.data.local.entitys

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.goodfood.model.recipe.AnalyzedInstruction
import com.example.goodfood.model.recipe.ExtendedIngredient
import com.example.goodfood.model.recipe.WinePairing

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val aggregateLikes: Int,
    val analyzedInstructions: List<AnalyzedInstruction>?,
    val cheap: Boolean,
    val creditsText: String?,
    val cuisines: List<String>?,
    val dairyFree: Boolean,
    val diets: List<String>?,
    val dishTypes: List<String>?,
    val extendedIngredients: List<ExtendedIngredient>?,
    val gaps: String?,
    val glutenFree: Boolean,
    val healthScore: Double?,
    val image: String?,
    val imageType: String?,
    val instructions: String?,
    val license: String?,
    val lowFodmap: Boolean,
    val occasions: List<String>?,
    val originalId: Int,
    val pricePerServing: Double,
    val readyInMinutes: Int,
    val servings: Int,
    val sourceName: String?,
    val sourceUrl: String?,
    val spoonacularScore: Double?,
    val spoonacularSourceUrl: String?,
    val summary: String?,
    val sustainable: Boolean,
    val title: String?,
    val vegan: Boolean,
    val vegetarian: Boolean,
    val veryHealthy: Boolean,
    val veryPopular: Boolean,
    val weightWatcherSmartPoints: Int,
    @Embedded(prefix = "wine_pairing_")
    val winePairing: WinePairing?
){
    var typeRequest:String? = null
}
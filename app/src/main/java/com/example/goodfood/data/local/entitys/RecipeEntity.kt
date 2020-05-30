package com.example.goodfood.data.local.entitys

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.goodfood.model.recipe.AnalyzedInstruction
import com.example.goodfood.model.recipe.ExtendedIngredient
import com.example.goodfood.model.recipe.WinePairing
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "recipes")
@Parcelize
data class RecipeEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val aggregateLikes: Int = 0,
    val analyzedInstructions: List<AnalyzedInstruction>? = null,
    val cheap: Boolean = false,
    val creditsText: String? = null,
    val cuisines: List<String>? = null,
    val dairyFree: Boolean = false,
    val diets: List<String>? = null,
    val dishTypes: List<String>? = null,
    val extendedIngredients: List<ExtendedIngredient>? = null,
    val gaps: String? = null,
    val glutenFree: Boolean = false,
    val healthScore: Double? = null,
    val image: String? = null,
    val imageType: String? = null,
    val instructions: String? = null,
    val license: String? = null,
    val lowFodmap: Boolean = false,
    val occasions: List<String>? = null,
    val originalId: Int = 0,
    val pricePerServing: Double = 0.0,
    val readyInMinutes: Int = 0,
    val servings: Int = 0,
    val sourceName: String? = null,
    val sourceUrl: String? = null,
    val spoonacularScore: Double? = null,
    val spoonacularSourceUrl: String? = null,
    val summary: String? = null,
    val sustainable: Boolean = false,
    val title: String? = null,
    val vegan: Boolean = false,
    val vegetarian: Boolean = false,
    val veryHealthy: Boolean = false,
    val veryPopular: Boolean = false,
    val weightWatcherSmartPoints: Int = 0,
    @Embedded(prefix = "wine_pairing_")
    val winePairing: WinePairing? = null
):Parcelable{
    @IgnoredOnParcel
    var typeRequest:String? = null
}
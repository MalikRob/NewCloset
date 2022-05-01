package com.example.allin.model

import android.os.Parcelable
import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * This class is going to begin with a finite number of clothing items.
 * The constructor will require an [outfitName], for each [Outfit] item.
 *
 * The [Parcelize] is a Kotlin annnotation that allows us to bind the data from each Entity item stored
 * while navigating through pages.
 */
@Parcelize
@Entity
data class Outfit constructor (

    @PrimaryKey(autoGenerate = true) val id: Int,
    var outfitName: String,

): Parcelable {
    @Ignore var clothingItem: List<Clothing>? = null
}

// With the Ignore Statement, make a call to add a new outfit that only takes in the Outfitname.
// Next, create the Outfit, then use the addOutfit(outfit: Outfit, clothing: List<Clothing>),
// To add clothing values to that list as well.

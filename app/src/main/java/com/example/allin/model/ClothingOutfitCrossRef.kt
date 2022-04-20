package com.example.allin.model

import androidx.room.Entity
import androidx.room.ForeignKey

/**
 * This class is unused still. Trying to find the correct way to implement it.
 *
 * It is a Relational Class required for the two relational tables:
 *  1.  ClothingWithOutfits
 *  2.  OutfitsWithClothing
 *
 */
@Entity(primaryKeys = ["clothingID", "outfitID"],
    foreignKeys =
    [
        ForeignKey(entity = Clothing::class,
            parentColumns = ["id"],
            childColumns = ["clothingID"]),

        ForeignKey(entity = Outfit::class,
            parentColumns = ["id"],
            childColumns = ["outfitID"])
    ]
)
data class ClothingOutfitCrossRef(
    val clothingID: Int,
    val outfitID: Int
)
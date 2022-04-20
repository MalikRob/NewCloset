package com.example.allin.data.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.allin.model.Clothing
import com.example.allin.model.ClothingOutfitCrossRef
import com.example.allin.model.Outfit

/**
 * Not in Use yet. Example of when this class will be needed.
 * For Many to Many relationships this class will define a List of Rows that meet
 * the requirements of the Embedded Clothing Item.
 *
 * Displays a list of Clothes that share the same OutfitID.
 *
 * Future Reason:
 * It will display a list of clothing items with the associated OutfitID.
 * For our Outfit List. When we select the Outfit we want, we should aim to
 * Grab this list to see each Clothing Item that is apart of it.
 */

data class OutfitsWithClothing(
    @Embedded val Outfit: Outfit,
    @Relation(
        parentColumn = "outfitID",
        entityColumn = "clothingID",
        associateBy = Junction(ClothingOutfitCrossRef::class)
    )
    val clothes: List<Clothing>
)
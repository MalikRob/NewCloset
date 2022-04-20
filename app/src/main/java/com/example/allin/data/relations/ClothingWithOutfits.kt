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
 * It will then display a list of Outfits That have the associated ClothingID
 * from the Embedded value.
 *
 * Future Reason. Each Clothing Item should have a list of Outfits they appear in.
 * That way the user can view them.
 */

data class ClothingWithOutfits(
    @Embedded val clothing: Clothing,
    @Relation(
        parentColumn = "clothingID",
        entityColumn = "outfitID",
        associateBy = Junction(ClothingOutfitCrossRef::class)
    )
    val outfits: List<Outfit>
)
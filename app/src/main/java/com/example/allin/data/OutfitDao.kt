package com.example.allin.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.allin.model.Clothing
import com.example.allin.model.Outfit

@Dao
interface OutfitDao {

    // Grabs data from Outfit Table, necessary for each other Query to read
    // from in the Outfit Repository class
    /**
     * @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun addOutfit(outfit: Outfit)
     */

    //Create the Outfit First, then bring the users to a new page to insert the list of clothing items.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addOutfit(outfit: Outfit)

    //This should, insert an Outfit with Matching Clothing Items into the DB.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addOutfit(outfit: Outfit, clothes: List<Clothing>)

    @Query("SELECT * FROM Outfit ORDER BY id ASC")
    fun readAllData(): LiveData<List<Outfit>>

}
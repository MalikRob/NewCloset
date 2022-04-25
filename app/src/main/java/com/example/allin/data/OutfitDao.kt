package com.example.allin.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.allin.model.Outfit

@Dao
interface OutfitDao {

    // Grabs data from Outfit Table, necessary for each other Query to read
    // from in the Outfit Repository class

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addOutfit(outfit: Outfit)


    @Query("SELECT * FROM Outfit ORDER BY id ASC")
    fun readAllData(): LiveData<List<Outfit>>


}
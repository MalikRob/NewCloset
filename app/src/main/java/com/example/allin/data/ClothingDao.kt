package com.example.allin.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.allin.model.Clothing

@Dao
interface ClothingDao {

    //Ignores when the exact same data is put in
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addClothing(clothing: Clothing)

    @Update
    suspend fun updateClothing(clothing: Clothing)

    @Query("SELECT * FROM clothing_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Clothing>>
}
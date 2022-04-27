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

    @Delete
    suspend fun deleteClothing(clothing: Clothing)

    @Query("DELETE FROM Clothing")
    suspend fun deleteAllClothing()

    @Query("SELECT * FROM Clothing ORDER BY id ASC")
    fun readAllData(): LiveData<List<Clothing>>

    @Query("SELECT * FROM Clothing WHERE type='Top' ORDER BY id ASC")
    fun selectClothingTops(): LiveData<List<Clothing>>

    //Called in ListFragment Searchbar. Queries Clothing Type or Clothing Color.
    @Query("SELECT * FROM Clothing WHERE type LIKE :searchQuery OR color LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): LiveData<List<Clothing>>

}


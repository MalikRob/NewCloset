package com.example.allin.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.allin.data.relations.ClothingWithOutfitsList
import com.example.allin.data.relations.OutfitsWithClothingList
import com.example.allin.model.Clothing
import com.example.allin.model.Outfit
import com.example.allin.model.OutfitClothingTable

@Dao
interface ClosetDao {

    @Query("SELECT * FROM Clothing ORDER BY clothingId ASC")
    fun readAllClothingData(): LiveData<List<Clothing>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addClothing(clothing: Clothing): Long

    @Update
    suspend fun updateClothing(clothing: Clothing)

    @Delete
    suspend fun deleteClothing(clothing: Clothing)

    @Query("DELETE FROM Clothing")
    suspend fun deleteAllClothing()

    @Query("SELECT * FROM Clothing WHERE type='Top' ORDER BY clothingId ASC")
    fun selectClothingTops(): LiveData<List<Clothing>>

    @Query("SELECT * FROM Clothing WHERE type='Bottom' ORDER BY clothingId ASC")
    fun selectClothingBottoms(): LiveData<List<Clothing>>

    @Query("SELECT * FROM Clothing WHERE type='Shoes' ORDER BY clothingId ASC")
    fun selectClothingShoes(): LiveData<List<Clothing>>

    @Query("SELECT * FROM Clothing WHERE type='OuterWear' ORDER BY clothingId ASC")
    fun selectClothingOuterWear(): LiveData<List<Clothing>>

    //Called in ListFragment Searchbar. Queries Clothing Type or Clothing Color.
    @Query("SELECT * FROM Clothing WHERE type LIKE :searchQuery OR color LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): LiveData<List<Clothing>>

    /**
     * Outfit DAO section
     */

    //Create the Outfit First, then bring the users to a new page to insert the list of clothing items.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addOutfit(outfit: Outfit): Long

    @Query("SELECT * FROM Outfit ORDER BY outfitId ASC")
    fun readAllOutfitData(): LiveData<List<Outfit>>

    @Query("SELECT * FROM OUTFIT WHERE outfitName = :name ORDER BY outfitId DESC LIMIT 1")
    fun getOutfit(name: String): Outfit

    /**
     * Insert Clothing into Outfits using New M-M relation table [OutfitClothingTable].
     */

    /* "Long" value not of much use other than if 1 or greater insert, if -1 not inserted */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addOutfitClothingMap(outfitClothingTable: OutfitClothingTable): Long

    @Transaction
    @Query("SELECT * FROM outfit")
    fun getAllOutfitsWithClothingList(): LiveData<List<OutfitsWithClothingList>>

    @Transaction
    @Query("SELECT * FROM clothing")
    fun getAllClothingWithOutfitList(): LiveData<List<ClothingWithOutfitsList>>

}


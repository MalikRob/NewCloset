package com.example.allin.repository

import androidx.lifecycle.LiveData
import com.example.allin.data.ClosetDao
import com.example.allin.data.relations.ClothingWithOutfitsList
import com.example.allin.data.relations.OutfitsWithClothingList
import com.example.allin.model.Clothing
import com.example.allin.model.Outfit
import com.example.allin.model.OutfitClothingTable
import com.example.allin.model.Packing

class ClosetRepository(private val closetDao: ClosetDao) {

    val readAllClothingData: LiveData<List<Clothing>> = closetDao.readAllClothingData()
    val readAllOutfitData: LiveData<List<Outfit>> = closetDao.readAllOutfitData()
    //val getAllOutfitWithClothingList: LiveData<List<OutfitsWithClothingList>> = closetDao.getAllOutfitsWithClothingList()
    val getAllClothingWitOutfitList: LiveData<List<ClothingWithOutfitsList>> = closetDao.getAllClothingWithOutfitList()

    suspend fun addClothing(clothing: Clothing){
        closetDao.addClothing(clothing)
    }

    suspend fun updateClothing(clothing: Clothing) {
        closetDao.updateClothing(clothing)
    }

    suspend fun deleteClothing(clothing: Clothing){
        closetDao.deleteClothing(clothing)
    }

    suspend fun deleteALLClothing() {
        closetDao.deleteAllClothing()
    }

    fun searchDatabase(searchQuery: String): LiveData<List<Clothing>> {
        return closetDao.searchDatabase(searchQuery)
    }

    fun selectAllTops(theme: String): LiveData<List<Clothing>>{
        return closetDao.selectClothingTops(theme)
    }

    fun selectAllBottoms(theme: String): LiveData<List<Clothing>>{
        return closetDao.selectClothingBottoms(theme)
    }

    fun selectAllShoes(theme: String): LiveData<List<Clothing>>{
        return closetDao.selectClothingShoes(theme)
    }

    fun selectAllOuterWear(theme: String): LiveData<List<Clothing>>{
        return closetDao.selectClothingOuterWear(theme)
    }

    /**
     * Outfit Calls are here
     */

    suspend fun addOutfit(outfit: Outfit){
        closetDao.addOutfit(outfit)
    }

    fun getOutfit(name: String): Outfit{
        return closetDao.getOutfit(name)
    }

    suspend fun deleteOutfit(outfitId: Long){
        closetDao.deleteOutfit(outfitId)
    }

    //New Update
    suspend fun updateOutfit(outfit: Outfit) {
        closetDao.updateOutfit(outfit)
    }

    /**
     * Outfit + Class Table calls here
     */

    suspend fun addOutfitWithClothingMap(outfitClothingTable: OutfitClothingTable) {
        closetDao.addOutfitClothingMap(outfitClothingTable)
    }

    fun getOutfitWithClothingList(outfitId: Long): LiveData<List<OutfitsWithClothingList>> {
        return closetDao.getAllOutfitsWithClothingList(outfitId)
    }

    /**
     * Packing Table calls here
     */

    val readAllPackingData: LiveData<List<Packing>> = closetDao.readAllPackingData()

    suspend fun addNewPackingList(packing: Packing){
        closetDao.addNewPackingList(packing)
    }


}
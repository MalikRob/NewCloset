package com.example.allin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.allin.data.ClothingDatabase
import com.example.allin.data.relations.OutfitsWithClothingList
import com.example.allin.repository.ClosetRepository
import com.example.allin.model.Clothing
import com.example.allin.model.Outfit
import com.example.allin.model.OutfitClothingTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//Communication between the repository and the UI
class ClosetViewModel(application: Application): AndroidViewModel(application) {
    //Initialize any lists that the functions below init() can use
    val readAllClothingData: LiveData<List<Clothing>>
    val readAllOutfitData: LiveData<List<Outfit>>
    //val getAllOutfitWithClothingList: LiveData<List<OutfitsWithClothingList>>
    private val repository: ClosetRepository

    init {
        val clothingDao = ClothingDatabase.getDatabase(application).getClosetDao()
        repository = ClosetRepository(clothingDao)
        readAllClothingData = repository.readAllClothingData
        readAllOutfitData = repository.readAllOutfitData
        //getAllOutfitWithClothingList = repository.getAllOutfitWithClothingList
    }

    fun addClothing(clothing: Clothing){
        viewModelScope.launch(Dispatchers.IO){
            repository.addClothing(clothing)
        }
    }

    fun updateClothing(clothing: Clothing){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateClothing(clothing)
        }
    }

    fun deleteClothing(clothing: Clothing){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteClothing(clothing)
        }
    }

    fun deleteAllClothing(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteALLClothing()
        }
    }

    fun searchDatabase(searchQuery: String): LiveData<List<Clothing>> {
        return repository.searchDatabase(searchQuery)
    }

    fun selectAllTops(): LiveData<List<Clothing>>{
        return repository.selectAllTops()
    }

    fun selecAllBottoms(): LiveData<List<Clothing>>{
        return repository.selectAllBottoms()
    }

    fun selectAllShoes(): LiveData<List<Clothing>>{
        return repository.selectAllShoes()
    }

    fun selectAllOuterWear(): LiveData<List<Clothing>>{
        return repository.selectAllOuterWear()
    }

    /**
     * Outfit Calls are here
     */

    fun addOutfit(outfit: Outfit){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addOutfit(outfit)
        }
    }

    fun getOutfit(name: String): Outfit{
        return repository.getOutfit(name)
    }

    /**
     * Outfit With Clothing calls are here
     */

    fun addOutfitWithClothingMap(outfitWithClothingTable: OutfitClothingTable){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addOutfitWithClothingMap(outfitWithClothingTable)
        }
    }

    fun getAllOutfitsWithClothing(outfitId: Long): LiveData<List<OutfitsWithClothingList>>{
           return repository.getOutfitWithClothingList(outfitId)
    }

}
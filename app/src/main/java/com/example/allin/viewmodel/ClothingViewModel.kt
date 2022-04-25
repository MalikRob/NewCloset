package com.example.allin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.allin.data.ClothingDatabase
import com.example.allin.repository.ClothingRepository
import com.example.allin.model.Clothing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//Communication between the repository and the UI
class ClothingViewModel(application: Application): AndroidViewModel(application) {
    val readAllData: LiveData<List<Clothing>>
    private val repository: ClothingRepository

    init {
        val clothingDao = ClothingDatabase.getDatabase(application).clothingDao()
        repository = ClothingRepository(clothingDao)
        readAllData = repository.readAllData
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

    fun selectClothingTops():LiveData<List<Clothing>>{
        return repository.selectAllTops()
    }

}
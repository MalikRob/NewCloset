package com.example.allin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.allin.data.ClothingDatabase
import com.example.allin.model.Clothing
import com.example.allin.model.Outfit
import com.example.allin.repository.OutfitRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OutfitViewModel(application: Application): AndroidViewModel(application) {
    val readAllData: LiveData<List<Outfit>>
    private val repository: OutfitRepository

    init {
        val outfitDao = ClothingDatabase.getDatabase(application).outfitDao()
        repository = OutfitRepository(outfitDao)
        readAllData = repository.readAllData
    }

    //Creates an Instance of Outfit with parameters except clothing.
    fun addOutfit(outfit: Outfit){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addOutfit(outfit)
        }
    }

    //After the Outfit is created, we add the Outfit and Clothing List selected for the user
    // to create their new outfit
   fun addOutfit(outfit: Outfit, clothing: List<Clothing>) {
       viewModelScope.launch(Dispatchers.IO){
           repository.addOutfit(outfit, clothing)
       }
    }
}
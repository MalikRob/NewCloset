package com.example.allin.repository

import androidx.lifecycle.LiveData
import com.example.allin.data.OutfitDao
import com.example.allin.model.Clothing
import com.example.allin.model.Outfit

class OutfitRepository(private val outfitDao: OutfitDao) {

    /**
     * The repository is a "Good Practice class" required to clean up the code and
     * call these functions by them selves as a middle man instead of directly to the ViewModel
     */

    val readAllData: LiveData<List<Outfit>> = outfitDao.readAllData()

    suspend fun addOutfit(outfit: Outfit){
        outfitDao.addOutfit(outfit)
    }

    suspend fun addOutfit(outfit: Outfit, clothing: List<Clothing>) {
        outfitDao.addOutfit(outfit, clothing)
    }
}
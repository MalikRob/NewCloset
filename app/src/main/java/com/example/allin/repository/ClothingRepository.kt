package com.example.allin.repository

import androidx.lifecycle.LiveData
import com.example.allin.data.ClothingDao
import com.example.allin.model.Clothing

class ClothingRepository(private val clothingDao: ClothingDao) {

    val readAllData: LiveData<List<Clothing>> = clothingDao.readAllData()

    suspend fun addClothing(clothing: Clothing){
        clothingDao.addClothing(clothing)
    }

    suspend fun updateClothing(clothing: Clothing) {
        clothingDao.updateClothing(clothing)
    }
}
package com.example.allin.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.allin.model.Clothing
import java.util.*

@Entity(foreignKeys = [ForeignKey(entity = Clothing::class, parentColumns = ["id"],
        childColumns = ["clothingItemID"])])
data class Outfit (
    @PrimaryKey(autoGenerate = true)
    val outfitID: Int,

    val clothingItemID: Int,
    val dateAdded: Date,
    val lastWorn: Date
        )
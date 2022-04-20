package com.example.allin.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(foreignKeys = [
            ForeignKey(entity = Clothing::class,
            parentColumns = ["id"],
            childColumns = ["clothingID"],
            onDelete = CASCADE
            )
    ]
)
data class Outfit (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val clothingID: Int,
    val outfitName: String
):Parcelable
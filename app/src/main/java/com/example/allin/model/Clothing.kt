package com.example.allin.model

import android.os.Parcelable

import androidx.room.Entity
import androidx.room.ForeignKey

import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * Class member values. These values will also represent the Table "Clothing"
 * and its columns by value types
 * Parcelize allow the data to be bound to navigation arguments and transferred between
 * fragments and activities on navigation calls
 *
 */
@Parcelize
@Entity(foreignKeys = [
    ForeignKey(entity = Outfit::class,
        parentColumns = ["id"],
        childColumns = ["outfitRefFK"]
        )
    ]
)
data class Clothing constructor (
    //Sets all attributes and primary key
    @PrimaryKey(autoGenerate = true) val id: Int,
    var type: String?,
    var color: String?,
    var style: String?,
    var description: String?,
    var dateAdded: Date = Date(),
    var brand: String?,
    var theme: String?,
    var image: String?,
    var outfitRefFK: Int? = null

): Parcelable
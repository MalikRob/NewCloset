package com.example.allin.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

//Sets the table name
@Parcelize
@Entity(tableName = "clothing_table")
data class Clothing (
    //Sets all attributes and primary key
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val type: String,
    val color: String,
    val description: String
    ): Parcelable
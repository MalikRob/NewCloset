package com.example.allin.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.allin.model.Clothing

//This class serves as the main conection between our data and the app.
@Database(entities = [Clothing::class], version = 1, exportSchema = false)
@TypeConverters(DBTypeConverters::class)
abstract class ClothingDatabase: RoomDatabase() {

    abstract fun clothingDao(): ClothingDao
    abstract fun outfitDao(): OutfitDao

    //Everything within this object will be visible to other classes
    companion object{
        //Rights to this field will be made visible to other threads
        @Volatile
        //Only one instance of database will be made
        private var INSTANCE: ClothingDatabase? = null

        fun getDatabase(context: Context): ClothingDatabase{
            val tempInstance = INSTANCE

            //If the instance already exists, return the same instance
            if (tempInstance != null) {
                return tempInstance
            }
            //Creates new instance if null
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ClothingDatabase::class.java,
                    "clothing_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
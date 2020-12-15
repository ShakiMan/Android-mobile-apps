package com.example.app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BmiData::class], version = 1)
abstract class BmiDatabase : RoomDatabase() {
    abstract val bmiDatabaseDao: BmiDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: BmiDatabase? = null

        fun getInstance(context: Context): BmiDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BmiDatabase::class.java,
                        "measurements_history"
                    )
                        .allowMainThreadQueries()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
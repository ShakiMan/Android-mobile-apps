package com.example.app.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BmiDatabaseDao {

    @Insert
    fun insert(data: BmiData)

    @Query("SELECT * FROM measurements_history")
    fun getAllBmi(): List<BmiData>

    @Query("DELETE FROM measurements_history")
    fun clear()
}
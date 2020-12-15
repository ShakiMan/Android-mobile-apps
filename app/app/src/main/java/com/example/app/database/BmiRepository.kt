package com.example.app.database

import android.app.Application

class BmiRepository(application: Application){
    private var bmiDatabaseDao: BmiDatabaseDao
    private var measurements: List<BmiData>

    init {
        val database: BmiDatabase = BmiDatabase.getInstance(application)
        bmiDatabaseDao = database.bmiDatabaseDao
        measurements = bmiDatabaseDao.getAllBmi()
    }

    fun insert(bmiData: BmiData) {
        bmiDatabaseDao.insert(bmiData)
    }

    fun clear() {
        bmiDatabaseDao.clear()
    }

    fun getAllBmi(): List<BmiData> {
        return measurements
    }
}
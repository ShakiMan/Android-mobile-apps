package com.example.app.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "measurements_history")
class BmiData(
    @PrimaryKey(autoGenerate = true)
    private var id: Int = 0,

    @ColumnInfo(name = "bmi")
    var bmi: String,

    @ColumnInfo(name = "mass")
    var mass: String,

    @ColumnInfo(name = "height")
    var height: String,

    @ColumnInfo(name = "date")
    var date: String,

    @ColumnInfo(name = "european_standard")
    var europeanStandard: Boolean
) : Serializable {
    fun setId(id: Int) {
        this.id = id
    }

    fun getId(): Int {
        return id
    }
}
package com.example.heroes.ui.details

import androidx.lifecycle.ViewModel

class UnitsDetailsViewModel : ViewModel() {
    companion object {
        var unitName: String = ""
    }

    fun getUnitName(): String {
        return unitName
    }

    fun setUnitName(name: String) {
        unitName = name
    }

}
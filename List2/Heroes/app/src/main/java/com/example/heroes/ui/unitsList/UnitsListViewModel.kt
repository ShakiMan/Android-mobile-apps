package com.example.heroes.ui.unitsList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.heroes.dataLoaders.UnitsDataSource
import com.example.heroes.dataModels.UnitData

class UnitsListViewModel : ViewModel() {
    private var allUnits: MutableList<UnitData> = UnitsDataSource().createDataSet()
    private val toShowUnits: MutableLiveData<MutableList<UnitData>>? = MutableLiveData()
    private val favouritesUnits: MutableLiveData<MutableList<UnitData>> = MutableLiveData()
    private var onlyFavourite: Boolean = false

    fun getAllUnitsList(): MutableList<UnitData> {
        return allUnits
    }

    fun setAllUnitsList(unitsList: MutableList<UnitData>) {
        allUnits = unitsList
    }

    fun getToShowUnitsList(): MutableList<UnitData>? {
        return toShowUnits?.value?.toMutableList()
    }

    fun getToShowUnitsListLiveData(): LiveData<MutableList<UnitData>>? {
        return toShowUnits
    }

    fun setToShowUnitsList(unitsList: MutableList<UnitData>) {
        toShowUnits?.value = unitsList
    }

    fun getOnlyFavourites():Boolean{
        return onlyFavourite
    }

    fun setOnlyFavourites(boolean: Boolean){
        onlyFavourite = boolean
    }

    fun getFavourites(): MutableList<UnitData>? {
        return favouritesUnits.value?.toMutableList()
    }

    fun setFavourites() {
        allUnits.forEach(){
            if (it.favourite){
                favouritesUnits.value?.add(it)
            }
        }
    }

    fun removeUnit(unitData: UnitData){
        allUnits.remove(unitData)
        toShowUnits?.value?.remove(unitData)
        toShowUnits?.value = toShowUnits?.value?.toMutableList()
    }
}
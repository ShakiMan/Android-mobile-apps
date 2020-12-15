package com.example.heroes.dataModels

class UnitData(
    var name: String,
    var category: String,
    var image: String,
    var favourite: Boolean
) {
    override fun toString(): String {
        return "$name, $category, $favourite"
    }
}
package com.example.app.models

import java.io.Serializable

class BmiData(
    var bmi: String,
    var mass: String,
    var height: String,
    var date: String,
    var europeanStandard: Boolean
) : Serializable {

    override fun toString(): String {
        return "BmiData(bmi='$bmi', mass='$mass', height='$height', date='$date', European system of measurement='$europeanStandard')"
    }
}
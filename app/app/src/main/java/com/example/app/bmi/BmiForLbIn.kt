package com.example.app.bmi

class BmiForLbIn(
    private val mass: Double,
    private val height: Double
) : Bmi {
    override fun count(): Double {
        if (mass < 0.0 || height < 0.0) return -1.00
        if (mass == 0.0 || height == 0.0) return 0.00
        return (mass / (height * height)) * 703
    }
}
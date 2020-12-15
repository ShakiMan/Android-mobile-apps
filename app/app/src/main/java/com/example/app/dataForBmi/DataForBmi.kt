package com.example.app.dataForBmi

class DataForBmi {
    companion object{
        //I used shortcuts to easier use in other classes
        const val seBorder: Double = 16.0 //Border between Starvation and Emaciation
        const val euBorder: Double = 17.0 //Border between Emaciation and Underweight
        const val uwBorder: Double = 18.5 //Border between Underweight and Wanted Weight
        const val woBorder: Double = 25.0 //Border between Wanted Weight and Overweight
        const val ofBorder: Double = 30.0 //Border between Overweight and First Degree Obesity
        const val fsBorder: Double = 35.0 //Border between First Degree Obesity and Second Degree Obesity
        const val stBorder: Double = 40.0 //Border between Second Degree Obesity and Third Degree Obesity
    }
}
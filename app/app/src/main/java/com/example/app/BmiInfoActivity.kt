package com.example.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_bmi_info.*

class BmiInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi_info)

        title = "Bmi details"

        val message = intent.getDoubleExtra(EXTRA_MESSAGE, 0.0)
        newActivityBmi.text = message.toString()

        match(message)
    }

    private fun match(bmi: Double) {
        when {
            bmi < 16 -> {
                newActivityBmi.setTextColor(getColor(R.color.colorStarvation))
                imageViewBmi.setImageResource(R.drawable.mass_deficit)
                textDescription.text = getString(R.string.starvation_description)
            }
            bmi >= 16 && bmi < 17 -> {
                newActivityBmi.setTextColor(getColor(R.color.colorEmaciation))
                imageViewBmi.setImageResource(R.drawable.mass_deficit)
                textDescription.text = getString(R.string.emaciation_description)
            }
            bmi >= 17 && bmi < 18.5 -> {
                newActivityBmi.setTextColor(getColor(R.color.colorUnderweight))
                imageViewBmi.setImageResource(R.drawable.underweight)
                textDescription.text = getString(R.string.underweight_description)
            }
            bmi >= 18.5 && bmi < 25 -> {
                newActivityBmi.setTextColor(getColor(R.color.colorWantedWeight))
                imageViewBmi.setImageResource(R.drawable.norm)
                textDescription.text = getString(R.string.norm_description)
            }
            bmi >= 25 && bmi < 30 -> {
                newActivityBmi.setTextColor(getColor(R.color.colorOverweight))
                imageViewBmi.setImageResource(R.drawable.overweight)
                textDescription.text = getString(R.string.overweight_description)
            }
            bmi >= 30 && bmi < 35 -> {
                newActivityBmi.setTextColor(getColor(R.color.colorFirstDegreeObesity))
                imageViewBmi.setImageResource(R.drawable.obesity1)
                textDescription.text = getString(R.string.obesity1_description)
            }
            bmi >= 35 && bmi < 40 -> {
                newActivityBmi.setTextColor(getColor(R.color.colorSecondDegreeObesity))
                imageViewBmi.setImageResource(R.drawable.obesity2)
                textDescription.text = getString(R.string.obesity2_description)
            }
            bmi > 40 -> {
                newActivityBmi.setTextColor(getColor(R.color.colorThirdDegreeObesity))
                imageViewBmi.setImageResource(R.drawable.obesity3)
                textDescription.text = getString(R.string.obesity3_description)
            }
        }
        if (bmi == 0.0) {
            imageViewBmi.setImageResource(R.drawable.bmi_image)
            textDescription.text = getString(R.string.error_2activity)
        }
    }
}
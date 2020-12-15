package com.example.app

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import com.example.app.bmi.Bmi
import com.example.app.bmi.BmiForCmKg
import com.example.app.bmi.BmiForLbIn
import com.example.app.dataForBmi.DataForBmi
import com.example.app.database.BmiRepository
import com.example.app.database.BmiData
import com.example.app.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

const val EXTRA_MESSAGE = "com.example.app.MESSAGE"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bmiRepository: BmiRepository
    private var europeanStandard = true
    private var bmiResult = 0.00

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bmiRepository = BmiRepository(application)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("bmi_result", bmiTV.text.toString())
        outState.putString("mass_unit", massTV.text.toString())
        outState.putString("height_unit", heightTV.text.toString())
        outState.putBoolean("unit_standard", europeanStandard)
        val color = bmiTV.currentTextColor
        outState.putInt("bmi_color", color)
        outState.putDouble("shipped_bmi", bmiResult)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        bmiTV.text = savedInstanceState.getString("bmi_result")
        massTV.text = savedInstanceState.getString("mass_unit")
        heightTV.text = savedInstanceState.getString("height_unit")
        europeanStandard = savedInstanceState.getBoolean("unit_standard")
        bmiTV.setTextColor(savedInstanceState.getInt("bmi_color"))
        bmiResult = savedInstanceState.getDouble("shipped_bmi")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.app_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.bmi_cm_kg -> {
                massTV.text = getString(R.string.mass_kg)
                heightTV.text = getString(R.string.height_cm)
                europeanStandard = true
                true
            }
            R.id.bmi_lb_in -> {
                massTV.text = getString(R.string.mass_lb)
                heightTV.text = getString(R.string.height_in)
                europeanStandard = false
                true
            }
            R.id.history -> {
                openHistoryActivity()
                true
            }
            R.id.clear_history -> {
                clearData()
                true
            }
            R.id.help -> {
                popupHelp()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun count(view: View) {
        binding.apply {
            var isEmpty = false
            var wrongValue = false
            if (heightET.text.isBlank()) {
                heightET.error = getString(R.string.height_is_empty)
                isEmpty = true
            }
            if (massET.text.isBlank()) {
                massET.error = getString(R.string.mass_is_empty)
                isEmpty = true
            }
            if (!isEmpty) {
                val height: Double = heightET.text.toString().replace(",", ".").toDouble()
                val mass: Double = massET.text.toString().replace(",", ".").toDouble()
                if (europeanStandard && (height < 100 || height > 250)) {
                    heightET.error = getString(R.string.height_wrong_value_europe)
                    wrongValue = true
                }
                if (!europeanStandard && (height < 39.37 || height > 98.43)) {
                    heightET.error = getString(R.string.height_wrong_value_not_europe)
                    wrongValue = true
                }
                if (europeanStandard && (mass < 30 || mass > 400)) {
                    massET.error = getString(R.string.mass_wrong_value_europe)
                    wrongValue = true
                }
                if (!europeanStandard && (mass < 66 || mass > 880)) {
                    massET.error = getString(R.string.mass_wrong_value_not_europe)
                    wrongValue = true
                }
                if (!wrongValue) {
                    val bmiObj: Bmi = if (europeanStandard) BmiForCmKg(mass, height)
                    else BmiForLbIn(mass, height)
                    val result = bmiObj.count()
                    bmiResult = result
                    bmiTV.text = String.format("%.1f", result)
                    match(result)
                    addToList()
                }
            }
        }
    }

    private fun addToList() {
        val date = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE)
        val time = LocalDateTime.now().format(DateTimeFormatter.ISO_TIME)
        val currentDate = "$date  $time"
        val bmiData =
            BmiData(
                bmi = bmiTV.text.toString(),
                mass = massET.text.toString(),
                height = heightET.text.toString(),
                date = currentDate,
                europeanStandard = europeanStandard
            )
        bmiRepository.insert(bmiData)
    }

    fun openBmiInfoActivity(view: View) {
        if (bmiTV.text.toString().replace(",", ".") != "0.00") {
            val message = bmiTV.text.toString().replace(",", ".").toDouble()
            val intent = Intent(this, BmiInfoActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, message)
            }
            startActivityForResult(intent, 0)
        }
    }

    private fun openHistoryActivity() {
        val intent = Intent(this, HistoryActivity::class.java)
        startActivityForResult(intent, 0)
    }

    private fun match(result: Double) {
        when {
            result < DataForBmi.seBorder ->
                bmiTV.setTextColor(getColor(R.color.colorStarvation))
            result >= DataForBmi.seBorder && result < DataForBmi.euBorder ->
                bmiTV.setTextColor(getColor(R.color.colorEmaciation))
            result >= DataForBmi.euBorder && result < DataForBmi.uwBorder ->
                bmiTV.setTextColor(getColor(R.color.colorUnderweight))
            result >= DataForBmi.uwBorder && result < DataForBmi.woBorder ->
                bmiTV.setTextColor(getColor(R.color.colorWantedWeight))
            result >= DataForBmi.woBorder && result < DataForBmi.ofBorder ->
                bmiTV.setTextColor(getColor(R.color.colorOverweight))
            result >= DataForBmi.ofBorder && result < DataForBmi.fsBorder ->
                bmiTV.setTextColor(getColor(R.color.colorFirstDegreeObesity))
            result >= DataForBmi.fsBorder && result < DataForBmi.stBorder ->
                bmiTV.setTextColor(getColor(R.color.colorSecondDegreeObesity))
            result > DataForBmi.stBorder ->
                bmiTV.setTextColor(getColor(R.color.colorThirdDegreeObesity))
        }
    }

    private fun clearData() {
        bmiRepository.clear()
    }

    @SuppressLint("InflateParams")
    private fun popupHelp() {
        val view = layoutInflater.inflate(R.layout.activity_help, null)
        val window = PopupWindow(
            view,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window.isOutsideTouchable = true
        window.showAtLocation(main_layout, Gravity.CENTER, 0, 0)
    }

}
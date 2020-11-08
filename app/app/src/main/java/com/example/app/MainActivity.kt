package com.example.app

import android.content.Context
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
import com.example.app.databinding.ActivityMainBinding
import com.example.app.models.BmiData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

const val EXTRA_MESSAGE = "com.example.app.MESSAGE"
const val DATA_MESSAGE = "com.example.app.DATA"
const val SHARED_MESSAGE = "com.example.app.SHARED_PREFERENCES"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    private var europeanStandard = true
    private var bmiResult = 0.00
    private var bmiDataList = ArrayList<BmiData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences(SHARED_MESSAGE, Context.MODE_PRIVATE)
        loadData()
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
        outState.putSerializable("history", bmiDataList)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        bmiTV.text = savedInstanceState.getString("bmi_result")
        massTV.text = savedInstanceState.getString("mass_unit")
        heightTV.text = savedInstanceState.getString("height_unit")
        europeanStandard = savedInstanceState.getBoolean("unit_standard")
        bmiTV.setTextColor(savedInstanceState.getInt("bmi_color"))
        bmiResult = savedInstanceState.getDouble("shipped_bmi")
        bmiDataList = if (savedInstanceState.getSerializable("history") == null) ArrayList<BmiData>()
        else savedInstanceState.getSerializable("history") as ArrayList<BmiData>
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
                    if (bmiDataList.size >= 10) {
                        bmiDataList.removeAt(0)
                        addToList()
                    } else {
                        addToList()
                    }

                }
            }
        }
    }

    private fun addToList() {
        val date = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE)
        val time = LocalDateTime.now().format(DateTimeFormatter.ISO_TIME)
        val currentDate = "$date  $time"
        bmiDataList.add(
            BmiData(
                bmiTV.text.toString(),
                massET.text.toString(),
                heightET.text.toString(),
                currentDate,
                europeanStandard
            )
        )
        saveData()
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
        val intent = Intent(this, HistoryActivity::class.java).apply {
            putExtra(DATA_MESSAGE, bmiDataList)
        }
        startActivityForResult(intent, 0)
    }

    private fun match(result: Double) {
        when {
            result < 16 -> bmiTV.setTextColor(getColor(R.color.colorStarvation))
            result >= 16 && result < 17 -> bmiTV.setTextColor(getColor(R.color.colorEmaciation))
            result >= 17 && result < 18.5 -> bmiTV.setTextColor(getColor(R.color.colorUnderweight))
            result >= 18.5 && result < 25 -> bmiTV.setTextColor(getColor(R.color.colorWantedWeight))
            result >= 25 && result < 30 -> bmiTV.setTextColor(getColor(R.color.colorOverweight))
            result >= 30 && result < 35 -> bmiTV.setTextColor(getColor(R.color.colorFirstDegreeObesity))
            result >= 35 && result < 40 -> bmiTV.setTextColor(getColor(R.color.colorSecondDegreeObesity))
            result > 40 -> bmiTV.setTextColor(getColor(R.color.colorThirdDegreeObesity))
        }
    }

    private fun saveData() {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        val gson = Gson()
        val json: String = gson.toJson(bmiDataList)
        editor.putString("data_list", json)
        editor.apply()
    }

    private fun loadData() {
        val json = sharedPreferences.getString("data_list", null)
        val type = object : TypeToken<ArrayList<BmiData>>() {}.type
        bmiDataList = if (json == null) {
            ArrayList<BmiData>()
        } else {
            Gson().fromJson(json, type)
        }
    }

    private fun clearData() {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        bmiDataList = ArrayList<BmiData>()
        val gson = Gson()
        val json: String = gson.toJson(bmiDataList)
        editor.putString("data_list", json)
        editor.apply()
    }

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
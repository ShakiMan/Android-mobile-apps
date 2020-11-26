package com.example.app

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app.models.BmiData
import kotlinx.android.synthetic.main.my_row.view.*

class MyAdapter(private var myDataSet: ArrayList<BmiData>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.my_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(myDataSet[position])
    }

    override fun getItemCount() = myDataSet.size

    class MyViewHolder(item_view: View) : RecyclerView.ViewHolder(item_view) {
        private val bmiText = item_view.bmi_data_text_view
        private val massText = item_view.mass_data_text_view
        private val heightText = item_view.height_data_text_view
        private val dateText = item_view.date_text_view

        private var massUnit: String = ""
        private var heightUnit: String = ""

        @SuppressLint("SetTextI18n")
        fun bind(bmiData: BmiData) {
            if (bmiData.europeanStandard){
                massUnit = " [kg]"
                heightUnit = " [m]"
            }
            else{
                massUnit = " [lb]"
                heightUnit = " [in]"
            }

            bmiText.text = "Bmi: " + bmiData.bmi
            massText.text = "Mass: "   + bmiData.mass + massUnit
            heightText.text = "Height: " + bmiData.height + heightUnit
            dateText.text = "Date: " + bmiData.date
        }
    }
}

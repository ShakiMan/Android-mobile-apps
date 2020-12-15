package com.example.app

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app.database.BmiData
import kotlinx.android.synthetic.main.my_row.view.*

class HistoryAdapter() : RecyclerView.Adapter<HistoryAdapter.MyViewHolder>() {
    private lateinit var myDataSet: List<BmiData>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.my_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(myDataSet[position])
    }

    override fun getItemCount() = myDataSet.size

    fun submitList(data: List<BmiData>) {
        myDataSet = data
    }

    class MyViewHolder(item_view: View) : RecyclerView.ViewHolder(item_view) {
        private var massUnit: String = ""
        private var heightUnit: String = ""

        @SuppressLint("SetTextI18n")
        fun bind(bmiData: BmiData) {
            if (bmiData.europeanStandard) {
                massUnit = " [kg]"
                heightUnit = " [m]"
            } else {
                massUnit = " [lb]"
                heightUnit = " [in]"
            }
            itemView.apply {
                bmi_data_text_view.text = "Bmi: " + bmiData.bmi
                mass_data_text_view.text = "Mass: " + bmiData.mass + massUnit
                height_data_text_view.text = "Height: " + bmiData.height + heightUnit
                date_text_view.text = "Date: " + bmiData.date
            }
        }
    }
}

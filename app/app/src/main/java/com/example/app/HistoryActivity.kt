package com.example.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app.database.BmiRepository
import com.example.app.database.BmiData
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {
    private lateinit var viewAdapter: HistoryAdapter
    private var measurementsList: List<BmiData> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        title = "History of measurements"
        measurementsList = BmiRepository(application).getAllBmi()
        viewAdapter = HistoryAdapter()

        initRecyclerView()
        submitData()
    }

    private fun initRecyclerView(){
        historyRV.apply{
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@HistoryActivity)
            val spacingDecorator = SpacingItemDecoration(30)
            addItemDecoration(spacingDecorator)
            adapter = viewAdapter
        }
    }

    private fun submitData(){
        viewAdapter.submitList(measurementsList)
    }

}
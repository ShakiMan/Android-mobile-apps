package com.example.app

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.models.BmiData

const val RETURN_DATA_MESSAGE = "com.example.app.RETURN_DATA"

class HistoryActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        title = "History of measurements"

        val myDataSet = intent.getSerializableExtra(DATA_MESSAGE)

        viewManager = LinearLayoutManager(this)
        viewAdapter = MyAdapter(myDataSet as ArrayList<BmiData>)

        recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            val topSpacingDecorator = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingDecorator)
            adapter = viewAdapter

        }

    }

}
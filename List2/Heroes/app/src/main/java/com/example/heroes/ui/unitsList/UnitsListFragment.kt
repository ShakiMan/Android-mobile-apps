package com.example.heroes.ui.unitsList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heroes.R
import com.example.heroes.dataModels.UnitData
import kotlinx.android.synthetic.main.units_list_fragment.*

class UnitsListFragment : Fragment() {

    private lateinit var viewModel: UnitsListViewModel
    private lateinit var unitsListAdapter: UnitsListAdapter
    private  var activeFilter: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(UnitsListViewModel::class.java)
        return inflater.inflate(R.layout.units_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        unitsRV.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(view.context)
            unitsListAdapter = UnitsListAdapter()
            val itemDecoration = SpacingItemDecoration(
                resources.getDimensionPixelSize(
                    R.dimen.margin
                )
            )
            addItemDecoration(itemDecoration)
            adapter = unitsListAdapter
        }

        val swipeHandler = object : SwipeToDeleteCallback(unitsRV.context) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = unitsRV.adapter as UnitsListAdapter
                adapter.removeAt(viewHolder.adapterPosition)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(unitsRV)
        setListData()

        viewModel.getToShowUnitsListLiveData()?.observe(viewLifecycleOwner, Observer {
            setListData()
        })

        allCategoryBTN.setOnClickListener {
            viewModel.setToShowUnitsList(viewModel.getAllUnitsList())
            unitsListAdapter.submitData(viewModel)
        }
        
        castleCategoryBTN.setOnClickListener {
            filterTheList("Castle")
        }

        rampartCategoryBTN.setOnClickListener {
            filterTheList("Rampart")
        }

        dungeonCategoryBTN.setOnClickListener {
            filterTheList("Dungeon")
        }

        favouriteCategoryBTN.setOnClickListener {
            filterTheListFavourite()
        }
    }

    private fun filterTheListFavourite() {
        val allUnitsList = viewModel.getAllUnitsList()
        val newList = mutableListOf<UnitData>()

        allUnitsList.forEach {
            if (it.favourite){
                newList.add(it)
            }
        }

        viewModel.setToShowUnitsList(newList)
        unitsListAdapter.submitData(viewModel)
    }

    private fun filterTheList(category: String) {
        if (activeFilter != category) {
            activeFilter = category

            val allUnitsList = viewModel.getAllUnitsList()
            val newList = mutableListOf<UnitData>()

            allUnitsList.forEach {
                if (it.category == category) {
                    newList.add(it)
                }
            }

            viewModel.setToShowUnitsList(newList)
            unitsListAdapter.submitData(viewModel)
        }
    }

    private fun setListData() {
        viewModel.let { unitsListAdapter.submitData(it) }
    }

}
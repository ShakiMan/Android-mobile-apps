package com.example.heroes.ui.details.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.heroes.R
import com.example.heroes.dataLoaders.UnitsDataSource
import com.example.heroes.ui.details.UnitsDetailsViewModel
import kotlinx.android.synthetic.main.description_screen_fragment.*
import java.util.*

class DescriptionScreenFragment : Fragment() {
    private lateinit var viewModel: UnitsDetailsViewModel
    private var unitName: String = "null"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(UnitsDetailsViewModel::class.java)
        if (unitName != "null")
            viewModel.setUnitName(unitName)
        return inflater.inflate(R.layout.description_screen_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val unit = UnitsDataSource().getUnitByName(viewModel.getUnitName())

        detailsScreenTV.text = getString(matchDescription(unit.name))
    }

    private fun matchDescription(name: String): Int {
        return when (name.toLowerCase(Locale.ROOT)) {
            "angel" -> R.string.angel_description
            "archer" -> R.string.archer_description
            "centaur" -> R.string.centaur_description
            "green dragon" -> R.string.green_dragon_description
            "griffin" -> R.string.griffin_description
            "manticore" -> R.string.manticore_description
            "minotaur" -> R.string.minotaur_description
            "red dragon" -> R.string.red_dragon_description
            else -> R.string.wood_elf_description
        }
    }

    fun setUnitName(name: String) {
        unitName = name
    }

}
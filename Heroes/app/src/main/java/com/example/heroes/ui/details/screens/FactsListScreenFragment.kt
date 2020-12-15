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
import kotlinx.android.synthetic.main.facts_list_screen_fragment.*
import java.util.*

class FactsListScreenFragment : Fragment() {
    private lateinit var viewModel: UnitsDetailsViewModel
    private var unitName: String = "null"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(UnitsDetailsViewModel::class.java)
        if (unitName != "null")
            viewModel.setUnitName(unitName)
        return inflater.inflate(R.layout.facts_list_screen_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val unit = UnitsDataSource().getUnitByName(viewModel.getUnitName())

        factsTV.text = getString(matchFacts(unit.name))
    }

    fun setUnitName(name: String) {
        unitName = name
    }

    private fun matchFacts(name: String): Int {
        return when (name.toLowerCase(Locale.ROOT)) {
            "angel" -> R.string.angel_facts
            "archer" -> R.string.archer_facts
            "centaur" -> R.string.centaur_facts
            "green dragon" -> R.string.green_dragon_facts
            "griffin" -> R.string.griffin_facts
            "manticore" -> R.string.manticore_facts
            "minotaur" -> R.string.minotaur_facts
            "red dragon" -> R.string.red_dragon_facts
            else -> R.string.wood_elf_facts
        }
    }

}
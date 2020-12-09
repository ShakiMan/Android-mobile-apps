package com.example.heroes.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.heroes.R
import com.example.heroes.dataLoaders.UnitsDataSource
import com.example.heroes.ui.details.viewPager.ViewPagerFragment
import kotlinx.android.synthetic.main.description_screen_fragment.*
import kotlinx.android.synthetic.main.units_details_fragment.*
import kotlinx.android.synthetic.main.units_list_item.*

class UnitsDetailsFragment : Fragment() {
    private lateinit var viewModel: UnitsDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.supportFragmentManager
        return inflater.inflate(R.layout.units_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(UnitsDetailsViewModel::class.java)
        val unitName = requireArguments().getString("unitName")

        val dataSource = UnitsDataSource()
        val unit = dataSource.getUnitByName(unitName)

        if (unitName != null) {
            viewModel.setUnitName(unitName)
        }

        detailsUnitNameTV.text = unit.name
        detailsUnitCategoryTV.text = unit.category

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)

        Glide.with(requireContext())
            .applyDefaultRequestOptions(requestOptions)
            .load(unit.image)
            .into(detailsUnitIV)

        val statsImage = dataSource.getStatsImageByName(unit.name)

        Glide.with(requireContext())
            .applyDefaultRequestOptions(requestOptions)
            .load(statsImage)
            .into(detailsUnitStatsIV)
    }

}
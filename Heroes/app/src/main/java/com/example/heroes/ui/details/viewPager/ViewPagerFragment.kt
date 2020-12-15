package com.example.heroes.ui.details.viewPager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.heroes.R
import com.example.heroes.ui.details.UnitsDetailsViewModel
import com.example.heroes.ui.details.screens.DescriptionScreenFragment
import com.example.heroes.ui.details.screens.FactsListScreenFragment
import com.example.heroes.ui.details.screens.GalleryScreenFragment
import kotlinx.android.synthetic.main.view_pager_fragment.view.*


class ViewPagerFragment : Fragment() {
    private lateinit var viewModel: UnitsDetailsViewModel

    private lateinit var descriptionScreenFragment: DescriptionScreenFragment
    private lateinit var galleryScreenFragment: GalleryScreenFragment
    private lateinit var factsListScreenFragment: FactsListScreenFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.supportFragmentManager
        return inflater.inflate(R.layout.view_pager_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UnitsDetailsViewModel::class.java)
        setViewPager()
    }

    private fun setViewPager() {
        descriptionScreenFragment = DescriptionScreenFragment()
        galleryScreenFragment = GalleryScreenFragment()
        factsListScreenFragment = FactsListScreenFragment()
        descriptionScreenFragment.setUnitName(viewModel.getUnitName())
        galleryScreenFragment.setUnitName(viewModel.getUnitName())
        factsListScreenFragment.setUnitName(viewModel.getUnitName())

        val fragmentsList: ArrayList<Fragment> = arrayListOf(
            descriptionScreenFragment,
            galleryScreenFragment,
            factsListScreenFragment
        )

        val adapter = ViewPagerAdapter(
            fragmentsList,
            childFragmentManager,
            lifecycle
        )

        view?.screensViewPager?.adapter = adapter
    }

}
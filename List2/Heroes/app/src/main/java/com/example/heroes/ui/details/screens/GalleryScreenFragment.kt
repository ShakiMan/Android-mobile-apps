package com.example.heroes.ui.details.screens

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
import com.example.heroes.ui.details.UnitsDetailsViewModel
import kotlinx.android.synthetic.main.gallery_screen_fragment.*

class GalleryScreenFragment : Fragment() {
    private lateinit var viewModel: UnitsDetailsViewModel
    private var unitName: String = "null"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(UnitsDetailsViewModel::class.java)
        if (unitName != "null")
            viewModel.setUnitName(unitName)
        return inflater.inflate(R.layout.gallery_screen_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataSource = UnitsDataSource()

        val unit = dataSource.getUnitByName(viewModel.getUnitName())

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)

        val imagesList = dataSource.getImagesUrlByName(unit.name)

        Glide.with(requireContext())
            .applyDefaultRequestOptions(requestOptions)
            .load(imagesList[0])
            .into(firstGalleryImageIV)

        Glide.with(requireContext())
            .applyDefaultRequestOptions(requestOptions)
            .load(imagesList[1])
            .into(secondGalleryImageIV)

        Glide.with(requireContext())
            .applyDefaultRequestOptions(requestOptions)
            .load(imagesList[2])
            .into(thirdGalleryImageIV)
    }

    fun setUnitName(name: String) {
        unitName = name
    }

}
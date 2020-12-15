package com.example.heroes.ui.unitsList

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.heroes.R
import com.example.heroes.dataModels.UnitData
import kotlinx.android.synthetic.main.units_list_item.view.*

class UnitsListAdapter : RecyclerView.Adapter<UnitsListAdapter.UnitsListViewHolder>() {
    private lateinit var unitsList: MutableList<UnitData>
    private lateinit var unitsToShowList: MutableList<UnitData>
    private lateinit var viewModel: UnitsListViewModel

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UnitsListViewHolder {
        return UnitsListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.units_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: UnitsListViewHolder, position: Int) {
        holder.bind(unitsToShowList[position])
    }

    override fun getItemCount(): Int {
        return unitsToShowList.size
    }

    fun removeAt(position: Int) {
        viewModel.removeUnit(unitsToShowList[position])
        notifyItemRemoved(position)
    }

    fun submitData(unitsListViewModel: UnitsListViewModel) {
        viewModel = unitsListViewModel
        unitsList = viewModel.getAllUnitsList()

        unitsToShowList = if (viewModel.getToShowUnitsList() == null) {
            unitsList.toMutableList()
        } else {
            viewModel.getToShowUnitsList()!!
        }
        notifyDataSetChanged()
    }


    inner class UnitsListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(unitData: UnitData) {
            itemView.apply {
                titleTV.text = unitData.name
                categoryTV.text = unitData.category
                changeFavouriteButtonColor(unitData)

                val requestOptions = RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)

                Glide.with(itemView.context)
                    .applyDefaultRequestOptions(requestOptions)
                    .load(unitData.image)
                    .into(unitIV)

                favoriteBTN.setOnClickListener {
                    unitData.favourite = !unitData.favourite
                    changeFavouriteButtonColor(unitData)
                    if (viewModel.getOnlyFavourites()) {
                        unitsToShowList = removeNotFavourites(unitsToShowList)
                        notifyDataSetChanged()
                    }
                    viewModel.setToShowUnitsList(unitsToShowList)
                }

                setItemBackground(unitData.category)

                setOnClickListener {
                    val unitName = unitData.name
                    val bundle = bundleOf("unitName" to unitName)
                    it.findNavController()
                        .navigate(R.id.action_unitsListFragment_to_unitsDetailsFragment, bundle)
                }
            }
        }

        private fun setItemBackground(category: String) {
            when (category) {
                "Castle" -> {
                    itemView.background = getDrawable(itemView.context, R.drawable.casle)
                    itemView.background.alpha = 55
                }
                "Rampart" -> {
                    itemView.background = getDrawable(itemView.context, R.drawable.rampart)
                    itemView.background.alpha = 55
                }
                "Dungeon" -> {
                    itemView.background = getDrawable(itemView.context, R.drawable.dungeon)
                    itemView.background.alpha = 55
                }
            }
        }

        private fun changeFavouriteButtonColor(unitData: UnitData) {
            itemView.apply {
                if (unitData.favourite)
                    favoriteBTN.setBackgroundResource(R.drawable.ic_baseline_favorite_orange_24)
                else
                    favoriteBTN.setBackgroundResource(R.drawable.ic_baseline_favorite_border_shadow_24)
            }
        }
    }

    private fun removeNotFavourites(unitsToShowList: MutableList<UnitData>): MutableList<UnitData> {
        val favouritesUnits = mutableListOf<UnitData>()
        for (item in unitsToShowList) {
            if (item.favourite) {
                favouritesUnits.add(item)
            }
        }
        return favouritesUnits
    }

    fun getDrawable(context: Context, drawable: Int): Drawable? {
        return ResourcesCompat.getDrawable(context.resources, drawable, null)
    }
}
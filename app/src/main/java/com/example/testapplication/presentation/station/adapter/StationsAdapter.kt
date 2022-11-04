package com.example.testapplication.presentation.station.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.R
import com.example.testapplication.databinding.StationItemBinding
import com.example.testapplication.network.data_station.StationResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StationsAdapter @Inject constructor(
    private val context: Context,
    private val typeItems: List<StationResponse>,
    private val onItemClickCallBack: OnItemClickCallBack,
) : RecyclerView.Adapter<StationsAdapter.ViewHolder>() {
        private val inflater: LayoutInflater = LayoutInflater.from(context)

        interface OnItemClickCallBack {
            fun onItemClick(index: Int)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = inflater.inflate(R.layout.station_item, parent, false)
            return ViewHolder(view)
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
             val response = typeItems[position]
            with(holder.binding){
                locationName.text = response.name
                arrow.fromAsset("icChevronRight.pdf").load()
                addressCity.text = response.address + response.city

                //distance.text = response.
            }

        }

        override fun getItemCount() = typeItems.size

        inner class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView),
            View.OnClickListener {

            var binding = StationItemBinding.bind(rootView)

            override fun onClick(v: View) {
                onItemClickCallBack.onItemClick(adapterPosition)
            }

            init {
                itemView.setOnClickListener(this)
            }

        }
}
package com.example.testapplication.presentation.station.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.R
import com.example.testapplication.app.AppPrefsKey
import com.example.testapplication.databinding.StationItemBinding
import com.example.testapplication.network.data_station.StationResponse
import com.example.testapplication.presentation.station.CoordinatePoint
import com.example.testapplication.presentation.station.StationResponseWithSortedByDistance
import com.example.testapplication.utils.prefs.SharedWorker
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.coroutineContext
import kotlin.math.roundToInt

@Singleton
class StationsAdapter @Inject constructor(
    private val context: Context,
    private val typeItems: List<StationResponseWithSortedByDistance>,
    private val onItemClickCallBack: OnItemClickCallBack,
    private val flagToShowKw: Boolean,
    private val flagToShowDistance: Boolean
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
        with(holder.binding) {
            locationName.text = response.name
            addressCity.text = response.address + response.city
            if (flagToShowDistance) {
                distance.text = getDistance(response.distance.toString())
            }
            number1.text = response.connectors[position].maxKw.toString()
            number2.text = response.connectors[position].maxKw.toString()
            number3.text = response.connectors[position].maxKw.toString()
            number4.text = response.connectors[position].maxKw.toString()

            if (flagToShowKw) {
                text1.text = response.connectors[position].type
                text2.text = response.connectors[position].type
                text3.text = response.connectors[position].type
                text4.text = response.connectors[position].type
            }

        }

    }

    override fun getItemCount() = typeItems.size


    private fun getDistance(distance: String) =
        context.getString(R.string.station_distance, distance)


    inner class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView),
        View.OnClickListener {
        val binding = StationItemBinding.bind(rootView)

        override fun onClick(v: View) {
            onItemClickCallBack.onItemClick(adapterPosition)
        }

        init {
            binding.arrow.fromAsset("icChevronRight.pdf").load()
            itemView.setOnClickListener(this)
        }

    }
}
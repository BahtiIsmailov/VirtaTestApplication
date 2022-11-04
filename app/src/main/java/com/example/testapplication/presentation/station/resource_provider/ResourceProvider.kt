package com.example.testapplication.presentation.station.resource_provider

import android.content.Context
import com.example.testapplication.R

class ResourceProvider(private val context: Context) {

    private fun getDistance(distance:String) =
        context.getString(R.string.station_distance, distance)
}
package com.example.testapplication.presentation.station.domain

import com.example.testapplication.network.data_station.StationResponse

interface StationsInteractor {

    suspend fun getStations(latitude:Float,longitude:Float,limit:Int):List<StationResponse>
}
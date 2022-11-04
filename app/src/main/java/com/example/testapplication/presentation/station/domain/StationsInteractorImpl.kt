package com.example.testapplication.presentation.station.domain

import com.example.testapplication.network.data_station.StationResponse
import com.example.testapplication.network.remotedatasource.ApiRepository
import javax.inject.Inject

class StationsInteractorImpl @Inject constructor(
    private val repository: ApiRepository
): StationsInteractor {


    override suspend fun getStations(latitude:Double,longitude:Double,limit:Int): List<StationResponse> {
        return repository.getStations(latitude,longitude,limit)
    }
}
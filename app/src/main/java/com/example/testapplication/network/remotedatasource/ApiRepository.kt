package com.example.testapplication.network.remotedatasource

import com.example.testapplication.network.AuthByLoggingResponse
import com.example.testapplication.network.data_station.StationResponse

interface ApiRepository {
    suspend fun login(login:String,password:String): AuthByLoggingResponse
    suspend fun getStations(latitude:Float,longitude:Float,limit:Int):List<StationResponse>
}
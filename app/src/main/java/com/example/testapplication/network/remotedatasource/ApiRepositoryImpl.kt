package com.example.testapplication.network.remotedatasource

import com.example.testapplication.network.AuthByLoggingQuery
import com.example.testapplication.network.AuthByLoggingResponse
import com.example.testapplication.network.data_station.StationResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(
    private val api: Api
):ApiRepository {

    override suspend fun login(login: String,password:String): AuthByLoggingResponse {
        return withContext(Dispatchers.IO){
            api.login(AuthByLoggingQuery(login,password))
        }
    }

    override suspend fun getStations(latitude:Double,longitude:Double,limit:Int):List<StationResponse>{
        return withContext(Dispatchers.IO){
            api.getStation(latitude.toFloat(),longitude.toFloat(),limit)
        }
    }
}
package com.example.testapplication.network.remotedatasource

import com.example.testapplication.network.AuthByLoggingQuery
import com.example.testapplication.network.AuthByLoggingResponse
import com.example.testapplication.network.data_station.StationResponse
import retrofit2.http.*

interface Api {

    @POST("auth")
    suspend fun login(
        @Body authByLoginQuery: AuthByLoggingQuery,
    ): AuthByLoggingResponse

    @GET("stations")
    suspend fun getStation(
        @Query("latitude") latitude: Float,
        @Query("longitude") longitude: Float,
        @Query("limit") limit: Int
    ):List<StationResponse>

}
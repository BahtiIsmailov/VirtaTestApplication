package com.example.testapplication.network.data_station

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StationResponse(
    val address: String,
    val city: String,
    val country: String,
    val evses: List<Evse>,
    val icon: Int,
    val id: Int,
    val isPrivate: Boolean,
    val isRemoved: Boolean,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val provider: String
):Parcelable
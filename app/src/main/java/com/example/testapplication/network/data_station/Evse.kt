package com.example.testapplication.network.data_station

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Evse(
    val connectors: List<Connector>,
    val groupName: String,
    val id: Int
):Parcelable
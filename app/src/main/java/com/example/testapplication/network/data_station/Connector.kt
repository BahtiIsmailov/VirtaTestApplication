package com.example.testapplication.network.data_station

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Connector(
    val maxKw: Float,
    val type: String
):Parcelable
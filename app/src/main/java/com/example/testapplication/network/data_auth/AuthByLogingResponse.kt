package com.example.testapplication.network

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthByLoggingResponse(
    @SerializedName("token") val token:String
):Parcelable

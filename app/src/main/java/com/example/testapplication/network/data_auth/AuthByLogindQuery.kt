package com.example.testapplication.network

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthByLoggingQuery(
    @SerializedName("email") val email:String,
    @SerializedName("code") val code:String
):Parcelable

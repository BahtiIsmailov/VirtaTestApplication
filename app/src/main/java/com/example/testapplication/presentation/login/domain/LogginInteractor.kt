package com.example.testapplication.presentation.login.domain

import com.example.testapplication.network.AuthByLoggingResponse

interface LogginInteractor {

    suspend fun login(userName:String,password:String):AuthByLoggingResponse
}
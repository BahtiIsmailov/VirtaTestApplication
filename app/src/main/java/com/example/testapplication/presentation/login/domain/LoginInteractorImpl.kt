package com.example.testapplication.presentation.login.domain

import com.example.testapplication.app.AppPrefsKey
import com.example.testapplication.network.AuthByLoggingResponse
import com.example.testapplication.network.remotedatasource.ApiRepository
import com.example.testapplication.utils.prefs.SharedWorker
import javax.inject.Inject

class LoginInteractorImpl @Inject constructor(
    private val apiRepository: ApiRepository,
    private val sharedWorker: SharedWorker
) : LogginInteractor {

    override suspend fun login(userName: String, password: String): AuthByLoggingResponse {
        val response = apiRepository.login(userName, password)
        sharedWorker.saveMediate(AppPrefsKey.AUTH_KEY, response.token)
        return response
    }

}
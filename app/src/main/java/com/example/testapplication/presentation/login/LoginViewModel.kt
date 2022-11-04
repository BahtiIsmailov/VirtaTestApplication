package com.example.testapplication.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapplication.network.AuthByLoggingResponse
import com.example.testapplication.presentation.login.domain.LogginInteractor
import com.example.testapplication.network.networkstate.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginInteractor: LogginInteractor
): ViewModel() {

    private val _networkState = MutableLiveData<NetworkState<AuthByLoggingResponse>>()
    val networkState: LiveData<NetworkState<AuthByLoggingResponse>>
        get() = _networkState

    fun login(userName:String,password:String){
        _networkState.value = NetworkState.Loading
        viewModelScope.launch {
            try {
                val response = loginInteractor.login(userName,password)
                _networkState.value = NetworkState.Success(response)
            }catch (e:java.lang.Exception){
                 _networkState.value = NetworkState.Error(e.message.toString())
            }

        }

    }



}
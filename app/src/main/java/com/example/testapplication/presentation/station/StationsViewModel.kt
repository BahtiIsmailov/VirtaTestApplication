package com.example.testapplication.presentation.station

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapplication.app.AppPrefsKey
import com.example.testapplication.network.data_station.StationResponse
import com.example.testapplication.network.networkstate.NetworkState
import com.example.testapplication.presentation.station.domain.StationsInteractor
import com.example.testapplication.utils.prefs.SharedWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StationsViewModel @Inject constructor(
    private val interactor: StationsInteractor,
    private val sharedWorker: SharedWorker
) : ViewModel() {

    private val _stationsLiveData = MutableLiveData<NetworkState<List<StationResponse>?>>()
    val stationsLiveData: LiveData<NetworkState<List<StationResponse>?>> get() = _stationsLiveData

    fun getStations() {
        _stationsLiveData.value = NetworkState.Loading

        viewModelScope.launch {
            try {
                val response = interactor.getStations(getLocation()[0].toFloat(),getLocation()[1].toFloat(),10)
                _stationsLiveData.value = NetworkState.Success(response)

            } catch (e: java.lang.Exception) {

                _stationsLiveData.value = NetworkState.Error(e.message.toString())

            }
        }
    }
    private fun getLocation() : List<String>{
        return sharedWorker.load(AppPrefsKey.LOCATION,"48.278067-16.456204").split("-")
    }
}
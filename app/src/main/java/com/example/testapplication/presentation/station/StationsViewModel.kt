package com.example.testapplication.presentation.station

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapplication.app.App
import com.example.testapplication.app.AppPrefsKey
import com.example.testapplication.network.data_station.Connector
import com.example.testapplication.network.data_station.StationResponse
import com.example.testapplication.network.networkstate.NetworkState
import com.example.testapplication.presentation.station.domain.StationsInteractor
import com.example.testapplication.presentation.station.resource_provider.ResourceProvider
import com.example.testapplication.utils.prefs.SharedWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class StationsViewModel @Inject constructor(
    private val interactor: StationsInteractor,
    private val sharedWorker: SharedWorker,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private val _stationsLiveData =
        MutableLiveData<NetworkState<List<StationResponseWithSortedByDistance>?>>()
    val stationsLiveData: LiveData<NetworkState<List<StationResponseWithSortedByDistance>?>> get() = _stationsLiveData

    private var myLocation: CoordinatePoint? = null
    private val connector = mutableListOf<Connector>()

    init {
        myLocation = CoordinatePoint(getLocation()[0].toDouble(), getLocation()[1].toDouble())
    }

    fun getStations() {
        _stationsLiveData.value = NetworkState.Loading

        viewModelScope.launch {
            try {
                val response = mutableListOf<StationResponseWithSortedByDistance>()

                val responseFromApi = interactor.getStations(
                    getLocation()[0].toDouble(),
                    getLocation()[1].toDouble(),
                    10
                )

                responseFromApi.map { stationResponse ->
                    stationResponse.evses.map { evse ->
                        evse.connectors.map {
                            connector.add(it)
                        }
                    }
                }

                responseFromApi.forEach {
                    response.add(
                        StationResponseWithSortedByDistance(
                            name = it.name,
                            address = it.address,
                            city = it.city,
                            distance = getDistanceFromUser(it.latitude, it.longitude),
                            connectors = connector
                        )
                    )
                }

                _stationsLiveData.value = NetworkState.Success(response.sortedBy { it.distance })

            } catch (e: java.lang.Exception) {
                _stationsLiveData.value = NetworkState.Error(e.message.toString())
            }
        }
    }

    fun getFlagToShowKw() = sharedWorker.load(AppPrefsKey.TOGGLE_HIDE_KW,true)
    fun getFlagToShowDistance() = sharedWorker.load(AppPrefsKey.TOGGLE_HIDE_DISTANCE,true)

    private fun getLocation(): List<String> {
        return sharedWorker.load(AppPrefsKey.LOCATION, "48.278067-16.456204").split("-")
    }

    private fun getDistanceFromUser(lat: Double, long: Double): Int {
        val loc1 = Location("")
        loc1.latitude = lat
        loc1.longitude = long
        val loc2 = Location("")
        loc2.latitude = myLocation!!.latitude
        loc2.longitude = myLocation!!.longitude
        return (loc1.distanceTo(loc2) / 1000).roundToInt()
    }


}

data class CoordinatePoint(val latitude: Double, val longitude: Double)
data class StationResponseWithSortedByDistance(
    val name: String,
    val address: String,
    val city: String,
    val distance: Int,
    val connectors: List<Connector>
)
package com.example.testapplication.network.networkstate


sealed class NetworkState<out R> {
    data class Success<out T>(val data: T) : NetworkState<T>()
    data class Error(val error: String) : NetworkState<Nothing>()
    object Loading : NetworkState<Nothing>()


    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$error]"
            Loading -> "Loading"
        }
    }
}
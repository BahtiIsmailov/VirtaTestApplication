package com.example.testapplication

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.example.testapplication.network.networkstate.NetworkState
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
open class BaseFragment : Fragment(){

    fun<T> LiveData<NetworkState<T>>.observeUIState(onLoading: (()-> Unit)? = null, onError:((String)-> Unit)? = null, onSuccess: (T)-> Unit) {
        this.observe(viewLifecycleOwner){
            when (it) {
                is NetworkState.Loading -> {
                    onLoading?.invoke()
                }
                is NetworkState.Success -> {
                    onSuccess(it.data)

                }
                is NetworkState.Error -> {
                    onError?.invoke(it.error)
                }
            }
        }
    }
}
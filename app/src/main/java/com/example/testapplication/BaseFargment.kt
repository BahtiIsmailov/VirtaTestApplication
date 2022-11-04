package com.example.testapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.viewbinding.ViewBinding
import com.example.testapplication.network.networkstate.NetworkState
import dagger.hilt.android.AndroidEntryPoint

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

open class BaseFragment<VB : ViewBinding>(private val inflate: Inflate<VB>) : Fragment(){

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

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
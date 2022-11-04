package com.example.testapplication.presentation.station

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.testapplication.BaseFragment
import com.example.testapplication.R
import com.example.testapplication.databinding.FragmentLoginBinding
import com.example.testapplication.databinding.FragmentStationBinding
import com.example.testapplication.presentation.station.adapter.StationsAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StationFragment : BaseFragment() {

    private var _binding: FragmentStationBinding? = null
    private val binding get() = _binding!!

    private val viewModel:StationsViewModel by viewModels()

    private val callback = object : StationsAdapter.OnItemClickCallBack {
        override fun onItemClick(index: Int) {
           // viewModel.getDistanceFromUser(index)
        }
    }


    var adapter:StationsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getStations()
        initObservable()
        initListener()

    }

    private fun initListener(){}


    private fun initObservable(){
        with(viewModel){
            stationsLiveData.observeUIState(
                onSuccess = {
                    adapter = StationsAdapter(
                        requireContext(),
                        it!!,
                        callback
                    )
                    binding.recycle.adapter = adapter
                },
                onError = {
                    Toast.makeText(requireContext(), "123123", Toast.LENGTH_SHORT).show()
                },
                onLoading = {

                }
            )
        }

    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
package com.example.testapplication.presentation.station

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import com.example.testapplication.BaseFragment
import com.example.testapplication.R
import com.example.testapplication.databinding.FragmentLoginBinding
import com.example.testapplication.databinding.FragmentStationBinding
import com.example.testapplication.presentation.settings.SettingsFragment
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


    private var adapter:StationsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservable()
        initListener()
    }

    private fun initListener(){
        binding.settings.fromAsset("icType2.pdf").load()
        binding.settingsView.setOnClickListener{
            activity?.supportFragmentManager?.commit {
                replace<SettingsFragment>(R.id.fragment_container_view)
            }
        }
    }


    private fun initObservable(){
        viewModel.getStations()
        with(viewModel){
            stationsLiveData.observeUIState(
                onSuccess = {
                    adapter = StationsAdapter(
                        requireContext(),
                        it!!,
                        callback
                    )
                    binding.recycle.adapter = adapter
                    binding.progress.isGone = true
                    binding.loadingView.isGone = true
                },
                onError = {
                    binding.progress.isGone = true
                    binding.loadingView.isGone = true
                },
                onLoading = {
                    binding.progress.isVisible = true
                    binding.loadingView.isVisible = true
                }
            )
        }

    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
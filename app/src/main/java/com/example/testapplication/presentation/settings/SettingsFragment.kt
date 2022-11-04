package com.example.testapplication.presentation.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.ToggleButton
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import com.example.testapplication.BaseFragment
import com.example.testapplication.R
import com.example.testapplication.databinding.FragmentLoginBinding
import com.example.testapplication.databinding.FragmentSettingsBinding
import com.example.testapplication.presentation.station.StationFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {


    private val viewModel:SettingsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        initView()
    }

    private fun initView(){
        binding.toggle.isChecked = viewModel.getFlagToShowKw()
        binding.toggle2.isChecked = viewModel.getFlagToShowDistance()
        binding.goBack.fromAsset("icX.pdf").load()
    }

    private fun initListener(){
        binding.toggle.setOnClickListener {
            viewModel.clickOnToggleHideKw((it as ToggleButton).isChecked)
        }
        binding.toggle2.setOnClickListener {
            viewModel.clickOnToggleHideDistance((it as ToggleButton).isChecked)
        }
        binding.frame.setOnClickListener {
            activity?.supportFragmentManager?.commit {
                replace<StationFragment>(R.id.fragment_container_view)
            }
        }
    }

}
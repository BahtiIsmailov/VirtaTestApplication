package com.example.testapplication.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.*
import com.example.testapplication.BaseFragment
import com.example.testapplication.R
import com.example.testapplication.databinding.FragmentLoginBinding
import com.example.testapplication.presentation.station.StationFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel:LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAssets()
        initObservable()
        initClickListener()
    }

    private fun initClickListener(){
        with(binding){
            loginButton.setOnClickListener {
                viewModel.login(login.text.toString(),password.text.toString())
            }
        }
    }

    private fun initAssets(){
        with(binding){
            cross.fromAsset("icX.pdf").load()
            phone.fromAsset("logIn.pdf").load()
            person.fromAsset("icPerson.pdf").load()
            lock.fromAsset("icLock.pdf").load()
        }

    }


    private fun initObservable(){
        viewModel.networkState.observeUIState(
            onSuccess = {
                activity?.supportFragmentManager?.commit{
                    replace<StationFragment>(R.id.fragment_container_view)
                }
            },
            onError = {
                Toast.makeText(requireContext(), "$it", Toast.LENGTH_SHORT).show()
                binding.progress.isGone = true
                binding.loadingView.isGone = true
            },
            onLoading = {
                binding.progress.isVisible = true
                binding.loadingView.isVisible = true
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
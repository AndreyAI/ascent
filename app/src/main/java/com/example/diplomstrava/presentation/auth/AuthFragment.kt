package com.example.diplomstrava.presentation.auth

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.diplomstrava.R
import com.example.diplomstrava.databinding.FragmentLoginBinding
import com.example.diplomstrava.presentation.ScreenState
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse

class AuthFragment : Fragment(R.layout.fragment_login) {

    private val binding by viewBinding(FragmentLoginBinding::bind)
    private val viewModel: AuthViewModel by viewModels()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == AUTH_REQUEST_CODE && data != null) {
            val tokenExchangeRequest = AuthorizationResponse.fromIntent(data)
                ?.createTokenExchangeRequest()
            val exception = AuthorizationException.fromIntent(data)
            when {
                tokenExchangeRequest != null && exception == null ->
                    viewModel.onAuthCodeReceived(tokenExchangeRequest)
                exception != null -> viewModel.onAuthCodeFailed(exception)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.imageLogo.isVisible = false
        }
        bindViewModel()
    }

    private fun bindViewModel() {
        binding.buttonContinue.setOnClickListener { viewModel.openLoginPage() }
//        viewModel.loadingLiveData.observe(viewLifecycleOwner, ::updateIsLoading)
        viewModel.openAuthPageLiveData.observe(viewLifecycleOwner, ::openAuthPage)
        viewModel.state.observe(viewLifecycleOwner) {
            updateView(it)
        }
//        viewModel.authSuccessLiveData.observe(viewLifecycleOwner) {
//            findNavController().navigate(AuthFragmentDirections.actionLoginFragmentToContainerFragment())
//        }
    }

    private fun updateView(state: ScreenState) {

        when (state) {
            is ScreenState.DefaultState -> {
                defaultView(true)
            }
            is ScreenState.LoadingState -> {
                defaultView(false)
            }
            is ScreenState.SuccessState -> {
                findNavController().navigate(AuthFragmentDirections.actionLoginFragmentToContainerFragment())
            }
            is ScreenState.ErrorState -> {
                toast("Error")
                defaultView(true)
            }
            is ScreenState.CancelState -> {
                toast("Cancel")
                defaultView(true)
            }
        }
    }

    private fun defaultView(visibility: Boolean) {
        binding.buttonContinue.isEnabled = visibility
        binding.progressBar.isVisible = !visibility
    }

    private fun openAuthPage(intent: Intent) {
        startActivityForResult(intent, AUTH_REQUEST_CODE)
    }

    private fun toast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
    }

    companion object {
        private const val AUTH_REQUEST_CODE = 342
    }

}
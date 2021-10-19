package com.example.diplomstrava.presentation

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.diplomstrava.R
import com.example.diplomstrava.databinding.FragmentLoginBinding
import com.example.diplomstrava.databinding.ItemOnboardingBinding
import com.example.diplomstrava.presentation.onboarding.OnBoardingFragmentDirections
import com.example.diplomstrava.presentation.onboarding.OnBoardingItemFragment

class LoginFragment: Fragment(R.layout.fragment_login) {

    private val binding by viewBinding(FragmentLoginBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
//            binding.imageLogo.scaleType = ImageView.ScaleType.FIT_XY
            binding.imageLogo.isVisible = false
        }


    }

}
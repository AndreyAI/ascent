package com.example.diplomstrava.presentation.onboarding

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.diplomstrava.R
import com.example.diplomstrava.databinding.ItemOnboardingBinding
import com.example.diplomstrava.utils.withArguments

class OnBoardingItemFragment : Fragment(R.layout.item_onboarding) {

    private val binding by viewBinding(ItemOnboardingBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageWelcome.setImageResource(requireArguments().getInt(KEY_IMAGE))
        binding.textHeadline.setText(requireArguments().getInt(KEY_HEADLINE))
        binding.textDescription.setText(requireArguments().getInt(KEY_DESC))
        binding.buttonSkip.setText(requireArguments().getInt(KEY_BUTTON))

        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            binding.imageWelcome.isVisible = false
        }

        binding.buttonSkip.setOnClickListener {
            val direction = OnBoardingFragmentDirections.actionOnBoardingFragmentToLoginFragment()
            findNavController().navigate(direction)
        }

    }

    companion object {

        private const val KEY_HEADLINE = "headline"
        private const val KEY_IMAGE = "image"
        private const val KEY_DESC = "description"
        private const val KEY_BUTTON = "button"

        fun newInstance(
            @StringRes headlineRes: Int,
            @DrawableRes drawableRes: Int,
            @StringRes descriptionRes: Int,
            @StringRes buttonRes: Int
        ): OnBoardingItemFragment {
            return OnBoardingItemFragment().withArguments {
                putInt(KEY_HEADLINE, headlineRes)
                putInt(KEY_IMAGE, drawableRes)
                putInt(KEY_DESC, descriptionRes)
                putInt(KEY_BUTTON, buttonRes)
            }
        }
    }
}
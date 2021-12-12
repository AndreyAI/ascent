package com.example.diplomstrava.presentation.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.diplomstrava.R
import com.example.diplomstrava.data.OnBoardingData
import com.example.diplomstrava.databinding.FragmentOnboardingBinding
import kotlin.math.abs

class OnBoardingFragment : Fragment(R.layout.fragment_onboarding) {

    private val binding by viewBinding(FragmentOnboardingBinding::bind)
    private val viewModel by viewModels<OnBoardingViewModel>()
    private val screens = listOf(
        OnBoardingData(
            headlineText = R.string.welcome_to_ascent,
            descriptionText = R.string.desc_welcome,
            buttonText = R.string.skip,
            onBoardImage = R.drawable.ic_onboarding_welcome
        ),
        OnBoardingData(
            headlineText = R.string.friends,
            descriptionText = R.string.desc_friends,
            buttonText = R.string.skip,
            onBoardImage = R.drawable.ic_onboarding_friends
        ),
        OnBoardingData(
            headlineText = R.string.activities,
            descriptionText = R.string.desc_activities,
            buttonText = R.string.okay,
            onBoardImage = R.drawable.ic_onboarding_activities
        )
    )


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewPager()

    }

    private fun initViewPager() {

        val adapter = OnBoardingAdapter(screens, this)
        binding.viewPager.adapter = adapter
        binding.springDotsIndicator.setViewPager2(binding.viewPager)

        binding.viewPager.setPageTransformer { page, position ->
            page.translationX = -position * page.width
            page.alpha = 1 - abs(position)
        }

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.saveCurrentPos(position)
            }
        })



        viewModel.currentPage.observe(viewLifecycleOwner) {
            binding.viewPager.currentItem = it
            adapter.notifyDataSetChanged() //https://github.com/tommybuonomo/dotsindicator/issues/68
        }

    }
}
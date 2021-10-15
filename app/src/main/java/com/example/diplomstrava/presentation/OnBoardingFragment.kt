package com.example.diplomstrava.presentation

import android.os.Bundle
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.diplomstrava.R
import com.example.diplomstrava.data.OnBoardingData
import com.example.diplomstrava.databinding.FragmentOnboardingBinding
import kotlin.math.abs

class OnBoardingFragment : Fragment(R.layout.fragment_onboarding) {

    private val binding by viewBinding(FragmentOnboardingBinding::bind)

    private val screens = listOf(
        OnBoardingData(
            headlineText = R.string.welcome_to_ascent,
            descriptionText = R.string.desc_welcome,
            onBoardImage = R.drawable.ic_onboarding_welcome
        ),
        OnBoardingData(
            headlineText = R.string.friends,
            descriptionText = R.string.desc_friends,
            onBoardImage = R.drawable.ic_onboarding_friends
        ),
        OnBoardingData(
            headlineText = R.string.activities,
            descriptionText = R.string.desc_activities,
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

//        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
//            tab.text = getString(articleToShow[position].textRes)
//
//            if (screens[position].amountBadge > 0) {
//                tab.orCreateBadge.apply {
//                    number = screens[position].amountBadge
//                    badgeGravity = BadgeDrawable.TOP_END
//                }
//            } else {
//                tab.removeBadge()
//            }
//        }.attach()


    }
}
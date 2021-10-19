package com.example.diplomstrava.presentation.onboarding

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.diplomstrava.data.OnBoardingData

class OnBoardingAdapter(
    private val screens: List<OnBoardingData>,
    fragment: Fragment
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return screens.size
    }

    override fun createFragment(position: Int): Fragment {
        val screen: OnBoardingData = screens[position]
        return OnBoardingItemFragment.newInstance(
            headlineRes = screen.headlineText,
            drawableRes = screen.onBoardImage,
            descriptionRes = screen.descriptionText,
            buttonRes = screen.buttonText

        )
    }

}
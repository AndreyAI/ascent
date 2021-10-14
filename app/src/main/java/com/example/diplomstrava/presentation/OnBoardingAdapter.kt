package com.example.diplomstrava.presentation

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
        return FragmentIntoViewPager.newInstance(
            textRes = screen.textRes,
            drawableRes = screen.drawableRes
        )
    }

}

/*
class AdapterDataToFragment(
    private val screens: List<DataScreen>,
    fragment: Fragment
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return screens.size
    }

    override fun createFragment(position: Int): Fragment {
        Log.d("viewPager", "OnboardingAdapter createFragment $position")
        val screen: DataScreen = screens[position]
        return FragmentIntoViewPager.newInstance(
            textRes = screen.textRes,
            drawableRes = screen.drawableRes
        )
    }
}
 */
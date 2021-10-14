package com.example.diplomstrava.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.diplomstrava.R
import com.example.diplomstrava.databinding.FragmentOnboardingBinding

class OnBoardingFragment : Fragment(R.layout.fragment_onboarding) {

    val binding by viewBinding(FragmentOnboardingBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
    }

    private fun initViewPager() {

        val adapter = AdapterDataToFragment(articleToShow, this)
        viewPager.adapter = adapter
        spring_dots_indicator.setViewPager2(viewPager)

        viewPager.setPageTransformer { page, position ->
            page.translationX = -position * page.width
            page.alpha = 1 - abs(position)
        }

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getString(articleToShow[position].textRes)

            if (screens[position].amountBadge > 0) {
                tab.orCreateBadge.apply {
                    number = screens[position].amountBadge
                    badgeGravity = BadgeDrawable.TOP_END
                }
            } else {
                tab.removeBadge()
            }
        }.attach()

        Log.d("CURRENT TAB", "CALL TABLAYOUT ONE ${tabLayout.hashCode()}")

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.getTabAt(position)?.removeBadge()
                screens[position].amountBadge = 0
            }
        })
    }
}
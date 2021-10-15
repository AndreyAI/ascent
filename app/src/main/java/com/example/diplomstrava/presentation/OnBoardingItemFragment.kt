package com.example.diplomstrava.presentation

import android.os.Bundle
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
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

    }

    companion object {

        private const val KEY_HEADLINE = "headline"
        private const val KEY_IMAGE = "image"
        private const val KEY_DESC = "description"

        fun newInstance(
            @StringRes headlineRes: Int,
            @DrawableRes drawableRes: Int,
            @StringRes descriptionRes: Int
        ): OnBoardingItemFragment {
            return OnBoardingItemFragment().withArguments {
                putInt(KEY_HEADLINE, headlineRes)
                putInt(KEY_IMAGE, drawableRes)
                putInt(KEY_DESC, descriptionRes)
            }
        }
    }
}

/*
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        textView.setText(requireArguments().getInt(KEY_TEXT))
        imageView.setImageResource(requireArguments().getInt(KEY_IMAGE))

        eventGenerateButton.setOnClickListener {
            (parentFragment as GenerateBadge).generateBadge()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(
            "viewPager",
            "Fragment onDestroy = ${resources.getString(requireArguments().getInt(KEY_TEXT))}"
        )
    }

    companion object {

        private const val KEY_TEXT = "text"
        private const val KEY_IMAGE = "image"

        fun newInstance(
            @StringRes textRes: Int,
            @DrawableRes drawableRes: Int
        ): FragmentIntoViewPager {
            return FragmentIntoViewPager().withArguments {
                putInt(KEY_TEXT, textRes)
                putInt(KEY_IMAGE, drawableRes)
            }
        }
    }
}
 */
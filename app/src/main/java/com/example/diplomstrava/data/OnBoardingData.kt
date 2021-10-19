package com.example.diplomstrava.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class OnBoardingData(
    @StringRes val headlineText: Int,
    @StringRes val descriptionText: Int,
    @StringRes val buttonText: Int,
    @DrawableRes val onBoardImage: Int
)
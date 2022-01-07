package com.example.diplomstrava.presentation.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OnBoardingViewModel: ViewModel() {

    private val currentPageLiveData = MutableLiveData<Int>()

    val currentPage: LiveData<Int>
    get() = currentPageLiveData

    fun saveCurrentPos(position: Int){
        currentPageLiveData.postValue(position)
    }
}
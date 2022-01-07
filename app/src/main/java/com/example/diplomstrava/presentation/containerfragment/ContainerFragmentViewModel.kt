package com.example.diplomstrava.presentation.containerfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ContainerFragmentViewModel: ViewModel() {

    private val currentPageLiveData = MutableLiveData<String>()

    val currentPage: LiveData<String>
        get() = currentPageLiveData

    fun saveCurrentPos(position: String){
        currentPageLiveData.postValue(position)
    }
}
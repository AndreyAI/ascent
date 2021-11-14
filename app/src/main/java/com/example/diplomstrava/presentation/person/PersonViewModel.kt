package com.example.diplomstrava.presentation.person

import androidx.lifecycle.*
import com.example.diplomstrava.data.RepositoryPerson
import com.example.diplomstrava.presentation.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(
    private val repository: RepositoryPerson
) : ViewModel() {

    val person = repository.getPersonFlow().asLiveData()

    private val stateLiveData = MutableLiveData<ScreenState>(ScreenState.LoadingState)

    val state: LiveData<ScreenState>
        get() = stateLiveData

    fun bindViewModel() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                stateLiveData.postValue(ScreenState.LoadingState)
                repository.queryPersonData()
                stateLiveData.postValue(ScreenState.DefaultState)
            } catch (t: Throwable) {
                stateLiveData.postValue(ScreenState.ErrorState)
                Timber.e(t)
            }
        }
    }

    fun updateWeight(weight: Double){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.updateWeight(weight)
            } catch (t: Throwable) {
                stateLiveData.postValue(ScreenState.ErrorState)
                Timber.e(t)
            }
        }
    }
}
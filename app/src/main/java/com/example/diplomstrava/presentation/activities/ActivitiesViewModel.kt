package com.example.diplomstrava.presentation.activities

import androidx.lifecycle.*
import com.example.diplomstrava.data.PersonWithActivity
import com.example.diplomstrava.data.RepositoryActivity
import com.example.diplomstrava.presentation.ScreenState
import com.example.diplomstrava.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivitiesViewModel @Inject constructor(
    private val repository: RepositoryActivity
) : ViewModel() {

    //val activities = repository.getActivities().asLiveData()
    private val activitiesLiveData = MutableLiveData<List<PersonWithActivity>>()
    val activities: LiveData<List<PersonWithActivity>>
        get() = activitiesLiveData

    private val stateLiveData = MutableLiveData<ScreenState>(ScreenState.LoadingState)
    val state: LiveData<ScreenState>
        get() = stateLiveData

    private val stateSnackLiveData = SingleLiveEvent<Unit>()
    val stateSnack: LiveData<Unit>
        get() = stateSnackLiveData

    private val retrySnackLiveData = SingleLiveEvent<Unit>()
    val retrySnack: LiveData<Unit>
        get() = retrySnackLiveData

    fun bindViewModel(isSwipe: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (isSwipe) stateLiveData.postValue(ScreenState.SwipeRefresh)
                else stateLiveData.postValue(ScreenState.LoadingState)
                val activities = repository.queryNewActivities()
                if (activities.isNotEmpty()) {
                    activitiesLiveData.postValue(activities)
                    stateLiveData.postValue(ScreenState.DefaultState)
                } else {
                    stateLiveData.postValue(ScreenState.EmptyListState)
                }
            } catch (t: Throwable) {
                val activities = repository.queryCachedActivities()
                if (activities.isNotEmpty()) {
                    activitiesLiveData.postValue(activities)
                    stateLiveData.postValue(ScreenState.ErrorState)
                    stateSnackLiveData.postValue(Unit)
                } else {
                    stateLiveData.postValue(ScreenState.RetryListState)
                    retrySnackLiveData.postValue(Unit)
                }
            }
        }
    }

    fun bindDefaultModel() {
        viewModelScope.launch(Dispatchers.IO) {
            stateLiveData.postValue(ScreenState.DefaultState)
        }
    }
}
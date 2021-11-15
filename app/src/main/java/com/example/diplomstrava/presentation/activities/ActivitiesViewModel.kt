package com.example.diplomstrava.presentation.activities

import androidx.lifecycle.*
import com.example.diplomstrava.data.PersonWithActivity
import com.example.diplomstrava.data.RepositoryActivity
import com.example.diplomstrava.presentation.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
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

    fun bindViewModel() {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val activities = repository.queryNewActivities()
                activitiesLiveData.postValue(activities)
            } catch (t: Throwable) {
                Timber.e(t)
            }
        }

    }
}

/*
class MovieListViewModel : ViewModel() {

 */
package com.example.diplomstrava.presentation.addactivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diplomstrava.data.RepositoryActivities
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddActivityViewModel @Inject constructor(
    private val repository: RepositoryActivities
) : ViewModel() {

    fun saveActivity(
        name: String,
        type: String,
        date: String,
        time: String,
        distance: Double,
        description: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.saveActivity(
                    name = name,
                    type = type,
                    date = date,
                    time = time,
                    distance = distance,
                    description = description
                )
            } catch (t: Throwable) {
                Timber.e(t, "user save error")
            }
        }
    }
}
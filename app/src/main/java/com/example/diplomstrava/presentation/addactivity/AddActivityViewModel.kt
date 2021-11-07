package com.example.diplomstrava.presentation.addactivity

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diplomstrava.data.RepositoryActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class AddActivityViewModel : ViewModel() {

    private val repository = RepositoryActivity()

    fun saveActivity(
        name: String,
        type: String,
        date: String,
        time: String,
        distance: String,
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
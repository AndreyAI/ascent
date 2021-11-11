package com.example.diplomstrava.presentation.person

import androidx.lifecycle.*
import com.example.diplomstrava.data.RepositoryPerson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(
    private val repository: RepositoryPerson
) : ViewModel() {

    val person = repository.getPerson().asLiveData()

    fun bindViewModel() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.queryPersonData()
            } catch (t: Throwable) {
                Timber.e(t)
            }
        }
    }
}
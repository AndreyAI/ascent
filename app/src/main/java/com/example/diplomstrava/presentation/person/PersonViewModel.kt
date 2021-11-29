package com.example.diplomstrava.presentation.person

import androidx.lifecycle.*
import com.example.diplomstrava.data.Person
import com.example.diplomstrava.data.PersonWithActivity
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

    //val person = repository.getPersonFlow().asLiveData()

    private val personLiveData = MutableLiveData<Person>()

    val person: LiveData<Person>
        get() = personLiveData

    private val stateLiveData = MutableLiveData<ScreenState>(ScreenState.LoadingState)

    val state: LiveData<ScreenState>
        get() = stateLiveData

    fun bindViewModel() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                stateLiveData.postValue(ScreenState.LoadingState)
                val person = repository.queryPersonData()
                personLiveData.postValue(person)
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



    fun logout(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.logout()
                stateLiveData.postValue(ScreenState.LogoutState)
            } catch (t: Throwable) {

                Timber.e(t)
            }
        }
    }
}
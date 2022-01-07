package com.example.diplomstrava.presentation.share

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diplomstrava.data.Contact
import com.example.diplomstrava.data.RepositoryPerson
import com.example.diplomstrava.presentation.ScreenState
import com.example.diplomstrava.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShareListViewModel @Inject constructor(
    private val repository: RepositoryPerson
) : ViewModel() {

    private val contactsLiveData = MutableLiveData<List<Contact>>()
    val contacts: LiveData<List<Contact>>
        get() = contactsLiveData

    private val stateLiveData = MutableLiveData<ScreenState>(ScreenState.LoadingState)
    val state: LiveData<ScreenState>
        get() = stateLiveData

    private val stateSnackLiveData = SingleLiveEvent<Unit>()
    val stateSnack: LiveData<Unit>
        get() = stateSnackLiveData

    fun getContact() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                stateLiveData.postValue(ScreenState.LoadingState)
                val contacts = repository.getContacts()
                contactsLiveData.postValue(contacts)
                stateLiveData.postValue(ScreenState.DefaultState)
            } catch (e: Exception) {
                if (e is SecurityException) {
                    stateLiveData.postValue(ScreenState.ErrorState)
                    stateSnackLiveData.postValue(Unit)
                }
            }

        }
    }
}
package com.example.diplomstrava.presentation.activities

import androidx.lifecycle.*
import com.example.diplomstrava.data.PersonWithActivity
import com.example.diplomstrava.data.RepositoryActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.combine
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

    private val repository = MovieRepository()

    private var currentCall: Call? = null
    private var currentJob: Job? = null
    private var currentQuery: Pair<String?, MovieType> = Pair(null, MovieType.MOVIE)

//    private val movieListLiveData = MutableLiveData<List<RemoteMovie>>()
//
//    val movieList: LiveData<List<RemoteMovie>>
//        get() = movieListLiveData

    private val _movieList = MutableStateFlow<List<RemoteMovie>>(emptyList())
    val movieList: StateFlow<List<RemoteMovie>> = _movieList


    @ExperimentalCoroutinesApi
    @FlowPreview
    fun bind(queryFlow: Flow<String?>, movieTypeFlow: Flow<MovieType>) {
        currentJob?.cancel()

        currentJob = combine(
            queryFlow.onStart { emit(currentQuery.first) },
            movieTypeFlow.onStart { emit(currentQuery.second) }
        ) { text, type -> text to type }
            .debounce(500)
            .mapLatest {
                currentQuery = it
                repository.searchMovies(it.first, it.second)
            }
            .catch { Timber.d("Error") }
            .flowOn(Dispatchers.IO)
            .onEach {
                _movieList.value =it
            }
            .logOn()
            .launchIn(viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
        currentCall?.cancel()
        currentJob?.cancel()
    }

}
 */
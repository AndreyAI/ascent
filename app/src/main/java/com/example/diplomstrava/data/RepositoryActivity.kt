package com.example.diplomstrava.data

import com.example.diplomstrava.data.db.Database
import kotlinx.coroutines.flow.Flow

class RepositoryActivity {

    private val activityDao = Database.instance.activityDao()

    fun getActivities(): Flow<List<Activity>> {
        return activityDao.getActivities()
    }

    suspend fun saveActivity(
        name: String,
        type: String,
        date: String,
        time: String,
        distance: String,
        description: String
    ) {
        val activity = Activity(
            id = 0L,
            name = name,
            type = type,
            date = date,
            time = 0,
            distance = 0,
            elevation = 0,
            description = description
        )
        activityDao.insertActivities(listOf(activity))
    }
    /*
        suspend fun searchMovies(
        query: String?,
        type: MovieType
    ): List<RemoteMovie> {
        return try {
            val response = Network.getSearchMovieCall(query, movieTypeToString(type)).execute()
            val responseString = response.body?.string().orEmpty()
            val movies = parseMovieResponse(responseString)
            addMoviesInDb(movies)
            movies
        } catch (t: Throwable) {
            getMovies(query, type)
        }
    }
     */
}